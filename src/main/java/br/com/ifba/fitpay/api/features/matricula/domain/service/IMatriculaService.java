package br.com.ifba.fitpay.api.features.matricula.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMatriculaService {

    Matricula save(Matricula matricula);

    Page<Matricula> findAll(Pageable pageable);

    Matricula findById(Long id);

    Matricula update(Matricula matricula);

    void delete(Long id);
}