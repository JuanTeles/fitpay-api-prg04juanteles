package br.com.ifba.fitpay.api.features.plano.domain.model;

import br.com.ifba.fitpay.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "planos")
@EqualsAndHashCode(callSuper = true)
public class Plano extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valor;

    private Integer duracaoDias;

    private String descricao;
}