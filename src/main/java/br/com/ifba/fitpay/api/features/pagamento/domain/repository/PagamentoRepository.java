package br.com.ifba.fitpay.api.features.pagamento.domain.repository;

import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // Custom Query: Busca pagamentos de um determinado mês/ano
    @Query("select p from Pagamento p where p.referenciaPeriodo = ?1")
    List<Pagamento> findByReferenciaPeriodo(String referenciaPeriodo);

    @Query("""
            SELECT p FROM Pagamento p
            JOIN p.matricula m
            JOIN m.aluno a
            WHERE (:nome IS NULL OR a.nome LIKE :nome)
            AND (:metodo IS NULL OR p.metodoPagamento = :metodo)
        """)
    Page<Pagamento> findWithFilters(
            @Param("nome") String nome,
            @Param("metodo") MetodoPagamento metodo,
            Pageable pageable
    );



}