package br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.response;

import br.com.ifba.fitpay.api.features.aluno.domain.dto.response.AlunoGetResponseDto;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.enums.StatusMatricula;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContratoAlunoGetResponseDto {

    private UUID id;

    @JsonProperty("data_inicio")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    private LocalDate dataFim;

    private StatusMatricula status;

    // Aqui retornamos o DTO completo do Aluno que você já criou
    @JsonProperty("aluno")
    private AlunoGetResponseDto aluno;

    // Supondo que você terá um DTO de Plano, ou pode retornar apenas o nome/ID por enquanto
    @JsonProperty("plano_id")
    private Long planoId;
}