package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service.MovimentacaoFinanceiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}