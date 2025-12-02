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

    // Metodo save
    public Plano save(Plano plano) {
        return planoRepository.save(plano);
    }

    // Metodo findAll
    public List<Plano> findAll() {
        return planoRepository.findAll();
    }

    // Metodo Update
    public Plano update(Plano plano) {
        return planoRepository.save(plano);
    }

    // Metodo Delete
    public void delete(Long id) {
        planoRepository.deleteById(id);
    }
}