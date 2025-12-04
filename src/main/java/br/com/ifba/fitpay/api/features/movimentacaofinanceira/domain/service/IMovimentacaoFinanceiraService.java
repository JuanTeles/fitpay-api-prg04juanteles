package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import java.util.List;
import java.util.UUID;

public interface IMovimentacaoFinanceiraService {

    MovimentacaoFinanceira save(MovimentacaoFinanceira movimentacao);

    List<MovimentacaoFinanceira> findAll();

    MovimentacaoFinanceira findById(UUID id);

    MovimentacaoFinanceira update(MovimentacaoFinanceira movimentacao);

    void delete(UUID id);
}