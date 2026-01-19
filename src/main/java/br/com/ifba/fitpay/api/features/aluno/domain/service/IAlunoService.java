package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAlunoService {

    Aluno save(Aluno aluno);
    Page<Aluno> findAll(Pageable pageable, String search, StatusMatricula status);
    Aluno findById(Long id);
    Aluno update(Aluno aluno);
    void delete(Long id);
    Page<Aluno> findByNomeOrCpf(String search, Pageable pageable);
}