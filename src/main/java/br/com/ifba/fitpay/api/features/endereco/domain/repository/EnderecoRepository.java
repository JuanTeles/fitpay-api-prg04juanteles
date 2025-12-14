package br.com.ifba.fitpay.api.features.endereco.domain.repository;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

    // Custom Query: Busca endereços por CEP (pode haver mais de um usuário no mesmo CEP, mas aqui retornamos lista)
    @Query("select e from Endereco e where e.cep = ?1")
    List<Endereco> findByCep(String cep);
}