package br.com.ifba.fitpay.api.features.plano.domain.repository;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest                          // (1) Sobe contexto JPA em memória (H2)
@ActiveProfiles("test")               // (2) Ativa o perfil "test"
@DisplayName("Testes para Plano Repository") // (3) Nome legível para a classe de testes
class PlanoRepositoryTest {

    @Autowired                        // (4) Injeta o repositório real via Spring
    private PlanoRepository planoRepository;

    @BeforeEach                       // (5) Executado antes de cada teste
    void setUp() {
        planoRepository.deleteAll();
    }

    @AfterEach                        // (6) Executado após cada teste
    void tearDown() {
        planoRepository.deleteAll();
    }

    // -------------------------------------------------------------------------
    // Salvar
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Deve salvar um plano com sucesso e persistir todos os campos")
    void save_whenSuccessful() {
        Plano plano = createPlanoGold();

        Plano planoSalvo = planoRepository.save(plano);

        Assertions.assertThat(planoSalvo).isNotNull();                                           // (assert 1)
        Assertions.assertThat(planoSalvo.getId()).isNotNull();                                   // (assert 2) ID gerado pelo banco
        Assertions.assertThat(planoSalvo.getNome()).isEqualTo("Plano Gold");                     // (assert 3)
        Assertions.assertThat(planoSalvo.getValor()).isEqualTo(99.90);                           // (assert 4)
        Assertions.assertThat(planoSalvo.getDuracaoDias()).isEqualTo(30);                        // (assert 5)
        Assertions.assertThat(planoSalvo.getDescricao()).isEqualTo("Acesso total à academia");   // (assert 6)
    }

    // -------------------------------------------------------------------------
    // Buscar por nome
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Deve encontrar plano pelo nome quando houver sucesso")
    void findByNome_whenSuccessful() {
        Plano planoCriado = planoRepository.save(createPlanoGold());

        Optional<Plano> planoEncontrado = planoRepository.findByNome(planoCriado.getNome());

        Assertions.assertThat(planoEncontrado).isPresent();                                      // (assert 8)
        Assertions.assertThat(planoEncontrado.get().getId()).isNotNull();                         // (assert 9)
        Assertions.assertThat(planoEncontrado.get().getNome()).isEqualTo(planoCriado.getNome());  // (assert 10)
    }

    @Test
    @DisplayName("Deve retornar vazio quando o plano não for encontrado pelo nome")
    void findByNome_whenPlanoNotFound() {
        Optional<Plano> planoEncontrado = planoRepository.findByNome("Plano Inexistente");

        Assertions.assertThat(planoEncontrado).isEmpty();                                        // (assert 11)
    }

    // -------------------------------------------------------------------------
    // Listar todos
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Deve retornar lista com todos os planos cadastrados")
    void findAll_whenSuccessful() {
        planoRepository.save(createPlanoGold());
        planoRepository.save(createPlanoSilver());

        List<Plano> planos = planoRepository.findAll();

        Assertions.assertThat(planos).isNotEmpty();                                              // (assert 12)
        Assertions.assertThat(planos).hasSize(2);                                                // (assert 13)
    }

    // -------------------------------------------------------------------------
    // Atualizar
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Deve atualizar os dados de um plano quando houver sucesso")
    void update_whenSuccessful() {
        Plano planoSalvo = planoRepository.save(createPlanoGold());

        planoSalvo.setNome("Plano Platinum");
        planoSalvo.setValor(199.90);
        Plano planoAtualizado = planoRepository.save(planoSalvo);

        Assertions.assertThat(planoAtualizado.getId()).isEqualTo(planoSalvo.getId());            // (assert 14) mesmo ID
        Assertions.assertThat(planoAtualizado.getNome()).isEqualTo("Plano Platinum");            // (assert 15)
        Assertions.assertThat(planoAtualizado.getValor()).isEqualTo(199.90);                     // (assert 16)
    }

    // -------------------------------------------------------------------------
    // Deletar
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Deve deletar plano pelo ID quando houver sucesso")
    void delete_whenSuccessful() {
        Plano planoSalvo = planoRepository.save(createPlanoGold());

        planoRepository.deleteById(planoSalvo.getId());

        Optional<Plano> planoBuscado = planoRepository.findById(planoSalvo.getId());
        Assertions.assertThat(planoBuscado).isEmpty();                                           // (assert 17)
    }

    // Helpers (Object Mother)

    private Plano createPlanoGold() {
        Plano plano = new Plano();
        plano.setNome("Plano Gold");
        plano.setValor(99.90);
        plano.setDuracaoDias(30);
        plano.setDescricao("Acesso total à academia");
        return plano;
    }

    private Plano createPlanoSilver() {
        Plano plano = new Plano();
        plano.setNome("Plano Silver");
        plano.setValor(59.90);
        plano.setDuracaoDias(30);
        plano.setDescricao("Acesso básico à academia");
        return plano;
    }
}