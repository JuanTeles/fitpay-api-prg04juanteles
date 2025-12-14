package br.com.ifba.fitpay.api.features.endereco.domain.repository;

import br.com.ifba.fitpay.api.features.endereco.domain.model.Endereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Endereco Repository")
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Busca endereço valido
    @Test
    @DisplayName("Deve encontrar endereços por CEP com sucesso")
    void findByCep_whenSuccessful() {
        // Cria e salva um endereço
        Endereco endereco = this.createEndereco();
        this.enderecoRepository.save(endereco);

        // Busca o endereço pelo cep
        List<Endereco> enderecosEncontrados = this.enderecoRepository.findByCep("44000-000");

        // Verificação
        Assertions.assertThat(enderecosEncontrados).isNotEmpty();
        Assertions.assertThat(enderecosEncontrados.get(0).getCidade()).isEqualTo("Feira de Santana");
        Assertions.assertThat(enderecosEncontrados.get(0).getUf()).isEqualTo("BA");
    }

    // Tenta buscar um cep inexistente
    @Test
    @DisplayName("Deve retornar lista vazia quando CEP não for encontrado")
    void findByCep_whenNotFound() {
        // Busca o endereco por um cep invalido
        List<Endereco> enderecos = this.enderecoRepository.findByCep("00000-000");

        // Verificação
        Assertions.assertThat(enderecos).isEmpty();
    }

    // Tenta salvar enreceço sem campos obrigatórios
    @Test
    @DisplayName("Deve lançar DataIntegrityViolationException ao salvar endereço com campos obrigatórios nulos")
    void save_throwsException_whenMandatoryFieldsAreNull() {
        // Endereço vazio (Logradouro, Bairro, Cidade etc são @Column(nullable=false))
        Endereco enderecoInvalido = new Endereco();
        enderecoInvalido.setComplemento("Casa sem rua");

        // Verificação
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.enderecoRepository.save(enderecoInvalido));
    }

    // Metodo Auxiliar para criar Endereco
    private Endereco createEndereco() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Av. Maria Quitéria");
        endereco.setNumero("1500");
        endereco.setBairro("Centro");
        endereco.setCidade("Feira de Santana");
        endereco.setUf("BA");
        endereco.setCep("44000-000");
        return endereco;
    }
}