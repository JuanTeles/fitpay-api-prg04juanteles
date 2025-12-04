package br.com.ifba.fitpay.api.features.plano.domain.service;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import java.util.List;
import java.util.UUID;

public interface IPlanoService {

    Plano save(Plano plano);

    List<Plano> findAll();

    Plano findById(UUID id);

    Plano update(Plano plano);

    void delete(UUID id);
}