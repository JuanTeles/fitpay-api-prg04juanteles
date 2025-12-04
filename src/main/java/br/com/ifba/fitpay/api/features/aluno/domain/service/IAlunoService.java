package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;

import java.util.List;
import java.util.UUID;

public interface IAlunoService {

    Aluno save(Aluno aluno);

    List<Aluno> findAll();

    Aluno findById(UUID id);

    Aluno update(Aluno aluno);

    void delete(UUID id);

}