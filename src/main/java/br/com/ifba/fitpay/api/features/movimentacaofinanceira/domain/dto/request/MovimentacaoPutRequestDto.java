package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.request;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoPutRequestDto {

    // O ID da Movimentação é obrigatório para atualização
    @NotNull(message = "O ID da movimentação é obrigatório")
    private UUID id;

    @JsonProperty("data_hora")
    @NotNull(message = "A data e hora são obrigatórias")
    private LocalDateTime dataHora;

    @JsonProperty("tipo_movimentacao")
    @NotNull(message = "O tipo de movimentação é obrigatório")
    private TipoMovimentacao tipoMovimentacao;

    @JsonProperty("valor")
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valor;

    @JsonProperty("descricao")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @JsonProperty("categoria_movimentacao")
    @NotNull(message = "A categoria da movimentação é obrigatória")
    private CategoriaMovimentacao categoriaMovimentacao;

    // Opcional: Link com Pagamento
    @JsonProperty("pagamento_origem")
    @Valid // Valida o ID interno se o objeto for enviado
    private PagamentoIdDto pagamentoOrigem;

    @Data
    public static class PagamentoIdDto {
        @NotNull(message = "O ID do pagamento de origem é obrigatório")
        private UUID id;
    }
}