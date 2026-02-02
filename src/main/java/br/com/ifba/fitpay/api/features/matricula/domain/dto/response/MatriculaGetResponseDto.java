package br.com.ifba.fitpay.api.features.matricula.domain.dto.response;

import br.com.ifba.fitpay.api.features.aluno.domain.dto.response.AlunoGetResponseDto;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.plano.domain.dto.response.PlanoGetResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaGetResponseDto {

    private Long id;

    @JsonProperty("data_inicio")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    private LocalDate dataFim;

    private StatusMatricula status;

    @JsonProperty("valor_fechado")
    private Double valorFechado;

    // Aqui retornamos o DTO completo do Aluno que você já criou
    @JsonProperty("aluno")
    private AlunoGetResponseDto aluno;

    @JsonProperty("plano")
    private PlanoGetResponseDto plano;
}