package br.com.ifba.fitpay.api.features.contratoaluno.domain.service;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContratoAlunoService {

    ContratoAluno save(ContratoAluno contratoAluno);

    Page<ContratoAluno> findAll(Pageable pageable);

    ContratoAluno findById(Long id);

    ContratoAluno update(ContratoAluno contratoAluno);

    void delete(Long id);
}