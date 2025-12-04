package br.com.ifba.fitpay.api.features.endereco.domain.service;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import java.util.List;
import java.util.UUID;

public interface IEnderecoService {

    Endereco save(Endereco endereco);

    List<Endereco> findAll();

    Endereco findById(UUID id);

    Endereco update(Endereco endereco);

    void delete(UUID id);
}