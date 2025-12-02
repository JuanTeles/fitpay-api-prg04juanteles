package br.com.ifba.fitpay.api.features.aluno.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
