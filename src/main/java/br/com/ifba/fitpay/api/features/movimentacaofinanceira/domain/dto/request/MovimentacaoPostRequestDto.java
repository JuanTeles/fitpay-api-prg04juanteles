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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoPostRequestDto {

    @JsonProperty("data_hora")
    // Opcional: Se não for enviado, o Service define como "agora".
    private LocalDateTime dataHora;

    @JsonProperty("tipo_movimentacao")
    @NotNull(message = "O tipo de movimentação (ENTRADA/SAIDA) é obrigatório")
    private TipoMovimentacao tipoMovimentacao;

    @JsonProperty("valor")
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor da movimentação deve ser positivo")
    private Double valor;

    @JsonProperty("descricao")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @JsonProperty("categoria_movimentacao")
    @NotNull(message = "A categoria da movimentação é obrigatória")
    private CategoriaMovimentacao categoriaMovimentacao;

    @JsonProperty("pagamento_origem")
    @Valid // Se o objeto for enviado, valida o ID dentro dele
    private PagamentoIdDto pagamentoOrigem;

    @Data
    public static class PagamentoIdDto {
        @NotNull(message = "O ID do pagamento de origem é obrigatório")
        private Long id;
    }
}