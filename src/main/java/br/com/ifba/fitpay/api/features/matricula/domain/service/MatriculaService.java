package br.com.ifba.fitpay.api.features.matricula.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.matricula.domain.repository.IMatriculaRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService implements IMatriculaService {

    private final IMatriculaRepository matriculaRepository;

    @Override
    @Transactional
    public Matricula save(Matricula matricula) {
        // Regra de Negócio: Validação de Datas
        validarDatas(matricula);

        if (matriculaRepository.existsByAlunoIdAndStatus(matricula.getAluno().getId(), StatusMatricula.ATIVO)) {
            throw new BusinessException("Este aluno já possui uma matrícula ATIVA. Cancele a anterior antes de criar uma nova.");
        }

        return matriculaRepository.save(matricula);
    }

    @Override
    public Page<Matricula> findAll(Pageable pageable) {
        return matriculaRepository.findAll(pageable);
    }

    @Override
    public Matricula findById(Long id) {
        // BusinessException se não encontrar
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Contrato não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public Matricula update(Matricula matricula) {
        // Verifica se existe antes de atualizar
        // ContratoAluno contratoExistente = this.findById(contratoAluno.getId());

        // Regra de Negócio: Validação de Datas na atualização também
        validarDatas(matricula);


        return matriculaRepository.save(matricula);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Garante que o recurso existe antes de tentar deletar
        this.findById(id);

        matriculaRepository.deleteById(id);
    }

    /**
     * Metodo auxiliar privado para validar regras de data.
     * Impede que um contrato termine antes de começar.
     */
    private void validarDatas(Matricula contrato) {
        if (contrato.getDataInicio() != null && contrato.getDataFim() != null) {
            if (contrato.getDataFim().isBefore(contrato.getDataInicio())) {
                throw new BusinessException("A Data Final do contrato não pode ser anterior à Data Inicial.");
            }
        }
    }

    @Override
    public List<Matricula> findByAluno(Long alunoId) {
        return matriculaRepository.findByAlunoIdOrderByDataInicioDesc(alunoId);
    }
}