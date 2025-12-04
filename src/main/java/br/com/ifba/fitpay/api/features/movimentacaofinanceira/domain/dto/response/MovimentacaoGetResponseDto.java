package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.response;

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
public class MovimentacaoGetResponseDto {

    private UUID id;

    @JsonProperty("data_hora")
    private LocalDateTime dataHora;

    @JsonProperty("tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("categoria_movimentacao")
    private CategoriaMovimentacao categoriaMovimentacao;

    // Retorna apenas o ID para não poluir a resposta com o objeto pagamento inteiro
    @JsonProperty("pagamento_origem_id")
    private Long pagamentoOrigemId;
}