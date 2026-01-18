package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.endereco.domain.dto.request.EnderecoPutRequestDto;
import br.com.ifba.fitpay.api.features.endereco.domain.dto.response.EnderecoGetResponseDto;
import br.com.ifba.fitpay.api.features.endereco.domain.dto.request.EnderecoPostRequestDto;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.service.IEnderecoService;
import br.com.ifba.fitpay.api.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/enderecos") // Define o prefixo da URL: http://localhost:8080/endereco
@RequiredArgsConstructor // Cria o construtor automaticamente
public class EnderecoController {

    private final IEnderecoService enderecoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoGetResponseDto> save(@RequestBody @Valid EnderecoPostRequestDto enderecoDto) {

        // DTO -> Entity
        Endereco endereco = objectMapperUtil.map(enderecoDto, Endereco.class);

        // Salva
        Endereco enderecoSalvo = enderecoService.save(endereco);

        // Entity -> DTO
        EnderecoGetResponseDto response = objectMapperUtil.map(enderecoSalvo, EnderecoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoGetResponseDto> findById(@PathVariable("id") Long id) {

        // Busca a entidade no Service
        Endereco endereco = enderecoService.findById(id);

        // Converte para DTO de Resposta
        EnderecoGetResponseDto response = objectMapperUtil.map(endereco, EnderecoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EnderecoGetResponseDto>> findAll(
            Pageable pageable,
            @RequestParam(required = false) String search // Adicionado parametro
    ) {
        Page<Endereco> enderecos;

        if (search != null && !search.isBlank()) {
            enderecos = enderecoService.findByLogradouroOrBairro(search, pageable);
        } else {
            enderecos = enderecoService.findAll(pageable);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(enderecos.map(c -> objectMapperUtil.map(c, EnderecoGetResponseDto.class)));
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid EnderecoPutRequestDto enderecoDto) {

        Endereco endereco = objectMapperUtil.map(enderecoDto, Endereco.class);

        enderecoService.update(endereco);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        enderecoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}