package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.request;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoPutRequestDto {

    // O ID da Movimentação é necessário
    private UUID id;

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

    // Opcional: Link com Pagamento (ID Corrigido para UUID)
    @JsonProperty("pagamento_origem")
    private PagamentoIdDto pagamentoOrigem;

    @Data
    public static class PagamentoIdDto {
        private UUID id; // Tipo corrigido para UUID
    }
}