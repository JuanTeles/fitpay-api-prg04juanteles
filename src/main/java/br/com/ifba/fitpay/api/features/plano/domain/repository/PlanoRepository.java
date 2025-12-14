package br.com.ifba.fitpay.api.features.plano.domain.repository;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {

    @Query("select p from Plano p where p.nome = ?1")
    Optional<Plano> findByNome(String nome);
}
