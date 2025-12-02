package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoFinanceiraRepository extends JpaRepository<MovimentacaoFinanceira, Long> {

}
