package br.com.ifba.fitpay.api.features.aluno.domain.dto.response;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoGetResponseDto {

    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    // ADICIONAR ESTE CAMPO
    @JsonProperty(value = "cpf")
    private String cpf;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "telefone")
    private String telefone;

    @JsonProperty(value = "endereco")
    private Endereco endereco;
}