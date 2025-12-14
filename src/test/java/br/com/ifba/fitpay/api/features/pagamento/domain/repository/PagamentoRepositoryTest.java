package br.com.ifba.fitpay.api.features.pagamento.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.repository.IAlunoRepository;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.model.ContratoAluno;
import br.com.ifba.fitpay.api.features.contratoaluno.domain.repository.ContratoAlunoRepository;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import br.com.ifba.fitpay.api.features.pagamento.domain.enums.MetodoPagamento;
import br.com.ifba.fitpay.api.features.pagamento.domain.model.Pagamento;
import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import br.com.ifba.fitpay.api.features.plano.domain.repository.PlanoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Pagamento Repository")
class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ContratoAlunoRepository contratoRepository;

    @Autowired
    private IAlunoRepository alunoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    // Encontrar pagamentos pela referencia de periodo.
    @Test
    @DisplayName("Deve encontrar pagamentos por referência de período com sucesso")
    void findByReferenciaPeriodo_whenSuccessful() {
        // Salva Aluno e Plano para criar um Contrato
        Aluno aluno = this.alunoRepository.save(this.createAluno());
        Plano plano = this.planoRepository.save(this.createPlano());

        // Salva um Contrato para criar o Pagamento
        ContratoAluno contrato = this.createContrato(aluno, plano);
        this.contratoRepository.save(contrato);

        // Cria e Salva o Pagamento
        Pagamento pagamento = this.createPagamento(contrato);
        this.pagamentoRepository.save(pagamento);

        // Busca um pagamento pela referencia
        List<Pagamento> pagamentos = this.pagamentoRepository.findByReferenciaPeriodo("Janeiro/2025");

        // Verificação
        Assertions.assertThat(pagamentos).isNotEmpty();
        Assertions.assertThat(pagamentos.get(0).getValorPago()).isEqualTo(100.00);
        Assertions.assertThat(pagamentos.get(0).getContratoAluno()).isNotNull();
    }

    // Tenta buscar pagamentos de um periodo que não existe.
    @Test
    @DisplayName("Deve retornar lista vazia quando não houver pagamentos no período")
    void findByReferenciaPeriodo_whenNotFound() {
        // Busca um período que não existe
        List<Pagamento> pagamentos = this.pagamentoRepository.findByReferenciaPeriodo("Dezembro/1999");

        // Verificação
        Assertions.assertThat(pagamentos).isEmpty();
    }

    // Tenta salvar um pagamento sem dados obrigatórios.
    @Test
    @DisplayName("Deve lançar DataIntegrityViolationException quando salvar pagamento sem contrato ou valor")
    void save_throwsException_whenMandatoryFieldsAreNull() {
        // Pagamento sem Contrato (que é obrigatório) e sem Valor
        Pagamento pagamentoInvalido = new Pagamento();
        pagamentoInvalido.setDataPagamento(LocalDate.now());
        pagamentoInvalido.setMetodoPagamento(MetodoPagamento.PIX);

        // Verificação
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.pagamentoRepository.save(pagamentoInvalido));
    }

    // Métodos Auxiliares
    private Pagamento createPagamento(ContratoAluno contrato) {
        Pagamento p = new Pagamento();
        p.setValorPago(100.00);
        p.setReferenciaPeriodo("Janeiro/2025");
        p.setMetodoPagamento(MetodoPagamento.PIX);
        p.setDataPagamento(LocalDate.now());
        p.setContratoAluno(contrato);
        return p;
    }

    private ContratoAluno createContrato(Aluno aluno, Plano plano) {
        ContratoAluno c = new ContratoAluno();
        c.setAluno(aluno);
        c.setPlano(plano);
        c.setStatus(StatusMatricula.ATIVO);
        c.setDataInicio(LocalDate.now());
        c.setDataFim(LocalDate.now().plusMonths(12));
        return c;
    }

    private Aluno createAluno() {
        Aluno a = new Aluno();
        a.setNome("Pagador Teste");
        a.setCpf("999.888.777-66");
        a.setEmail("pagador@teste.com");
        a.setTelefone("71988887777");
        a.setDataMatricula(LocalDate.now());

        Endereco e = new Endereco();
        e.setLogradouro("Rua Pagamento");
        e.setNumero("100");
        e.setBairro("Financeiro");
        e.setCidade("Salvador");
        e.setUf("BA");
        e.setCep("40000-000");
        a.setEndereco(e);

        return a;
    }

    private Plano createPlano() {
        Plano p = new Plano();
        p.setNome("Plano Anual");
        p.setValor(100.00);
        p.setDuracaoDias(365);
        return p;
    }
}