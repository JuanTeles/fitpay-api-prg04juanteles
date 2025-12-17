package br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.request;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.enums.StatusMatricula;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContratoAlunoPostRequestDto {

    @JsonProperty("data_inicio")
    @NotNull(message = "A data de início do contrato é obrigatória")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    @NotNull(message = "A data de fim do contrato é obrigatória")
    private LocalDate dataFim;

    @NotNull(message = "O status da matrícula é obrigatório")
    private StatusMatricula status;

    // Para o ModelMapper STRICT funcionar, a estrutura deve bater com a Entidade.
    // A entidade tem um objeto "aluno", então aqui criamos um objeto para receber o ID.
    @JsonProperty("aluno")
    @NotNull(message = "Os dados do aluno são obrigatórios")
    @Valid // Garante que o Java entre na classe AlunoIdDto para validar o ID lá dentro
    private AlunoIdDto aluno;

    @JsonProperty("plano")
    @NotNull(message = "Os dados do plano são obrigatórios")
    @Valid // Garante que o Java entre na classe PlanoIdDto para validar o ID lá dentro
    private PlanoIdDto plano;

    // Classes estáticas internas para facilitar o mapeamento de ID
    @Data
    public static class AlunoIdDto {
        @NotNull(message = "O ID do aluno é obrigatório")
        private Long id;
    }

    @Data
    public static class PlanoIdDto {
        @NotNull(message = "O ID do plano é obrigatório")
        private Long id;
    }
}