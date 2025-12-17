package br.com.ifba.fitpay.api.features.contratoaluno.domain.repository;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoAlunoRepository extends JpaRepository<ContratoAluno, Long> {

    // Custom Query: Busca todos os contratos com determinado status
    @Query("select c from ContratoAluno c where c.status = ?1")
    List<ContratoAluno> findByStatus(StatusMatricula status);
}
