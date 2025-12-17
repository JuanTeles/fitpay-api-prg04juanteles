package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimentacaoFinanceiraRepository extends JpaRepository<MovimentacaoFinanceira, Long> {

    // Custom Query para teste (busca exata pela descrição)
    @Query("select m from MovimentacaoFinanceira m where m.descricao = ?1")
    Optional<MovimentacaoFinanceira> findByDescricao(String descricao);
}
