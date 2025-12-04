package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.request;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoPostRequestDto {

    @JsonProperty("data_hora")
    private LocalDateTime dataHora;

    @JsonProperty("tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao; // ENTRADA ou SAIDA

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("categoria_movimentacao")
    private CategoriaMovimentacao categoriaMovimentacao;

    // Opcional: Link com Pagamento (se for uma receita gerada por mensalidade)
    @JsonProperty("pagamento_origem")
    private PagamentoIdDto pagamentoOrigem;

    @Data
    public static class PagamentoIdDto {
        private Long id;
    }
}