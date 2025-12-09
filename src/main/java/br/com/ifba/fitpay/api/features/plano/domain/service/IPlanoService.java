package br.com.ifba.fitpay.api.features.plano.domain.service;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IPlanoService {

    Plano save(Plano plano);

    Page<Plano> findAll(Pageable pageable);

    Plano findById(UUID id);

    Plano update(Plano plano);

    void delete(UUID id);
}