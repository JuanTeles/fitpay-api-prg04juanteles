package br.com.ifba.fitpay.api.features.endereco.domain.service;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEnderecoService {

    Endereco save(Endereco endereco);
    Page<Endereco> findAll(Pageable pageable);
    Endereco findById(Long id);
    Endereco update(Endereco endereco);
    void delete(Long id);
    Page<Endereco> findByLogradouroOrBairro(String search, Pageable pageable);
}