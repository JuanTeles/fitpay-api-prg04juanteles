package br.com.ifba.fitpay.api.features.matricula.domain.dto.request;

import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
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
public class MatriculaPutRequestDto {

    // O ID do Contrato é obrigatório para saber qual registro atualizar
    @NotNull(message = "O ID do contrato é obrigatório para a atualização")
    private Long id;

    @JsonProperty("data_inicio")
    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @JsonProperty("data_fim")
    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate dataFim;

    @NotNull(message = "O status da matrícula é obrigatório")
    private StatusMatricula status;

    // Referência ao Aluno
    @JsonProperty("aluno")
    @NotNull(message = "Os dados do aluno são obrigatórios")
    @Valid // Garante a validação do ID dentro da classe AlunoIdDto
    private AlunoIdDto aluno;

    // Referência ao Plano
    @JsonProperty("plano")
    @NotNull(message = "Os dados do plano são obrigatórios")
    @Valid // Garante a validação do ID dentro da classe PlanoIdDto
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
        private Long id; // Mantive UUID conforme seu código, mas verifique se no banco Plano é UUID ou Long
    }
}