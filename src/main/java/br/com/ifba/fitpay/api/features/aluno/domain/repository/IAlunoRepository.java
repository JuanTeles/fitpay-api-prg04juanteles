package br.com.ifba.fitpay.api.features.aluno.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAlunoRepository extends JpaRepository<Aluno, UUID> {

    // Metodo para verificar existência
    boolean existsByCpf(String cpf);

    // Verifica se existe algum CPF igual, mas que NÃO seja do próprio usuário
    boolean existsByCpfAndIdNot(String cpf, UUID id);

    @Query("select a from Aluno a where a.email = ?1")
    Optional<Aluno> findByEmail(String email);
}