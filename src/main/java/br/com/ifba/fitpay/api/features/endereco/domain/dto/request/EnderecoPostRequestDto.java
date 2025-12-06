package br.com.ifba.fitpay.api.features.endereco.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoPostRequestDto {

    @JsonProperty("logradouro")
    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    @JsonProperty("numero")
    @NotBlank(message = "O número é obrigatório")
    private String numero;

    @JsonProperty("complemento")
    // Complemento é opcional, então não colocamos @NotBlank
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
    // Opcional: Você pode usar @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    // Mas por enquanto o @NotBlank já garante que não vá vazio.
    private String cep;
}