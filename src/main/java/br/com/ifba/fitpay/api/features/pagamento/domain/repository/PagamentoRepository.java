package br.com.ifba.fitpay.api.features.pagamento.domain.repository;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}