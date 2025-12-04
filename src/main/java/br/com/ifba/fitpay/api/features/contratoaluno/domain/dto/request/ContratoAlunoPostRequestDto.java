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
public class ContratoAlunoPostRequestDto {

    @JsonProperty("data_inicio")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    private LocalDate dataFim;

    private StatusMatricula status;

    // Para o ModelMapper STRICT funcionar, a estrutura deve bater com a Entidade.
    // A entidade tem um objeto "aluno", então aqui criamos um objeto para receber o ID.
    @JsonProperty("aluno")
    private AlunoIdDto aluno;

    @JsonProperty("plano")
    private PlanoIdDto plano;

    // Classes estáticas internas para facilitar o mapeamento de ID
    @Data
    public static class AlunoIdDto {
        private UUID id;
    }

    @Data
    public static class PlanoIdDto {
        private Long id;
    }
}