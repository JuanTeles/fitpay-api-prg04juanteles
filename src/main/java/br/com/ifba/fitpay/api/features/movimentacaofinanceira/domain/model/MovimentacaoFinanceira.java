package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.infraestructure.model.PersistenceEntity;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movimentacoes_financeiras")
@EqualsAndHashCode(callSuper = true)
public class MovimentacaoFinanceira extends PersistenceEntity {

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    private Double valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private CategoriaMovimentacao categoriaMovimentacao;

    @OneToOne
    @JoinColumn(name = "pagamento_id", nullable = true)
    private Pagamento pagamentoOrigem;
}