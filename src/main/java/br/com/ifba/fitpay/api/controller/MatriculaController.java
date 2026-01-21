package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.matricula.domain.dto.request.MatriculaPutRequestDto;
import br.com.ifba.fitpay.api.features.matricula.domain.dto.response.MatriculaGetResponseDto;
import br.com.ifba.fitpay.api.features.matricula.domain.dto.request.MatriculaPostRequestDto;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.matricula.domain.service.MatriculaService;
import br.com.ifba.fitpay.api.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/matriculas") // Define o prefixo da URL: http://localhost:8080/matriculas_alunos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final ObjectMapperUtil objectMapperUtil;

    // Endpoint para Salvar
    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatriculaGetResponseDto> save(@RequestBody @Valid MatriculaPostRequestDto contratoDto) {

        // DTO -> Entity
        Matricula contratoEntity = objectMapperUtil.map(contratoDto, Matricula.class);

        // Service Salva
        Matricula contratoSalvo = matriculaService.save(contratoEntity);

        // Entity -> DTO Response
        MatriculaGetResponseDto response = objectMapperUtil.map(contratoSalvo, MatriculaGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para Listar Todos
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MatriculaGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.matriculaService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, MatriculaGetResponseDto.class)));
    }

    // Endpoint para Atualizar
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid MatriculaPutRequestDto contratoDto) {

        Matricula contratoEntity = objectMapperUtil.map(contratoDto, Matricula.class);
        matriculaService.update(contratoEntity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para Deletar
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        matriculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<MatriculaGetResponseDto>> findByAluno(@PathVariable Long alunoId) {
        List<Matricula> matriculas = matriculaService.findByAluno(alunoId);

        // Mapeia a lista de entidades para DTOs
        List<MatriculaGetResponseDto> dtos = matriculas.stream()
                .map(m -> objectMapperUtil.map(m, MatriculaGetResponseDto.class))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/dashboard/novas-no-mes")
    public ResponseEntity<Long> countNovasNoMes() {
        long count = matriculaService.countNovasMatriculasNoMes();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/dashboard/a-renovar")
    public ResponseEntity<Long> countARenovar() {
        // Define fixo em 7 dias
        long count = matriculaService.countMatriculasARenovar(7);
        return ResponseEntity.ok(count);
    }
}