package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository.MovimentacaoFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoFinanceiraService {
    private final MovimentacaoFinanceiraRepository movimentacaoFinanceiraRepository;

    public MovimentacaoFinanceira save(MovimentacaoFinanceira movimentacaoFinanceira) {
        return movimentacaoFinanceiraRepository.save(movimentacaoFinanceira);
    }

    public List<MovimentacaoFinanceira> findAll() {
        return movimentacaoFinanceiraRepository.findAll();
    }
}