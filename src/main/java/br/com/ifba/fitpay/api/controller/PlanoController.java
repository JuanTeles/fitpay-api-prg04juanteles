package br.com.ifba.fitpay.api.controller;

import br.com.ifba.fitpay.api.features.plano.domain.dto.request.PlanoPutRequestDto;
import br.com.ifba.fitpay.api.features.plano.domain.dto.response.PlanoGetResponseDto;
import br.com.ifba.fitpay.api.features.plano.domain.dto.request.PlanoPostRequestDto;
import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.service.IPlanoService;
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
@RequestMapping("/planos") // Define o prefixo da URL: http://localhost:8080/planos
@RequiredArgsConstructor // Cria o construtor automaticamente
public class PlanoController {

    private final IPlanoService planoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanoGetResponseDto> save(@RequestBody @Valid PlanoPostRequestDto planoDto) {

        // DTO -> Entity
        Plano plano = objectMapperUtil.map(planoDto, Plano.class);

        // Salva
        Plano planoSalvo = planoService.save(plano);

        // Entity -> DTO Response
        PlanoGetResponseDto response = objectMapperUtil.map(planoSalvo, PlanoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanoGetResponseDto> findById(@PathVariable("id") Long id) {

        Plano planoEncontrado = planoService.findById(id);

        // Converte a Entidade (Plano) para o DTO de Resposta (PlanoGetResponseDto)
        PlanoGetResponseDto response = objectMapperUtil.map(planoEncontrado, PlanoGetResponseDto.class);

        // Retorna status 200 (OK) com o objeto no corpo
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PlanoGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.planoService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, PlanoGetResponseDto.class)));
    }

    @PutMapping(path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody @Valid PlanoPutRequestDto planoDto) {

        Plano plano = objectMapperUtil.map(planoDto, Plano.class);
        planoService.update(plano);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        planoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}