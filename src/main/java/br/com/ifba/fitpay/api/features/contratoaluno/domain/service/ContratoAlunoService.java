package br.com.ifba.fitpay.api.features.contratoaluno.domain.service;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.repository.ContratoAlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratoAlunoService {
    private final ContratoAlunoRepository contratoAlunoRepository;

    public ContratoAluno save(ContratoAluno contratoAluno) {
        return contratoAlunoRepository.save(contratoAluno);
    }

    public List<ContratoAluno> findAll() {
        return contratoAlunoRepository.findAll();
    }
}