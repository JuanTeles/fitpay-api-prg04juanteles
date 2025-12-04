package br.com.ifba.fitpay.api.features.pagamento.domain.service;

import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import java.util.List;
import java.util.UUID;

public interface IPagamentoService {

    Pagamento save(Pagamento pagamento);

    List<Pagamento> findAll();

    Pagamento findById(UUID id);

    Pagamento update(Pagamento pagamento);

    void delete(UUID id);
}