package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository.MovimentacaoFinanceiraRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoFinanceiraService implements IMovimentacaoFinanceiraService {

    private final MovimentacaoFinanceiraRepository repository;

    @Override
    public MovimentacaoFinanceira save(MovimentacaoFinanceira movimentacao) {
        // Regras de validação
        validarMovimentacao(movimentacao);

        // Se não informar data/hora, assume o momento atual
        if (movimentacao.getDataHora() == null) {
            movimentacao.setDataHora(LocalDateTime.now());
        }

        return repository.save(movimentacao);
    }

    @Override
    public List<MovimentacaoFinanceira> findAll() {
        return repository.findAll();
    }

    @Override
    public MovimentacaoFinanceira findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Movimentação Financeira não encontrada com o ID: " + id));
    }

    @Override
    public MovimentacaoFinanceira update(MovimentacaoFinanceira movimentacao) {
        // Garante existência
        this.findById(movimentacao.getId());

        validarMovimentacao(movimentacao);

        return repository.save(movimentacao);
    }

    @Override
    public void delete(UUID id) {
        this.findById(id);
        repository.deleteById(id);
    }

    /**
     * Regras de validação financeira.
     */
    private void validarMovimentacao(MovimentacaoFinanceira mov) {
        if (mov.getValor() == null || mov.getValor() <= 0) {
            throw new BusinessException("O valor da movimentação deve ser positivo e maior que zero.");
        }

        if (mov.getDescricao() == null || mov.getDescricao().trim().isEmpty()) {
            throw new BusinessException("É obrigatório informar uma descrição para a movimentação.");
        }

        if (mov.getTipoMovimentacao() == null) {
            throw new BusinessException("O tipo de movimentação (ENTRADA/SAIDA) é obrigatório.");
        }

        if (mov.getCategoriaMovimentacao() == null) {
            throw new BusinessException("A categoria da movimentação é obrigatória.");
        }
    }
}