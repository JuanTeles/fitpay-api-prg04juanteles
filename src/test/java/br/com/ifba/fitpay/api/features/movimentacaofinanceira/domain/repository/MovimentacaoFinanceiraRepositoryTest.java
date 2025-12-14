package br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.repository;

import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.CategoriaMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.enums.TipoMovimentacao;
import br.com.ifba.fitpay.api.features.movimentacaofinanceira.domain.model.MovimentacaoFinanceira;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para Movimentacao Financeira Repository")
class MovimentacaoFinanceiraRepositoryTest {

    @Autowired
    private MovimentacaoFinanceiraRepository repository;

    // Encontrar Movimentação Financeira pela descrição.
    @Test
    @DisplayName("Deve encontrar movimentação pela descrição quando houver sucesso")
    void findByDescricao_whenSuccessful() {
        // Cria e salva uma movimentação financeira
        MovimentacaoFinanceira movimentacao = this.createMovimentacao();
        this.repository.save(movimentacao);

        // Busca pelo metodo personalizado
        Optional<MovimentacaoFinanceira> movEncontrada = this.repository.findByDescricao(movimentacao.getDescricao());

        // Verificação
        Assertions.assertThat(movEncontrada).isPresent();
        Assertions.assertThat(movEncontrada.get().getId()).isNotNull();
        Assertions.assertThat(movEncontrada.get().getValor()).isEqualTo(movimentacao.getValor());
    }

    // Tentar buscar uma movimentação que não existe.
    @Test
    @DisplayName("Deve retornar vazio quando a movimentação não for encontrada pela descrição")
    void findByDescricao_whenNotFound() {
        // Busca uma descrição que não existe no banco
        Optional<MovimentacaoFinanceira> movEncontrada = this.repository.findByDescricao("Descricao Inexistente");

        // Verificação
        Assertions.assertThat(movEncontrada).isEmpty();
    }

    // Tenta salvar uma movimentação sem dados obrigatórios.
    @Test
    @DisplayName("Deve lançar DataIntegrityViolationException quando salvar com campos obrigatórios nulos")
    void save_throwsException_whenMandatoryFieldsAreNull() {
        // Objeto vazio, sem os campos @Column(nullable=false)
        MovimentacaoFinanceira movInvalida = new MovimentacaoFinanceira();
        // Define apenas a data, deixando valor e descrição nulos
        movInvalida.setDataHora(LocalDateTime.now());

        // Verificação
        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.repository.save(movInvalida));
    }

    // Metodo auxiliar (Factory) para criar uma movimentação válida
    private MovimentacaoFinanceira createMovimentacao() {
        MovimentacaoFinanceira mov = new MovimentacaoFinanceira();
        mov.setDescricao("Compra de Equipamentos");
        mov.setValor(500.00);
        mov.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        mov.setCategoriaMovimentacao(CategoriaMovimentacao.COMPRA_MATERIAL);
        mov.setDataHora(LocalDateTime.now());
        return mov;
    }
}