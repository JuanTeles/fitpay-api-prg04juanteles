package br.com.ifba.fitpay.api.features.matricula.domain.service;

import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMatriculaService {

    Matricula save(Matricula matricula, MetodoPagamento metodoPagamento);
    Page<Matricula> findAll(Pageable pageable);
    Matricula findById(Long id);
    Matricula update(Matricula matricula);
    void delete(Long id);
    List<Matricula> findByAluno(Long alunoId);
    long countNovasMatriculasNoMes();
    long countMatriculasARenovar(int dias);
}