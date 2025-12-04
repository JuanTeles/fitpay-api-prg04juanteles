package br.com.ifba.fitpay.api.features.pagamento.domain.dto.response;

import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoGetResponseDto {

    private UUID id;

    @JsonProperty("data_pagamento")
    private LocalDate dataPagamento;

    @JsonProperty("valor_pago")
    private Double valorPago;

    @JsonProperty("referencia_periodo")
    private String referenciaPeriodo;

    @JsonProperty("metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    // Retorna o ID do contrato vinculado
    @JsonProperty("contrato_aluno_id")
    private Long contratoAlunoId;

}