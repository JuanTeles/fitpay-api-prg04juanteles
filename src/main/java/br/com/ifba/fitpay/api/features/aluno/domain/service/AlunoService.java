package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.repository.IAlunoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService implements IAlunoService{

    private final IAlunoRepository alunoRepository;

    // Regra: Salvar
    @Override
    @Transactional
    public Aluno save(Aluno aluno) {
        // Validação de CPF Único
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            throw new BusinessException("Já existe um aluno cadastrado com este CPF.");
        }

        // Regra de Negócio: Data de Matrícula Automática
        if (aluno.getDataMatricula() == null) {
            aluno.setDataMatricula(LocalDate.now());
        }

        return alunoRepository.save(aluno);
    }

    // Listar Todos (Sem regras complexas, apenas repassa)
    @Override
    public Page<Aluno> findAll(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    // Regra: Buscar por ID
    @Override
    public Aluno findById(UUID id) {
        // Validação de Existência com Tratamento de Erro
        return alunoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado com o ID: " + id));
    }

    // Regra: Atualizar
    @Override
    @Transactional
    public Aluno update(Aluno aluno) {
        // Verifica se o aluno existe antes de tentar atualizar
        Aluno alunoExistente = this.findById(aluno.getId());

        // Validação de CPF na Edição
        // Se o CPF mudou, verificamos se o novo CPF já pertence a OUTRA pessoa
        if (!alunoExistente.getCpf().equals(aluno.getCpf())) {
            if (alunoRepository.existsByCpfAndIdNot(aluno.getCpf(), aluno.getId())) {
                throw new BusinessException("Este CPF já está sendo usado por outro aluno.");
            }
        }

        // Mantém a data de matrícula original se não for passada na atualização
        if (aluno.getDataMatricula() == null) {
            aluno.setDataMatricula(alunoExistente.getDataMatricula());
        }

        return alunoRepository.save(aluno);
    }

    // Regra: Deletar
    @Override
    @Transactional
    public void delete(UUID id) {
        // Garante que existe antes de deletar
        this.findById(id);

        alunoRepository.deleteById(id);
    }
}