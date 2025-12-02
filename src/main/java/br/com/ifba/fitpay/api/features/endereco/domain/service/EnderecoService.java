package br.com.ifba.fitpay.api.features.endereco.domain.service;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.endereco.domain.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    // Metodo save
    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Metodo findAll
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    // Metodo Update
    public Endereco update(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Metodo Delete
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }
}