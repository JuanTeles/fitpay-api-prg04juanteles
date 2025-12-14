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

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "tipo_movimentacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "categoria_movimentacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaMovimentacao categoriaMovimentacao;

    @OneToOne
    @JoinColumn(name = "pagamento_id", nullable = true)
    private Pagamento pagamentoOrigem;
}