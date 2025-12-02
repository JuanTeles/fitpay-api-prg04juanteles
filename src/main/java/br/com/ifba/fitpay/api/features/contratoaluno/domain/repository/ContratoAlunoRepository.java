package br.com.ifba.fitpay.api.features.contratoaluno.domain.repository;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoAlunoRepository extends JpaRepository<ContratoAluno, Long> {

}
