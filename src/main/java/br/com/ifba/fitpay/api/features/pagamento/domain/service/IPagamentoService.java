package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPagamentoService {

    Pagamento save(Pagamento pagamento);

    Page<Pagamento> findAll(Pageable pageable);

    Pagamento findById(Long id);

    Pagamento update(Pagamento pagamento);

    void delete(Long id);
}