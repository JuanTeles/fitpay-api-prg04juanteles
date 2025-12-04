package br.com.ifba.fitpay.api.features.plano.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanoPutRequestDto {

    // O ID do Plano é necessário
    private UUID id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("duracao_dias")
    private Integer duracaoDias;

    @JsonProperty("descricao")
    private String descricao;
}