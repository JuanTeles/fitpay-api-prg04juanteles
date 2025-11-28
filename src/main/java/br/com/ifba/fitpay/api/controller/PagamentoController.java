package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}