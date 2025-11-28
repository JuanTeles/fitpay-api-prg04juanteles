package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.service.PlanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/planos") // Define o prefixo da URL: http://localhost:8080/planos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class PlanoController {

    private final PlanoService planoService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<Plano> save(@RequestBody Plano plano) {
        // @RequestBody converte o JSON que vem do Postman para o objeto Plano
        Plano planoSalvo = planoService.save(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(planoSalvo);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<Plano>> findAll() {
        return ResponseEntity.ok(planoService.findAll());
    }
}