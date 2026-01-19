package br.com.ifba.fitpay.api.features.aluno.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAlunoRepository extends JpaRepository<Aluno, Long> {

    // Metodo para verificar existência
    boolean existsByCpf(String cpf);

    // Verifica se existe algum CPF igual, mas que NÃO seja do próprio usuário
    boolean existsByCpfAndIdNot(String cpf, Long id);

    @Query("select a from Aluno a where a.email = ?1")
    Optional<Aluno> findByEmail(String email);

    // Busca alunos onde o Nome CONTÉM o texto OU o CPF CONTÉM o texto (ignorando maiúsculas/minúsculas)
    Page<Aluno> findByNomeContainingIgnoreCaseOrCpfContaining(String nome, String cpf, Pageable pageable);

    // Filtra apenas pelo Status da Matrícula
    @Query("SELECT DISTINCT a FROM Aluno a JOIN a.matriculas m WHERE m.status = :status")
    Page<Aluno> findByStatusMatricula(@Param("status") StatusMatricula status, Pageable pageable);

    // Filtra por Busca (Nome/CPF) E Status da Matrícula simultaneamente
    @Query("SELECT DISTINCT a FROM Aluno a JOIN a.matriculas m WHERE (LOWER(a.nome) LIKE LOWER(CONCAT('%', :search, '%')) OR a.cpf LIKE CONCAT('%', :search, '%')) AND m.status = :status")
    Page<Aluno> findByNomeOrCpfAndStatusMatricula(@Param("search") String search, @Param("status") StatusMatricula status, Pageable pageable);
}