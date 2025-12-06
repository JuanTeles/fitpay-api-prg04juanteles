package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.repository.PagamentoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    @Override
    public Pagamento findById(UUID id) {
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
    public void delete(UUID id) {
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
        if (pagamento.getContratoAluno() == null || pagamento.getContratoAluno().getId() == null) {
            throw new BusinessException("O pagamento deve estar vinculado a um Contrato de Aluno válido.");
        }
    }
}