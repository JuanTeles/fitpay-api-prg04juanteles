package br.com.ifba.fitpay.api.features.pagamento.domain.dto.request;

import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoPostRequestDto {

    @JsonProperty("data_pagamento")
    // Opcional: Se não informado, o Service define como LocalDate.now()
    private LocalDate dataPagamento;

    @JsonProperty("valor_pago")
    @NotNull(message = "O valor pago é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valorPago;

    @JsonProperty("referencia_periodo")
    @NotBlank(message = "A referência do período (ex: Janeiro/2025) é obrigatória")
    private String referenciaPeriodo;

    @JsonProperty("metodo_pagamento")
    @NotNull(message = "O método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;

    // Objeto aninhado para mapear o relacionamento com ContratoAluno
    @JsonProperty("contrato_aluno")
    @NotNull(message = "O contrato do aluno é obrigatório")
    @Valid // Valida o ID dentro do objeto
    private ContratoIdDto contratoAluno;

    @Data
    public static class ContratoIdDto {
        @NotNull(message = "O ID do contrato é obrigatório")
        private UUID id;
    }
}