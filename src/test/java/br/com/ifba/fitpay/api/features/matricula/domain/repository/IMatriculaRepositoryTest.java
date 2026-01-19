package br.com.ifba.fitpay.api.features.matricula.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.aluno.domain.repository.IAlunoRepository;
import br.com.ifba.fitpay.api.features.matricula.domain.enums.StatusMatricula;
import br.com.ifba.fitpay.api.features.matricula.domain.model.Matricula;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
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
@DisplayName("Testes para Contrato Aluno Repository")
class IMatriculaRepositoryTest {

    @Autowired
    private IMatriculaRepository contratoRepository;

    @Autowired
    private IAlunoRepository alunoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    // Encontrar contrato pelo status.
    @Test
    @DisplayName("Deve encontrar contratos pelo status quando houver sucesso")
    void findByStatus_whenSuccessful() {
        // Precisamos persiste Aluno e Plano
        Aluno aluno = this.alunoRepository.save(this.createAluno());
        Plano plano = this.planoRepository.save(this.createPlano());

        Matricula contrato = this.createContrato(aluno, plano);
        this.contratoRepository.save(contrato);

        // Busca o contrato pelo status da matricula
        List<Matricula> contratosAtivos = this.contratoRepository.findByStatus(StatusMatricula.ATIVO);

        // Verificação
        Assertions.assertThat(contratosAtivos).isNotEmpty();
        Assertions.assertThat(contratosAtivos.get(0).getStatus()).isEqualTo(StatusMatricula.ATIVO);
        Assertions.assertThat(contratosAtivos.get(0).getAluno().getNome()).isEqualTo(aluno.getNome());
    }

    // Tentar buscar contratos que não existem.
    @Test
    @DisplayName("Deve retornar lista vazia quando não houver contratos com o status")
    void findByStatus_whenNotFound() {
        // Buscar por status INATIVO sem ter criado nenhum
        List<Matricula> contratos = this.contratoRepository.findByStatus(StatusMatricula.INATIVO);

        // Verificação
        Assertions.assertThat(contratos).isEmpty();
    }

    // Tenta salvar um contrato sem plano e sem aluno.
    @Test
    @DisplayName("Deve lançar DataIntegrityViolationException quando salvar sem aluno ou plano")
    void save_throwsException_whenDependenciesAreNull() {
        // Cria Contrato sem Aluno e Sem Plano
        Matricula contratoInvalido = new Matricula();
        contratoInvalido.setDataInicio(LocalDate.now());
        contratoInvalido.setDataFim(LocalDate.now().plusMonths(1));
        contratoInvalido.setStatus(StatusMatricula.ATIVO);

        // Verificação
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.contratoRepository.save(contratoInvalido));
    }

    // Métodos Auxiliares para criar os Objetos
    private Matricula createContrato(Aluno aluno, Plano plano) {
        Matricula contrato = new Matricula();
        contrato.setAluno(aluno);
        contrato.setPlano(plano);
        contrato.setStatus(StatusMatricula.ATIVO);
        contrato.setDataInicio(LocalDate.now());
        contrato.setDataFim(LocalDate.now().plusMonths(6));
        return contrato;
    }

    private Aluno createAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Teste");
        aluno.setCpf("123.123.123-99");
        aluno.setEmail("aluno@teste.com");
        aluno.setTelefone("71999990000");
        aluno.setDataMatricula(LocalDate.now());

        // Aluno precisa de endereço
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua A");
        endereco.setNumero("1");
        endereco.setBairro("Centro");
        endereco.setCidade("Salvador");
        endereco.setUf("BA");
        endereco.setCep("40000000");
        aluno.setEndereco(endereco);

        return aluno;
    }

    private Plano createPlano() {
        Plano plano = new Plano();
        plano.setNome("Plano Mensal");
        plano.setValor(80.00);
        plano.setDuracaoDias(30);
        return plano;
    }
}