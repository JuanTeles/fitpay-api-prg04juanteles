package br.com.ifba.fitpay.api.features.pagamento.domain.repository;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    // Custom Query: Busca pagamentos de um determinado mês/ano
    @Query("select p from Pagamento p where p.referenciaPeriodo = ?1")
    List<Pagamento> findByReferenciaPeriodo(String referenciaPeriodo);
}