package br.com.ifba.fitpay.api.features.plano.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanoPostRequestDto {

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("duracao_dias")
    private Integer duracaoDias;

    @JsonProperty("descricao")
    private String descricao;
}