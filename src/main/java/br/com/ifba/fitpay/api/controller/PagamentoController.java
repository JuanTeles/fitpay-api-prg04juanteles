package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/pagamentos") // Define o prefixo da URL: http://localhost:8080/pagamentos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class PagamentoController {

    private final PagamentoService pagamentoService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<Pagamento> save(@RequestBody Pagamento pagamento) {
        // @RequestBody converte o JSON que vem do Postman para o objeto Pagamento
        Pagamento pagamentoSalvo = pagamentoService.save(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoSalvo);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<Pagamento>> findAll() {
        return ResponseEntity.ok(pagamentoService.findAll());
    }

    // Endpoint para Deletar por ID
    // {id} é a variável de caminho que identifica qual pagamento excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Executa a exclusão primeiro
        pagamentoService.delete(id);

        // Depois retorna o status NO_CONTENT sem corpo (build)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Edpoint para atualizar o pagamento
    // A anotação @PutMapping mapeia requisições HTTP PUT para atualizações
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Pagamento pagamento) {
        pagamentoService.update(pagamento);
        // Retorna 204 No Content após atualizar com sucesso
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}