package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.pagamento.domain.dto.request.PagamentoPutRequestDto;
import br.com.ifba.fitpay.api.features.pagamento.domain.dto.response.PagamentoGetResponseDto;
import br.com.ifba.fitpay.api.features.pagamento.domain.dto.request.PagamentoPostRequestDto;
import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.service.IPagamentoService;
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
@RequestMapping("/pagamentos") // Define o prefixo da URL: http://localhost:8080/pagamentos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class PagamentoController {

    private final IPagamentoService pagamentoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoGetResponseDto> save(@RequestBody @Valid PagamentoPostRequestDto pagamentoDto) {

        // DTO -> Entity
        Pagamento pagamento = objectMapperUtil.map(pagamentoDto, Pagamento.class);

        // Service Salva
        Pagamento pagamentoSalvo = pagamentoService.save(pagamento);

        // Entity -> DTO Response
        PagamentoGetResponseDto response = objectMapperUtil.map(pagamentoSalvo, PagamentoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PagamentoGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.pagamentoService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, PagamentoGetResponseDto.class)));
    }

    // Endpoint Update (Simplificado)
    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid PagamentoPutRequestDto pagamentoDto) {

        // Conversão
        Pagamento pagamento = objectMapperUtil.map(pagamentoDto, Pagamento.class);

        pagamentoService.update(pagamento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        pagamentoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}