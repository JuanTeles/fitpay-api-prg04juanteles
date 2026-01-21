package br.com.ifba.fitpay.api.features.matricula.domain.repository;

import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IMatriculaRepository extends JpaRepository<Matricula, Long> {

    // Custom Query: Busca todos os contratos com determinado status
    @Query("select c from Matricula c where c.status = ?1")
    List<Matricula> findByStatus(StatusMatricula status);

    boolean existsByAlunoIdAndStatus(Long alunoId, StatusMatricula status);

    // Busca todas as matrículas de um aluno, ordenadas da mais recente para a mais antiga
    List<Matricula> findByAlunoIdOrderByDataInicioDesc(Long alunoId);

    // busca matriculas com status X que venceram antes da data Y
    List<Matricula> findByStatusAndDataFimBefore(StatusMatricula status, LocalDate data);

    // Conta matrículas criadas num intervalo de datas
    long countByDataInicioBetween(LocalDate inicio, LocalDate fim);

    // [Conta matrículas ativas que vencem dentro de um intervalo de datas
    long countByDataFimBetweenAndStatus(LocalDate inicio, LocalDate fim, StatusMatricula status);
}
