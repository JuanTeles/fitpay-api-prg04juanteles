package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.service.ContratoAlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/contratos_alunos") // Define o prefixo da URL: http://localhost:8080/contratos_alunos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class ContratoAlunoController {

    private final ContratoAlunoService contratoAlunoService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<ContratoAluno> save(@RequestBody ContratoAluno contratoAluno) {
        // @RequestBody converte o JSON que vem do Postman para o objeto ContratoAluno
        ContratoAluno contratoAlunoSalvo = contratoAlunoService.save(contratoAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoAlunoSalvo);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<ContratoAluno>> findAll() {
        return ResponseEntity.ok(contratoAlunoService.findAll());
    }

    // Endpoint para Deletar por ID
    // {id} é a variável de caminho que identifica qual contrato excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Executa a exclusão primeiro
        contratoAlunoService.delete(id);

        // Depois retorna o status NO_CONTENT sem corpo (build)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Edpoint para atualizar o contrato do aluno
    // A anotação @PutMapping mapeia requisições HTTP PUT para atualizações
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody ContratoAluno contratoAluno) {
        contratoAlunoService.update(contratoAluno);
        // Retorna 204 No Content após atualizar com sucesso
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
