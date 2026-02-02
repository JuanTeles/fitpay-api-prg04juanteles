package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMovimentacaoFinanceiraService {

    MovimentacaoFinanceira save(MovimentacaoFinanceira movimentacao);
    MovimentacaoFinanceira findById(Long id);
    MovimentacaoFinanceira update(MovimentacaoFinanceira movimentacao);
    Page<MovimentacaoFinanceira> findAll(TipoMovimentacao tipo, CategoriaMovimentacao categoria, Pageable pageable);
    void delete(Long id);
}