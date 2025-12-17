package br.com.ifba.fitpay.api.features.plano.domain.service;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlanoService {

    Plano save(Plano plano);
    Page<Plano> findAll(Pageable pageable);
    Plano findById(Long id);
    Plano update(Plano plano);
    void delete(Long id);
}