package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.request.MovimentacaoPutRequestDto;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.response.MovimentacaoGetResponseDto;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.dto.request.MovimentacaoPostRequestDto;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.service.IMovimentacaoFinanceiraService;
import br.com.ifba.fitpay.api.infraestructure.util.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController // Diz ao Spring que isso é uma API REST (retorna JSON)
@RequestMapping("/movimentacoes_financeiras") // Define o prefixo da URL: http://localhost:8080/movimentacoes_financeiras
@RequiredArgsConstructor // Cria o construtor automaticamente
public class MovimentacaoFinanceiraController {

    private final IMovimentacaoFinanceiraService movimentacaoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovimentacaoGetResponseDto> save(@RequestBody @Valid MovimentacaoPostRequestDto dto) {

        // DTO -> Entity
        MovimentacaoFinanceira mov = objectMapperUtil.map(dto, MovimentacaoFinanceira.class);

        // Service Save
        MovimentacaoFinanceira movSalva = movimentacaoService.save(mov);

        // Entity -> DTO Response
        MovimentacaoGetResponseDto response = objectMapperUtil.map(movSalva, MovimentacaoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MovimentacaoGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.movimentacaoService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, MovimentacaoGetResponseDto.class)));
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid MovimentacaoPutRequestDto dto) {
        // Mapeamento e atualização
        MovimentacaoFinanceira mov = objectMapperUtil.map(dto, MovimentacaoFinanceira.class);

        movimentacaoService.update(mov);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        movimentacaoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}