package br.com.ifba.fitpay.api.features.plano.domain.repository;

import br.com.ifba.fitpay.api.features.plano.domain.model.Plano;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Plano Repository")
class PlanoRepositoryTest {

    @Autowired
    private PlanoRepository planoRepository;

    // Encontrar plano pelo nome.
    @Test
    @DisplayName("Deve encontrar plano pelo nome quando houver sucesso")
    void findByNome_whenSuccessful() {
        // Cria e salva um plano
        Plano planoCriado = this.createPlano();
        this.planoRepository.save(planoCriado);

        // Busca pelo metodo personalizado
        Optional<Plano> planoEncontrado = this.planoRepository.findByNome(planoCriado.getNome());

        // Verificação (AssertJ)
        Assertions.assertThat(planoEncontrado).isPresent(); // Garante que encontrou
        Assertions.assertThat(planoEncontrado.get().getId()).isNotNull(); // Garante que tem ID
        Assertions.assertThat(planoEncontrado.get().getNome()).isEqualTo(planoCriado.getNome()); // Confere o dado
    }

    // Tenta buscar um plano que não existe.
    @Test
    @DisplayName("Deve retornar vazio quando o plano não for encontrado pelo nome")
    void findByNome_whenPlanoNotFound() {
        // Busca um nome que não existe no banco
        Optional<Plano> planoEncontrado = this.planoRepository.findByNome("Plano Inexistente");

        // Verificação
        Assertions.assertThat(planoEncontrado).isEmpty();
    }

    // Tenta salvar um plano sem dados obrigatórios.
    @Test
    @DisplayName("Deve lançar DataIntegrityViolationException quando campos obrigatórios forem nulos")
    void save_throwsDataIntegrityViolationException_whenNomeAndValorAreNull() {
        // Plano vazio (sem nome e valor, que são obrigatórios no banco)
        Plano planoInvalido = new Plano();
        // Define apenas duração e descrição, deixando nome e valor null
        planoInvalido.setDuracaoDias(30);
        planoInvalido.setDescricao("Plano sem nome");

        // Verificação de Exceção
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.planoRepository.save(planoInvalido));
    }

    // Metodo auxiliar para criar um Plano válido (Padrão Object Mother/Factory)
    private Plano createPlano() {
        Plano plano = new Plano();
        plano.setNome("Plano Gold");
        plano.setValor(99.90);
        plano.setDuracaoDias(30);
        plano.setDescricao("Acesso total à academia");
        return plano;
    }
}