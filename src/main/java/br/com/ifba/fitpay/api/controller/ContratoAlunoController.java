package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.request.ContratoAlunoPutRequestDto;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.response.ContratoAlunoGetResponseDto;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.dto.request.ContratoAlunoPostRequestDto;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.service.ContratoAlunoService;
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
@RequestMapping("/contratos_alunos") // Define o prefixo da URL: http://localhost:8080/contratos_alunos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class ContratoAlunoController {

    private final ContratoAlunoService contratoAlunoService;
    private final ObjectMapperUtil objectMapperUtil;

    // Endpoint para Salvar
    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContratoAlunoGetResponseDto> save(@RequestBody @Valid ContratoAlunoPostRequestDto contratoDto) {

        // DTO -> Entity
        ContratoAluno contratoEntity = objectMapperUtil.map(contratoDto, ContratoAluno.class);

        // Service Salva
        ContratoAluno contratoSalvo = contratoAlunoService.save(contratoEntity);

        // Entity -> DTO Response
        ContratoAlunoGetResponseDto response = objectMapperUtil.map(contratoSalvo, ContratoAlunoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para Listar Todos
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ContratoAlunoGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.contratoAlunoService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, ContratoAlunoGetResponseDto.class)));
    }

    // Endpoint para Atualizar
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid ContratoAlunoPutRequestDto contratoDto) {

        ContratoAluno contratoEntity = objectMapperUtil.map(contratoDto, ContratoAluno.class);
        contratoAlunoService.update(contratoEntity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para Deletar
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        contratoAlunoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}