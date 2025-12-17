package br.com.ifba.fitpay.api.features.pagamento.domain.dto.request;

import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoPutRequestDto {

    // O ID do Pagamento é obrigatório para atualização
    @NotNull(message = "O ID do pagamento é obrigatório")
    private Long id;

    @JsonProperty("data_pagamento")
    @NotNull(message = "A data do pagamento é obrigatória")
    private LocalDate dataPagamento;

    @JsonProperty("valor_pago")
    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valorPago;

    @JsonProperty("referencia_periodo")
    @NotBlank(message = "A referência do período é obrigatória")
    private String referenciaPeriodo;

    @JsonProperty("metodo_pagamento")
    @NotNull(message = "O método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;

    // Objeto aninhado para mapear o relacionamento com ContratoAluno
    @JsonProperty("contrato_aluno")
    @NotNull(message = "O contrato vinculado é obrigatório")
    private ContratoIdDto contratoAluno;

    @Data
    public static class ContratoIdDto {
        @NotNull(message = "O ID do contrato é obrigatório")
        private Long id;
    }
}