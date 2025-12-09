package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAlunoService {

    Aluno save(Aluno aluno);
    Page<Aluno> findAll(Pageable pageable);
    Aluno findById(UUID id);
    Aluno update(Aluno aluno);
    void delete(UUID id);

}