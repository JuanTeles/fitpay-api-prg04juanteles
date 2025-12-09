package br.com.ifba.fitpay.api.features.endereco.domain.service;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IEnderecoService {

    Endereco save(Endereco endereco);

    public Page<Endereco> findAll(Pageable pageable);

    Endereco findById(UUID id);

    Endereco update(Endereco endereco);

    void delete(UUID id);
}