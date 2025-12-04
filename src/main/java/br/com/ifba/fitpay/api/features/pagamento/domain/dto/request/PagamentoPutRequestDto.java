package br.com.ifba.fitpay.api.features.pagamento.domain.dto.request;

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
public class PagamentoPutRequestDto {

    // O ID do Pagamento é necessário
    private UUID id;

    @JsonProperty("data_pagamento")
    private LocalDate dataPagamento;

    @JsonProperty("valor_pago")
    private Double valorPago;

    @JsonProperty("referencia_periodo")
    private String referenciaPeriodo;

    @JsonProperty("metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    // Objeto aninhado para mapear o relacionamento com ContratoAluno (ID Corrigido para UUID)
    @JsonProperty("contrato_aluno")
    private ContratoIdDto contratoAluno;

    @Data
    public static class ContratoIdDto {
        private UUID id; // Tipo corrigido para UUID
    }
}