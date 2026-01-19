package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.aluno.domain.dto.request.AlunoPutRequestDto;
import br.com.ifba.fitpay.api.features.aluno.domain.dto.response.AlunoGetResponseDto;
import br.com.ifba.fitpay.api.features.aluno.domain.dto.request.AlunoPostRequestDto;
import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.service.IAlunoService;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
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
@RequestMapping("/alunos") // Define o prefixo da URL: http://localhost:8080/alunos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class AlunoController {

    private final IAlunoService alunoService;
    private final ObjectMapperUtil objectMapperUtil; // Injeção do Mapper

    // Endpoint para salvar
    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid AlunoPostRequestDto alunoPostRequestDto) {

        // Converte DTO -> Entity
        Aluno alunoEntity = objectMapperUtil.map(alunoPostRequestDto, Aluno.class);

        // Salva no Service
        Aluno alunoSalvo = alunoService.save(alunoEntity);

        // Converte Entity -> ResponseDTO
        AlunoGetResponseDto responseDto = objectMapperUtil.map(alunoSalvo, AlunoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // Endpoint para listar (com ou sem busca)
    @GetMapping("/findall")
    public ResponseEntity<Page<AlunoGetResponseDto>> findAll(
            Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) StatusMatricula status // Novo parâmetro opcional
    ) {
        // Chama o service com os 3 parâmetros
        Page<Aluno> alunos = alunoService.findAll(pageable, search, status);

        // Mantém a lógica de mapeamento e verificação de "ativo" que fizemos antes
        Page<AlunoGetResponseDto> alunosDto = alunos.map(aluno -> {
            AlunoGetResponseDto dto = objectMapperUtil.map(aluno, AlunoGetResponseDto.class);

            boolean estaAtivo = aluno.getMatriculas() != null &&
                    aluno.getMatriculas().stream()
                            .anyMatch(m -> m.getStatus() == StatusMatricula.ATIVO);

            dto.setAtivo(estaAtivo);
            return dto;
        });

        return ResponseEntity.status(HttpStatus.OK).body(alunosDto);
    }

    // Endpoint para excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        alunoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint para atualizar
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid AlunoPutRequestDto alunoDto) {

        Aluno aluno = objectMapperUtil.map(alunoDto, Aluno.class);
        alunoService.update(aluno);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para buscar pelo id
    @GetMapping("/find/{id}")
    public ResponseEntity<AlunoGetResponseDto> findById(@PathVariable("id") Long id) {
        Aluno aluno = alunoService.findById(id);

        // Converte para DTO
        AlunoGetResponseDto responseDto = objectMapperUtil.map(aluno, AlunoGetResponseDto.class);

        return ResponseEntity.ok(responseDto);
    }
}