package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    // Metodo Save
    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Metodo FindAll
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    // Metodo Update
    public Aluno update(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Metodo Delete
    public void delete(Long id) {
        alunoRepository.deleteById(id);
    }
}