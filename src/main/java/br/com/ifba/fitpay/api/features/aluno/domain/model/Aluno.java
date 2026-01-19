package br.com.ifba.fitpay.api.features.aluno.domain.model;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "alunos")
@EqualsAndHashCode(callSuper = true)
public class Aluno extends PersistenceEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula;

    // Mapeia a relação inversa para podermos verificar as matrículas do aluno
    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private java.util.List<Matricula> matriculas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;
}