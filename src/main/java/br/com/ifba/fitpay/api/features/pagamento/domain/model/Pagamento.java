package br.com.ifba.fitpay.api.features.pagamento.domain.model;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
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

    private LocalDate dataPagamento;

    private Double valorPago;

    private String referenciaPeriodo;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @ManyToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private ContratoAluno contratoAluno;
}