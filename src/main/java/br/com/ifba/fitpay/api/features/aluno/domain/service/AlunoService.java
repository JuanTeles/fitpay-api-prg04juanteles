package br.com.ifba.fitpay.api.features.aluno.domain.service;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.repository.IAlunoRepository;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.repository.EnderecoRepository;
import br.com.ifba.fitpay.api.features.endereco.domain.service.IEnderecoService;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.matricula.domain.repository.IMatriculaRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService implements IAlunoService{

    private final IAlunoRepository alunoRepository;
    private final EnderecoRepository enderecoRepository;
    private final IMatriculaRepository matriculaRepository;

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

        if (aluno.getEndereco() != null) {
            Endereco novoEnd = aluno.getEndereco();

            // Busca se já existe um endereço idêntico no banco
            Optional<Endereco> enderecoExistente = enderecoRepository.findByCepAndNumeroAndComplemento(
                    novoEnd.getCep(),
                    novoEnd.getNumero(),
                    novoEnd.getComplemento()
            );

            if (enderecoExistente.isPresent()) {
                // vincula o aluno ao endereço já salvo (ID existente)
                aluno.setEndereco(enderecoExistente.get());
            } else {
                // Se não existe, o CascadeType.ALL do Aluno irá salvar o novo endereço automaticamente
            }
        }

        return alunoRepository.save(aluno);
    }

    // Listar Todos ou busca
    @Override
    public Page<Aluno> findAll(Pageable pageable, String search, StatusMatricula status) {
        // Tem busca E tem status
        if (status != null && (search != null && !search.isBlank())) {
            return alunoRepository.findByNomeOrCpfAndStatusMatricula(search, status, pageable);
        }
        // Apenas status
        else if (status != null) {
            return alunoRepository.findByStatusMatricula(status, pageable);
        }
        // Apenas busca
        else if (search != null && !search.isBlank()) {
            return alunoRepository.findByNomeContainingIgnoreCaseOrCpfContaining(search, search, pageable);
        }
        // Nenhum filtro (busca tudo)
        else {
            return alunoRepository.findAll(pageable);
        }
    }

    // Regra: Buscar por ID
    @Override
    public Aluno findById(Long id) {
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
    public void delete(Long id) {
        // Garante que existe antes de deletar
        this.findById(id);

        // Verifica se há histórico
        if (matriculaRepository.existsByAlunoId(id)) {
            throw new BusinessException("Não é possível remover este aluno pois ele possui histórico de matrículas. Tente inativá-lo.");
        }

        alunoRepository.deleteById(id);

        alunoRepository.deleteById(id);
    }

    @Override
    public Page<Aluno> findByNomeOrCpf(String search, Pageable pageable) {
        return alunoRepository.findByNomeContainingIgnoreCaseOrCpfContaining(search, search, pageable);
    }
}