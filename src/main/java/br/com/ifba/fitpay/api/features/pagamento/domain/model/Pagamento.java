package br.com.ifba.fitpay.api.features.pagamento.domain.model;

import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.infraestructure.model.PersistenceEntity;
import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pagamentos")
@EqualsAndHashCode(callSuper = true)
public class Pagamento extends PersistenceEntity {

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor_pago", nullable = false)
    private Double valorPago;

    @Column(name = "referencia_periodo", nullable = false)
    private String referenciaPeriodo;

    @Column(name = "metodo_pagamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @ManyToOne
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;
}