package br.com.ifba.fitpay.api.features.plano.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanoPutRequestDto {

    // O ID do Plano é obrigatório para atualização
    @NotNull(message = "O ID do plano é obrigatório")
    private Long id;

    @JsonProperty("nome")
    @NotBlank(message = "O nome do plano é obrigatório")
    private String nome;

    @JsonProperty("valor")
    @NotNull(message = "O valor é obrigatório")
    @PositiveOrZero(message = "O valor do plano não pode ser negativo")
    private Double valor;

    @JsonProperty("duracao_dias")
    @NotNull(message = "A duração em dias é obrigatória")
    @Positive(message = "A duração deve ser de pelo menos 1 dia")
    private Integer duracaoDias;

    @JsonProperty("descricao")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;
}