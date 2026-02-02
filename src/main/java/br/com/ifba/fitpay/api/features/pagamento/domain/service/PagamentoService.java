package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
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

@Service
@RequiredArgsConstructor
public class PagamentoService implements IPagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Override
    @Transactional
    public Pagamento save(Pagamento pagamento) {
        // Regras de validação antes de salvar
        validarPagamento(pagamento);

        // Regra: Se a data não for informada, assume a data de hoje
        if (pagamento.getDataPagamento() == null) {
            pagamento.setDataPagamento(LocalDate.now());
        }

        return pagamentoRepository.save(pagamento);
    }

    @Override
    public Page<Pagamento> findAll(Pageable pageable) {
        return pagamentoRepository.findAll(pageable);
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
}