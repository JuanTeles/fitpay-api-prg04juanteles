package br.com.ifba.fitpay.api.features.contratoaluno.domain.model;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.infraestructure.model.PersistenceEntity;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.enums.StatusMatricula;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contratos_alunos")
@EqualsAndHashCode(callSuper = true)
public class ContratoAluno extends PersistenceEntity {

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private StatusMatricula status;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;
}