package br.com.ifba.fitpay.api.features.plano.domain.service;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.repository.PlanoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanoService implements IPlanoService {

    private final PlanoRepository planoRepository;

    @Override
    @Transactional
    public Plano save(Plano plano) {
        validarPlano(plano);
        return planoRepository.save(plano);
    }

    @Override
    public List<Plano> findAll() {
        return planoRepository.findAll();
    }

    @Override
    public Plano findById(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Plano não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public Plano update(Plano plano) {
        // Garante que o plano existe antes de atualizar
        this.findById(plano.getId());

        validarPlano(plano);

        return planoRepository.save(plano);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        planoRepository.deleteById(id);
    }

    /**
     * Regras de validação para Planos
     */
    private void validarPlano(Plano plano) {
        if (plano.getNome() == null || plano.getNome().trim().isEmpty()) {
            throw new BusinessException("O nome do plano é obrigatório.");
        }

        if (plano.getValor() == null || plano.getValor() < 0) {
            throw new BusinessException("O valor do plano não pode ser negativo.");
        }

        if (plano.getDuracaoDias() == null || plano.getDuracaoDias() <= 0) {
            throw new BusinessException("A duração do plano deve ser de pelo menos 1 dia.");
        }
    }
}