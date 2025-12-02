package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;

    // Metodo save
    public Pagamento save(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Metodo findAll
    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    // Metodo Update
    public Pagamento update(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    // Metodo Delete
    public void delete(Long id) {
        pagamentoRepository.deleteById(id);
    }
}