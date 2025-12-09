package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IMovimentacaoFinanceiraService {

    MovimentacaoFinanceira save(MovimentacaoFinanceira movimentacao);

    Page<MovimentacaoFinanceira> findAll(Pageable pageable);

    MovimentacaoFinanceira findById(UUID id);

    MovimentacaoFinanceira update(MovimentacaoFinanceira movimentacao);

    void delete(UUID id);
}