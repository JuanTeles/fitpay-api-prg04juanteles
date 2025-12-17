package br.com.ifba.fitpay.api.features.contratoaluno.domain.service;

import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.repository.ContratoAlunoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContratoAlunoService implements IContratoAlunoService {

    private final ContratoAlunoRepository contratoAlunoRepository;

    @Override
    @Transactional
    public ContratoAluno save(ContratoAluno contratoAluno) {
        // Regra de Negócio: Validação de Datas
        validarDatas(contratoAluno);

        return contratoAlunoRepository.save(contratoAluno);
    }

    @Override
    public Page<ContratoAluno> findAll(Pageable pageable) {
        return contratoAlunoRepository.findAll(pageable);
    }

    @Override
    public ContratoAluno findById(Long id) {
        // BusinessException se não encontrar
        return contratoAlunoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Contrato não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public ContratoAluno update(ContratoAluno contratoAluno) {
        // Verifica se existe antes de atualizar
        // ContratoAluno contratoExistente = this.findById(contratoAluno.getId());

        // Regra de Negócio: Validação de Datas na atualização também
        validarDatas(contratoAluno);


        return contratoAlunoRepository.save(contratoAluno);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Garante que o recurso existe antes de tentar deletar
        this.findById(id);

        contratoAlunoRepository.deleteById(id);
    }

    /**
     * Metodo auxiliar privado para validar regras de data.
     * Impede que um contrato termine antes de começar.
     */
    private void validarDatas(ContratoAluno contrato) {
        if (contrato.getDataInicio() != null && contrato.getDataFim() != null) {
            if (contrato.getDataFim().isBefore(contrato.getDataInicio())) {
                throw new BusinessException("A Data Final do contrato não pode ser anterior à Data Inicial.");
            }
        }
    }
}