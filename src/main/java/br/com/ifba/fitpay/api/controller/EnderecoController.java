package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.endereco.domain.dto.request.EnderecoPutRequestDto;
import br.com.ifba.fitpay.api.features.endereco.domain.dto.response.EnderecoGetResponseDto;
import br.com.ifba.fitpay.api.features.endereco.domain.dto.request.EnderecoPostRequestDto;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.service.IEnderecoService;
import br.com.ifba.fitpay.api.infraestructure.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/enderecos") // Define o prefixo da URL: http://localhost:8080/endereco
@RequiredArgsConstructor // Cria o construtor automaticamente
public class EnderecoController {

    private final IEnderecoService enderecoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoGetResponseDto> save(@RequestBody EnderecoPostRequestDto enderecoDto) {

        // DTO -> Entity
        Endereco endereco = objectMapperUtil.map(enderecoDto, Endereco.class);

        // Salva
        Endereco enderecoSalvo = enderecoService.save(endereco);

        // Entity -> DTO
        EnderecoGetResponseDto response = objectMapperUtil.map(enderecoSalvo, EnderecoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnderecoGetResponseDto>> findAll() {
        return ResponseEntity.ok(
                objectMapperUtil.mapAll(
                        enderecoService.findAll(),
                        EnderecoGetResponseDto.class
                )
        );
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody EnderecoPutRequestDto enderecoDto) {

        Endereco endereco = objectMapperUtil.map(enderecoDto, Endereco.class);

        enderecoService.update(endereco);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        enderecoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}