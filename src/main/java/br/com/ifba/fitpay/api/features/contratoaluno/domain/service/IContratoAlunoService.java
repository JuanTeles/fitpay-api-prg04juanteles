package br.com.ifba.fitpay.api.features.contratoaluno.domain.service;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;

import java.util.List;
import java.util.UUID;

public interface IContratoAlunoService {

    ContratoAluno save(ContratoAluno contratoAluno);

    List<ContratoAluno> findAll();

    ContratoAluno findById(UUID id);

    ContratoAluno update(ContratoAluno contratoAluno);

    void delete(UUID id);
}