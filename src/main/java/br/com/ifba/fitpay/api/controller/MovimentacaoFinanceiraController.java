package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service.MovimentacaoFinanceiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/movimentacoes_financeiras") // Define o prefixo da URL: http://localhost:8080/movimentacoes_financeiras
@RequiredArgsConstructor // Cria o construtor automaticamente
public class MovimentacaoFinanceiraController {

    private final MovimentacaoFinanceiraService movimentacaoFinanceiraService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<MovimentacaoFinanceira> save(@RequestBody MovimentacaoFinanceira movimentacaoFinanceira) {
        // @RequestBody converte o JSON que vem do Postman para o objeto MovimentacaoFinanceira
        MovimentacaoFinanceira movimentacaoFinanceiraSalva = movimentacaoFinanceiraService.save(movimentacaoFinanceira);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacaoFinanceiraSalva);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<MovimentacaoFinanceira>> findAll() {
        return ResponseEntity.ok(movimentacaoFinanceiraService.findAll());
    }

    // Endpoint para Deletar por ID
    // {id} é a variável de caminho que identifica qual movimentacao excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Executa a exclusão primeiro
        movimentacaoFinanceiraService.delete(id);

        // Depois retorna o status NO_CONTENT sem corpo (build)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Edpoint para atualizar a movimentação financeira
    // A anotação @PutMapping mapeia requisições HTTP PUT para atualizações
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody MovimentacaoFinanceira movimentacaoFinanceira) {
        movimentacaoFinanceiraService.update(movimentacaoFinanceira);
        // Retorna 204 No Content após atualizar com sucesso
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}