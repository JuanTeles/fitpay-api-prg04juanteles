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

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "telefone")
    private String telefone;

    // CPF omitido aqui por ser um dado sensível

    @JsonProperty(value = "endereco")
    private Endereco endereco;
}