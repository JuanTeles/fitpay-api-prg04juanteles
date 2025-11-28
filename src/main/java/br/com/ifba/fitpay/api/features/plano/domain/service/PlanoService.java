package br.com.ifba.fitpay.api.features.plano.domain.service;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoService {
    private final PlanoRepository planoRepository;

    public Plano save(Plano plano) {
        return planoRepository.save(plano);
    }

    public List<Plano> findAll() {
        return planoRepository.findAll();
    }
}