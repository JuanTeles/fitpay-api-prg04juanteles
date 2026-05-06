package br.com.ifba.fitpay.api.features.aluno.domain.repository;

import br.com.ifba.fitpay.api.features.aluno.domain.model.Aluno;
import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Aluno Repository")
class AlunoRepositoryTest {

    @Autowired
    private IAlunoRepository alunoRepository;

    private Aluno aluno;
    private Endereco endereco;

    // Executado antes de cada teste
    @BeforeEach
    @DisplayName("Configuração inicial antes de cada teste")
    void setUp() {
        // Inicializa objeto de teste
        aluno = new Aluno();
        aluno.setNome("Juan Teles");
        aluno.setCpf("000.111.222-33");
        aluno.setEmail("juan@ifba.edu.br");
        aluno.setTelefone("75999998888");
        aluno.setDataMatricula(LocalDate.now());

        // Cria endereço válido
        endereco = new Endereco();
        endereco.setLogradouro("Rua dos Testes");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("Irece");
        endereco.setUf("BA");
        endereco.setCep("40000000");

        aluno.setEndereco(endereco);
    }

    // Executado após cada teste
    @AfterEach
    @DisplayName("Limpeza após cada teste")
    void tearDown() {
        // Limpa dados após cada teste
        aluno = null;
        endereco = null;
    }

    // Teste com assertNotNull
    @Test
    @DisplayName("Deve salvar aluno com sucesso e verificar se ID não é nulo")
    void save_whenSuccessful_assertNotNull() {
        // Salva o aluno
        Aluno alunoSalvo = this.alunoRepository.save(aluno);

        // Asserção: assertNotNull
        assertNotNull(alunoSalvo.getId(), "O ID do aluno não deve ser nulo após salvar");
        assertNotNull(alunoSalvo.getNome(), "O nome do aluno não deve ser nulo");
    }

    // Teste com assertEquals
    @Test
    @DisplayName("Deve encontrar aluno por email e verificar dados")
    void findByEmail_whenSuccessful_assertEquals() {
        // Salva o aluno
        this.alunoRepository.save(aluno);

        // Busca pelo email
        Optional<Aluno> alunoEncontrado = this.alunoRepository.findByEmail(aluno.getEmail());

        // Asserção: assertEquals
        assertTrue(alunoEncontrado.isPresent(), "Aluno deve ser encontrado pelo email");
        assertEquals(aluno.getEmail(), alunoEncontrado.get().getEmail(), "O email deve ser igual");
        assertEquals(aluno.getNome(), alunoEncontrado.get().getNome(), "O nome deve ser igual");
    }

    // Teste com assertNull e assertNotNull
    @Test
    @DisplayName("Deve retornar vazio quando aluno não for encontrado")
    void findByEmail_whenNotFound_assertNull() {
        // Busca um email inexistente
        Optional<Aluno> alunoEncontrado = this.alunoRepository.findByEmail("inexistente@teste.com");

        // Asserção: assertNull (isEmpty equivalente a Optional)
        assertFalse(alunoEncontrado.isPresent(), "Aluno não deve ser encontrado");
        assertTrue(alunoEncontrado.isEmpty(), "Optional deve estar vazio");
    }

    // Teste com assertTrue
    @Test
    @DisplayName("Deve validar se aluno foi salvo com sucesso")
    void save_checkIfDataPersisted_assertTrue() {
        // Salva o aluno
        Aluno alunoSalvo = this.alunoRepository.save(aluno);

        // Busca novamente do banco
        Optional<Aluno> alunoRecuperado = this.alunoRepository.findById(alunoSalvo.getId());

        // Asserção: assertTrue
        assertTrue(alunoRecuperado.isPresent(), "Aluno deve estar persistido no banco");
        assertTrue(alunoRecuperado.get().getId() > 0, "ID do aluno deve ser maior que zero");
    }

    // Teste com assertFalse
    @Test
    @DisplayName("Deve deletar aluno e verificar remoção")
    void delete_whenSuccessful_assertFalse() {
        // Salva e recupera o aluno
        Aluno alunoSalvo = this.alunoRepository.save(aluno);
        Long alunoId = alunoSalvo.getId();

        // Deleta o aluno
        this.alunoRepository.deleteById(alunoId);

        // Busca o aluno deletado
        Optional<Aluno> alunoEncontrado = this.alunoRepository.findById(alunoId);

        // Asserção: assertFalse
        assertFalse(alunoEncontrado.isPresent(), "Aluno não deve existir após ser deletado");
    }

    // Teste parametrizado com diferentes CPFs
    @ParameterizedTest
    @ValueSource(strings = {"111.222.333-44", "555.666.777-88", "999.888.777-66"})
    @DisplayName("Deve validar salvamento com diferentes CPFs")
    void save_withDifferentCPFs_ParameterizedTest(String cpf) {
        // Modifica o CPF
        aluno.setCpf(cpf);

        // Salva o aluno
        Aluno alunoSalvo = this.alunoRepository.save(aluno);

        // Asserção: assertEquals
        assertEquals(cpf, alunoSalvo.getCpf(), "O CPF deve ser igual ao definido");
        assertNotNull(alunoSalvo.getId(), "ID não deve ser nulo");
    }

    // Teste com atualização usando assertTrue e assertEquals
    @Test
    @DisplayName("Deve atualizar aluno e verificar alterações")
    void update_whenSuccessful_assertTrue() {
        // Salva o aluno
        Aluno alunoSalvo = this.alunoRepository.save(aluno);

        // Modifica dados
        alunoSalvo.setNome("Juan Teles Atualizado");
        alunoSalvo.setTelefone("75988888888");
        Aluno alunoAtualizado = this.alunoRepository.save(alunoSalvo);

        // Asserção: assertTrue e assertEquals
        assertTrue(alunoAtualizado.getId().equals(alunoSalvo.getId()), "ID deve permanecer o mesmo");
        assertEquals("Juan Teles Atualizado", alunoAtualizado.getNome(), "Nome deve ser atualizado");
        assertEquals("75988888888", alunoAtualizado.getTelefone(), "Telefone deve ser atualizado");
    }

    // Teste com validação de constraints
    @Test
    @DisplayName("Deve lançar exceção quando salvar aluno sem nome")
    void save_throwsException_whenNameIsEmpty() {
        // Cria um novo endereço local apenas para este teste (isolado do compartilhado)
        Endereco enderecoLocal = new Endereco();
        enderecoLocal.setLogradouro("Rua Teste Validacao");
        enderecoLocal.setNumero("99");
        enderecoLocal.setBairro("Bairro Teste");
        enderecoLocal.setCidade("Cidade Teste");
        enderecoLocal.setUf("BA");
        enderecoLocal.setCep("50000000");

        // Cria aluno sem nome (falta validação obrigatória)
        Aluno alunoSemNome = new Aluno();
        // alunoSemNome.setNome("Juan Teles");
        alunoSemNome.setCpf("123.456.789-00");
        alunoSemNome.setEmail("teste@email.com");
        alunoSemNome.setTelefone("75999999999"); 
        alunoSemNome.setDataMatricula(LocalDate.now()); 
        alunoSemNome.setEndereco(enderecoLocal);

        // Asserção: verifica se lança exceção de validação ou integridade
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> this.alunoRepository.save(alunoSemNome));
    }
}