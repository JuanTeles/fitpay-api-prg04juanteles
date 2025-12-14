package br.com.ifba.fitpay.api.features.aluno.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Aluno Repository")
class AlunoRepositoryTest {

    @Autowired
    private IAlunoRepository alunoRepository;

    // Encontrar aluno por Email
    @Test
    @DisplayName("Deve encontrar aluno por email quando houver sucesso")
    void findByEmail_whenSuccessful() {
        // Cria e salva um aluno
        Aluno alunoCriado = this.createAluno();
        this.alunoRepository.save(alunoCriado);

        // Busca pelo metodo personalizado
        Optional<Aluno> alunoEncontrado = this.alunoRepository.findByEmail(alunoCriado.getEmail());

        // Verificação (AssertJ)
        Assertions.assertThat(alunoEncontrado).isPresent(); // Verifica se encontrou
        Assertions.assertThat(alunoEncontrado.get().getId()).isNotNull(); // Verifica se tem ID gerado
        Assertions.assertThat(alunoEncontrado.get().getEmail()).isEqualTo(alunoCriado.getEmail()); // Compara valores
    }

    // Não encontra aluno
    @Test
    @DisplayName("Deve retornar vazio quando o aluno não for encontrado pelo email")
    void findByEmail_whenAlunoNotFound() {
        // Busca um email que não existe
        Optional<Aluno> alunoEncontrado = this.alunoRepository.findByEmail("email_inexistente@teste.com");

        // Verificação
        Assertions.assertThat(alunoEncontrado).isEmpty();
    }


    // Tenta salvar aluno sem nome
    @Test
    @DisplayName("Deve lançar ConstraintViolationException quando o nome for vazio")
    void save_throwsConstraintViolationException_whenNameIsEmpty() {
        // Aluno com nome vazio
        Aluno aluno = new Aluno();
        aluno.setCpf("123.456.789-00");
        aluno.setEmail("teste@email.com");

        // Verificação de Exceção
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.alunoRepository.save(aluno))
                .withMessageContaining("não deve estar vazio"); // Ajuste conforme a mensagem real da validação
    }

    // Metodo auxiliar para criar um Aluno válido para testes
    private Aluno createAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Juan Teles");
        aluno.setCpf("000.111.222-33");
        aluno.setEmail("juan@ifba.edu.br");
        aluno.setTelefone("75999998888");
        aluno.setDataMatricula(LocalDate.now());

        // Cria um endereço válido pois é obrigatório na entidade Aluno
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua dos Testes");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("Irece");
        endereco.setUf("BA");
        endereco.setCep("40000000");

        aluno.setEndereco(endereco);

        return aluno;
    }
}