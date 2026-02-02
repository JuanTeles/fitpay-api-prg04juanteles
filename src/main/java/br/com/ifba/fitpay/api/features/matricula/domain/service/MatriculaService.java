package br.com.ifba.fitpay.api.features.matricula.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.matricula.domain.repository.IMatriculaRepository;
import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.service.IPagamentoService;
import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.repository.PlanoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculaService implements IMatriculaService {

    private final IMatriculaRepository matriculaRepository;
    private final PlanoRepository planoRepository;
    private final IPagamentoService pagamentoService;

    @Override
    @Transactional
    public Matricula save(Matricula matricula, MetodoPagamento metodoPagamento) {
        // Regra de Negócio: Validação de Datas
        validarDatas(matricula);

        // Verifica duplicidade de matrícula ativa
        if (matriculaRepository.existsByAlunoIdAndStatus(matricula.getAluno().getId(), StatusMatricula.ATIVO)) {
            throw new BusinessException("Este aluno já possui uma matrícula ATIVA. Cancele a anterior antes de criar uma nova."); //
        }

        // Congelamento do Preço (Validação pela Fonte da Verdade)
        Plano planoReal = planoRepository.findById(matricula.getPlano().getId())
                .orElseThrow(() -> new BusinessException("Plano não encontrado com o ID informado."));

        // Seta o valor da matrícula com o valor do plano do banco
        // garante que ninguém forjou o preço na requisição
        matricula.setValorFechado(planoReal.getValor());

        // Vincula o objeto plano completo para garantir consistência no save
        matricula.setPlano(planoReal);

        // Salva a matrícula primeiro para gerar o ID necessário para o pagamento
        Matricula matriculaSalva = matriculaRepository.save(matricula);

        // se um metodo de pagamento foi selecionado, cria o registro financeiro automaticamente
        if (metodoPagamento != null) {
            Pagamento pagamento = new Pagamento();
            pagamento.setMatricula(matriculaSalva);
            pagamento.setMetodoPagamento(metodoPagamento);
            pagamento.setValorPago(matriculaSalva.getValorFechado());
            pagamento.setDataPagamento(LocalDate.now());

            // Define uma referência amigável (Ex: "Adesão - 02/2026")
            String ref = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
            pagamento.setReferenciaPeriodo("Adesão - " + ref);

            pagamentoService.save(pagamento);
        }

        return matriculaSalva;
    }

    @Override
    public Page<Matricula> findAll(Pageable pageable) {
        return matriculaRepository.findAll(pageable);
    }

    @Override
    public Matricula findById(Long id) {
        // BusinessException se não encontrar
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Contrato não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public Matricula update(Matricula matricula) {
        // Verifica se existe antes de atualizar
        Matricula matriculaExistente = this.findById(matricula.getId());

        // Permite ATIVO (para trancar) ou TRANCADO (para destrancar/ativar).
        if (matriculaExistente.getStatus() != StatusMatricula.ATIVO &&
                matriculaExistente.getStatus() != StatusMatricula.TRANCADO) {
            throw new BusinessException("Apenas matrículas ATIVAS ou TRANCADAS podem ser alteradas.");
        }

        // Validação de Datas
        validarDatas(matricula);

        // Atualiza apenas os campos permitidos no objeto existente
        matriculaExistente.setStatus(matricula.getStatus());
        matriculaExistente.setDataInicio(matricula.getDataInicio());
        matriculaExistente.setDataFim(matricula.getDataFim());

        return matriculaRepository.save(matriculaExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Garante que o recurso existe antes de tentar deletar
        this.findById(id);

        matriculaRepository.deleteById(id);
    }

    /**
     * Metodo auxiliar privado para validar regras de data.
     * Impede que um contrato termine antes de começar.
     */
    private void validarDatas(Matricula contrato) {
        if (contrato.getDataInicio() != null && contrato.getDataFim() != null) {
            if (contrato.getDataFim().isBefore(contrato.getDataInicio())) {
                throw new BusinessException("A Data Final do contrato não pode ser anterior à Data Inicial.");
            }
        }
    }

    @Override
    public List<Matricula> findByAluno(Long alunoId) {
        return matriculaRepository.findByAlunoIdOrderByDataInicioDesc(alunoId);
    }

    @Override
    public long countNovasMatriculasNoMes() {
        LocalDate agora = LocalDate.now();
        LocalDate inicioMes = agora.withDayOfMonth(1);
        LocalDate fimMes = agora.withDayOfMonth(agora.lengthOfMonth());

        return matriculaRepository.countByDataInicioBetween(inicioMes, fimMes);
    }

    @Override
    public long countMatriculasARenovar(int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(dias);

        // Busca entre HOJE e DATA_LIMITE que estejam ATIVOS
        return matriculaRepository.countByDataFimBetweenAndStatus(hoje, limite, StatusMatricula.ATIVO);
    }

    /**
     * Verifica diariamente se há matrículas vencidas e as inativa.
     * Cron "0 0 0 * * *" significa: Segundo 0, Minuto 0, Hora 0 (Meia-noite), Todos os dias.
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void verificarVencimentoDeMatriculas() {
        log.info("Iniciando verificação automática de matrículas vencidas...");

        LocalDate hoje = LocalDate.now();

        // Busca apenas as ATIVAS que venceram antes de HOJE
        List<Matricula> vencidas = matriculaRepository.findByStatusAndDataFimBefore(
                StatusMatricula.ATIVO,
                hoje
        );

        if (vencidas.isEmpty()) {
            log.info("Nenhuma matrícula vencida encontrada hoje.");
            return;
        }

        for (Matricula matricula : vencidas) {
            matricula.setStatus(StatusMatricula.EXPIRADO);
            // matricula.setDataCancelamento(hoje); // Se tiver esse campo, é bom setar
            log.info("Inativando matrícula ID: {} do Aluno: {}", matricula.getId(), matricula.getAluno().getNome());
        }

        matriculaRepository.saveAll(vencidas);
        log.info("Processo finalizado. Total inativado: {}", vencidas.size());
    }
}