package br.com.ifba.fitpay.api.features.endereco.domain.repository;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Custom Query: Busca endereços por CEP (pode haver mais de um usuário no mesmo CEP, mas aqui retornamos lista)
    @Query("select e from Endereco e where e.cep = ?1")
    List<Endereco> findByCep(String cep);

    // Busca alunos enderecos pelo bairro ou logradouro
    Page<Endereco> findByLogradouroContainingIgnoreCaseOrBairroContainingIgnoreCase(String logradouro, String bairro, Pageable pageable);

    // Busca endereço exato para evitar duplicidade
    Optional<Endereco> findByCepAndNumeroAndComplemento(String cep, String numero, String complemento);
}