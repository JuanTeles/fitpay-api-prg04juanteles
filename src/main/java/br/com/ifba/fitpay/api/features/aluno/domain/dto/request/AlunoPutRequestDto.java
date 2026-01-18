package br.com.ifba.fitpay.api.features.aluno.domain.dto.request;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoPutRequestDto {

    // No PUT, o ID é crucial para saber quem atualizar
    @NotNull(message = "O ID do aluno é obrigatório para atualização")
    private Long id;

    @JsonProperty(value = "nome")
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @JsonProperty(value = "cpf")
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @JsonProperty(value = "telefone")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @JsonProperty(value = "email")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @JsonProperty(value = "data_matricula")
    private LocalDate dataMatricula;

    // O @Valid aqui é essencial para validar o objeto Endereco aninhado
    @JsonProperty(value = "endereco")
    @NotNull(message = "O endereço é obrigatório")
    @Valid
    private Endereco endereco;
}