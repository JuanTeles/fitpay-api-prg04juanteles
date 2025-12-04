package br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.request;

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
public class ContratoAlunoPutRequestDto {

    // O ID do Contrato é necessário
    private UUID id;

    @JsonProperty("data_inicio")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    private LocalDate dataFim;

    private StatusMatricula status;

    // Referência ao Aluno
    @JsonProperty("aluno")
    private AlunoIdDto aluno;

    // Referência ao Plano
    @JsonProperty("plano")
    private PlanoIdDto plano;

    // Classes estáticas internas para facilitar o mapeamento de ID
    @Data
    public static class AlunoIdDto {
        private UUID id;
    }

    @Data
    public static class PlanoIdDto {
        private UUID id;
    }
}