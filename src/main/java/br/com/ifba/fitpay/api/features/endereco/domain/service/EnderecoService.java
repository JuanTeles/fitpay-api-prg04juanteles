package br.com.ifba.fitpay.api.features.endereco.domain.service;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.repository.EnderecoRepository;
import br.com.ifba.fitpay.api.infraestructure.exception.BusinessException; //
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoService implements IEnderecoService { // [cite: 937]

    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public Endereco save(Endereco endereco) {
        validarRegrasNegocio(endereco);

        return enderecoRepository.save(endereco);
    }

    @Override
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    @Override
    public Endereco findById(UUID id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Endereço não encontrado com o ID: " + id));
    }

    @Override
    @Transactional
    public Endereco update(Endereco endereco) {
        // Garante que o endereço existe no banco
        this.findById(endereco.getId());

        // Aplica as validações novamente na edição
        validarRegrasNegocio(endereco);

        return enderecoRepository.save(endereco);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        enderecoRepository.deleteById(id);
    }

    /**
     * Metodo privado responsável por centralizar as regras de negócio de Endereço.
     */
    private void validarRegrasNegocio(Endereco endereco) {

        // Regra 1: Campos Obrigatórios Básicos
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
            throw new BusinessException("O Logradouro é obrigatório.");
        }
        if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
            throw new BusinessException("O Bairro é obrigatório.");
        }
        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new BusinessException("A Cidade é obrigatória.");
        }

        // Regra 2: Validação de UF (Estado)
        if (endereco.getUf() == null || endereco.getUf().length() != 2) {
            throw new BusinessException("A UF deve conter exatamente 2 letras (Ex: BA, SP).");
        }

        // Regra 3: Validação de CEP
        if (endereco.getCep() == null) {
            throw new BusinessException("O CEP é obrigatório.");
        }

        // Remove caracteres não numéricos (traço, ponto) para validar apenas os números
        String cepLimpo = endereco.getCep().replaceAll("[^0-9]", "");

        if (cepLimpo.length() != 8) {
            throw new BusinessException("O CEP deve conter 8 números.");
        }

        // Opcional: Atualiza o objeto com o CEP limpo (apenas números) para salvar padronizado no banco
        endereco.setCep(cepLimpo);
    }
}