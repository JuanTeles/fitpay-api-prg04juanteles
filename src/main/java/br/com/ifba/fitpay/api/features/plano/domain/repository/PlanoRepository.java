package br.com.ifba.fitpay.api.features.plano.domain.repository;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

}
