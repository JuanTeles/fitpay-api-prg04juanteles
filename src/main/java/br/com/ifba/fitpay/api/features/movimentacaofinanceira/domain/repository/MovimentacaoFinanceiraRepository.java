package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimentacaoFinanceiraRepository extends JpaRepository<MovimentacaoFinanceira, Long> {

    // Custom Query para teste (busca exata pela descrição)
    @Query("select m from MovimentacaoFinanceira m where m.descricao = ?1")
    Optional<MovimentacaoFinanceira> findByDescricao(String descricao);

    // Filtra apenas por Tipo
    Page<MovimentacaoFinanceira> findByTipoMovimentacao(TipoMovimentacao tipo, Pageable pageable);

    // Filtra apenas por Categoria
    Page<MovimentacaoFinanceira> findByCategoriaMovimentacao(CategoriaMovimentacao categoria, Pageable pageable);

    // Filtra por ambos (Tipo E Categoria)
    Page<MovimentacaoFinanceira> findByTipoMovimentacaoAndCategoriaMovimentacao(
            TipoMovimentacao tipo,
            CategoriaMovimentacao categoria,
            Pageable pageable
    );
}
