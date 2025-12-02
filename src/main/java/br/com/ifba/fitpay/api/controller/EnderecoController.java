package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/enderecos") // Define o prefixo da URL: http://localhost:8080/endereco
@RequiredArgsConstructor // Cria o construtor automaticamente
public class EnderecoController {

    private final EnderecoService enderecoService;

    // Endpoint para Salvar
    @PostMapping("/save")
    public ResponseEntity<Endereco> save(@RequestBody Endereco endereco) {
        // @RequestBody converte o JSON que vem do Postman para o objeto Endereco
        Endereco enderecoSalvo = enderecoService.save(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
    }

    // Endpoint para Listar Todos
    @GetMapping("/findall")
    public ResponseEntity<List<Endereco>> findAll() {
        return ResponseEntity.ok(enderecoService.findAll());
    }

    // Endpoint para Deletar por ID
    // {id} é a variável de caminho que identifica qual endereco excluir
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // Executa a exclusão primeiro
        enderecoService.delete(id);

        // Depois retorna o status NO_CONTENT sem corpo (build)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Edpoint para atualizar o endereco
    // A anotação @PutMapping mapeia requisições HTTP PUT para atualizações
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Endereco endereco) {
        enderecoService.update(endereco);
        // Retorna 204 No Content após atualizar com sucesso
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
