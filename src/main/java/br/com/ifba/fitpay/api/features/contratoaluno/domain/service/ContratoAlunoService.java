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

    // Metodo save
    public ContratoAluno save(ContratoAluno contratoAluno) {
        return contratoAlunoRepository.save(contratoAluno);
    }

    // Metodo findAll
    public List<ContratoAluno> findAll() {
        return contratoAlunoRepository.findAll();
    }

    // Metodo Update
    public ContratoAluno update(ContratoAluno contratoAluno) {
        return contratoAlunoRepository.save(contratoAluno);
    }

    // Metodo Delete
    public void delete(Long id) {
        contratoAlunoRepository.deleteById(id);
    }
}