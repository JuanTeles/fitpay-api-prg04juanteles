package br.com.ifba.fitpay.api.features.endereco.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoPutRequestDto {

    // O ID do Endereço é necessário para saber qual atualizar
    @NotNull(message = "O ID do endereço é obrigatório")
    private Long id;

    @JsonProperty("logradouro")
    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    @JsonProperty("numero")
    @NotBlank(message = "O número é obrigatório")
    private String numero;

    @JsonProperty("complemento")
    // Complemento é opcional
    private String complemento;

    @JsonProperty("bairro")
    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @JsonProperty("cidade")
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @JsonProperty("uf")
    @NotBlank(message = "A UF é obrigatória")
    @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres (Ex: BA)")
    private String uf;

    @JsonProperty("cep")
    @NotBlank(message = "O CEP é obrigatório")
    private String cep;
}