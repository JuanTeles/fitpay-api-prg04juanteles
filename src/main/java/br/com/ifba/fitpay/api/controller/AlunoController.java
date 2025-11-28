package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}