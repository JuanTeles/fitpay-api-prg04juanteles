package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.matricula.domain.repository.IMatriculaRepository;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service.IMovimentacaoFinanceiraService;
import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.repository.PagamentoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentoService implements IPagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final IMovimentacaoFinanceiraService movimentacaoService;
    private final IMatriculaRepository matriculaRepository;

    @Override
    @Transactional
    public Pagamento save(Pagamento pagamento) {
        // Regras de validação antes de salvar
        validarPagamento(pagamento);

        // Busca a Matrícula completa para ter acesso aos dados do Aluno
        Matricula matriculaCompleta = matriculaRepository.findByIdWithAluno(pagamento.getMatricula().getId())
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada."));

        // Vincula a matrícula completa ao objeto pagamento
        pagamento.setMatricula(matriculaCompleta);

        // Regra: Se a data não for informada, assume a data de hoje
        if (pagamento.getDataPagamento() == null) {
            pagamento.setDataPagamento(LocalDate.now());
        }

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        // Gera automaticamente a movimentação financeira de ENTRADA
        this.gerarMovimentacao(pagamentoSalvo);

        return pagamentoSalvo;
    }

    @Override
    public Page<Pagamento> findAll(String nome, MetodoPagamento metodo, Pageable pageable) {
        if (nome != null && !nome.trim().isEmpty()) {
            nome = "%" + nome.trim() + "%";
        } else {
            nome = null;
        }
        return pagamentoRepository.findWithFilters(nome, metodo, pageable);
    }

    @Override
    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Pagamento não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public Pagamento update(Pagamento pagamento) {
        // Garante existência
        this.findById(pagamento.getId());

        // Valida regras novamente
        validarPagamento(pagamento);

        return pagamentoRepository.save(pagamento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.findById(id);
        pagamentoRepository.deleteById(id);
    }

    /**
     * Centraliza as validações de regra de negócio para Pagamentos.
     */
    private void validarPagamento(Pagamento pagamento) {
        // Regra 1: Valor deve ser positivo
        if (pagamento.getValorPago() == null || pagamento.getValorPago() <= 0) {
            throw new BusinessException("O valor do pagamento deve ser maior que zero.");
        }

        // Regra 2: Metodo de pagamento é obrigatório
        if (pagamento.getMetodoPagamento() == null) {
            throw new BusinessException("O método de pagamento (PIX, CARTAO, DINHEIRO) é obrigatório.");
        }

        // Regra 3: Deve estar vinculado a um contrato
        if (pagamento.getMatricula() == null || pagamento.getMatricula().getId() == null) {
            throw new BusinessException("O pagamento deve estar vinculado a um Contrato de Aluno válido.");
        }
    }

    @Override
    @Transactional
    public Pagamento gerarPagamentoAdesao(Matricula matricula, MetodoPagamento metodo) {
        Pagamento pagamento = new Pagamento();

        // Vincula o pagamento à matrícula recém-criada
        pagamento.setMatricula(matricula);

        // O valor é extraído do valorFechado da matrícula para garantir integridade
        pagamento.setValorPago(matricula.getValorFechado());

        // Define o metodo de pagamento obrigatório vindo da requisição
        pagamento.setMetodoPagamento(metodo);

        // Define a data atual para o registro
        pagamento.setDataPagamento(LocalDate.now());

        // Define uma referência amigável (Ex: "Adesão - 02/2026")
        String ref = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear();
        pagamento.setReferenciaPeriodo("Adesão - " + ref);

        // Salva utilizando o metodo save da própria classe para disparar as validações
        return this.save(pagamento);
    }

    private void gerarMovimentacao(Pagamento pagamento) {
        MovimentacaoFinanceira movimentacao = new MovimentacaoFinanceira();

        movimentacao.setDataHora(LocalDateTime.now());
        movimentacao.setValor(pagamento.getValorPago());
        movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacao.setCategoriaMovimentacao(CategoriaMovimentacao.MENSALIDADE);
        movimentacao.setPagamentoOrigem(pagamento);

        // Monta uma descrição detalhada para o fluxo de caixa
        movimentacao.setDescricao("Recebimento: " + pagamento.getReferenciaPeriodo() + " - Aluno: " + pagamento.getMatricula().getAluno().getNome());

        movimentacaoService.save(movimentacao);
    }
}