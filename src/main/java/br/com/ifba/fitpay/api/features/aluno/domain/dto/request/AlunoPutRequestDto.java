package br.com.ifba.fitpay.api.features.aluno.domain.dto.request;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data // Adicionado: Fornece Getters, Setters, etc.
@AllArgsConstructor
@NoArgsConstructor
public class AlunoPutRequestDto {

    // O ID é necessário para identificar qual aluno será atualizado
    private UUID id;

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

    // Idealmente, deve ser um EnderecoPutRequestDto
    @JsonProperty(value = "endereco")
    private Endereco endereco;
}