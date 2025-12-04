package br.com.ifba.fitpay.api.features.aluno.domain.dto.request;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco; // Pode criar um DTO para endereço também futuramente
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoPostRequestDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "cpf")
    private String cpf;

    @JsonProperty(value = "telefone")
    private String telefone;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "data_matricula")
    private LocalDate dataMatricula;

    @JsonProperty(value = "endereco")
    private Endereco endereco;
}