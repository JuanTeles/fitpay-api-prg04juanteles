package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository.MovimentacaoFinanceiraRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovimentacaoFinanceiraService implements IMovimentacaoFinanceiraService {

    private final MovimentacaoFinanceiraRepository repository;

    @Override
    @Transactional
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
    public Page<MovimentacaoFinanceira> findAll(TipoMovimentacao tipo, CategoriaMovimentacao categoria, Pageable pageable) {
        // Tem Tipo E Categoria
        if (tipo != null && categoria != null) {
            return repository.findByTipoMovimentacaoAndCategoriaMovimentacao(tipo, categoria, pageable);
        }
        // Apenas Tipo
        else if (tipo != null) {
            return repository.findByTipoMovimentacao(tipo, pageable);
        }
        // Apenas Categoria
        else if (categoria != null) {
            return repository.findByCategoriaMovimentacao(categoria, pageable);
        }
        // Nenhum filtro (busca tudo)
        else {
            return repository.findAll(pageable);
        }
    }

    @Override
    public MovimentacaoFinanceira findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Movimentação Financeira não encontrada com o ID: " + id));
    }

    @Override
    @Transactional
    public MovimentacaoFinanceira update(MovimentacaoFinanceira movimentacao) {

        MovimentacaoFinanceira existente = this.findById(movimentacao.getId());

        if (existente.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            throw new BusinessException("Movimentações de ENTRADA não podem ser editadas.");
        }

        validarMovimentacao(movimentacao);

        return repository.save(movimentacao);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        MovimentacaoFinanceira existente = this.findById(id);

        if (existente.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            throw new BusinessException("Movimentações de ENTRADA não podem ser excluídas.");
        }

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