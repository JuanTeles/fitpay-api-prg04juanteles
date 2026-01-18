package br.com.ifba.fitpay.api.features.aluno.domain.dto.request;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; // Importante
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoPostRequestDto {

    @JsonProperty(value = "nome")
    @NotBlank(message = "O nome é obrigatório e não pode estar vazio!")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @JsonProperty(value = "cpf")
    @NotBlank(message = "O CPF é obrigatório!")
    @CPF
    private String cpf;

    @JsonProperty(value = "telefone")
    @NotBlank(message = "O telefone é obrigatório!")
    private String telefone;

    @JsonProperty(value = "email")
    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "Formato de e-mail inválido!")
    private String email;

    @JsonProperty(value = "data_matricula")
    private LocalDate dataMatricula;

    // Endereco é um objeto, podemos validar com @NotNull e @Valid (cascade)
    @JsonProperty(value = "endereco")
    @NotNull(message = "Os dados de endereço são obrigatórios")
    private Endereco endereco;
}