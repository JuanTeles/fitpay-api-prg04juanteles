package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/alunos") // Define o prefixo da URL: http://localhost:8080/alunos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class AlunoController {

    private final AlunoService alunoService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<Aluno> save(@RequestBody Aluno aluno) {
        // @RequestBody converte o JSON que vem do Postman para o objeto Aluno
        Aluno alunoSalvo = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<Aluno>> findAll() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    // Endpoint para Deletar por ID
    // {id} é a variável de caminho que identifica qual aluno excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Executa a exclusão primeiro
        alunoService.delete(id);

        // Depois retorna o status NO_CONTENT sem corpo (build)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Edpoint para atualizar o aluno
    // A anotação @PutMapping mapeia requisições HTTP PUT para atualizações
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Aluno aluno) {
        alunoService.update(aluno);
        // Retorna 204 No Content após atualizar com sucesso
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}