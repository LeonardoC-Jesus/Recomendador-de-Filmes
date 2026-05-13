import exception.DuracaoInvalidaException;
import exception.NotaInvalidaException;
import exception.PerfilIncompletoException;
import exception.PesoInvalidoException;
import model.Filme;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PerfilCinefiloTest {

    private PerfilCinefilo perfilCinefilo;

    @Mock
    private Filme filmeMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        perfilCinefilo = new PerfilCinefilo(
                ClassificacaoEtaria.DEZOITO,
                90,
                180,
                List.of(Idioma.PORTUGUES, Idioma.INGLES),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("Teste 1: Deve criar perfil cinéfilo com atributos válidos")
    void deve_criarPerfilCinefilo_quando_dadosForemValidos() {

        assertAll(
                () -> assertEquals(
                        ClassificacaoEtaria.DEZOITO,
                        perfilCinefilo.getClassificacaoMaxima()
                ),

                () -> assertEquals(
                        90,
                        perfilCinefilo.getDuracaoMinima()
                ),

                () -> assertEquals(
                        180,
                        perfilCinefilo.getDuracaoMaxima()
                ),

                () -> assertEquals(
                        2,
                        perfilCinefilo.getIdiomas().size()
                ),

                () -> assertTrue(
                        perfilCinefilo.getHistoricoDeFilmes().isEmpty()
                )
        );
    }

    @Test
    @DisplayName("Teste 2: Deve cadastrar peso de gênero corretamente")
    void deve_cadastrarPesoDeGenero_quando_pesoForValido() {

        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.8);

        assertEquals(
                0.8,
                perfilCinefilo.getPesoPorGenero().get(Genero.ACAO)
        );
    }

    @Test
    @DisplayName("Teste 3: Deve lançar exceção quando peso for inválido")
    void deve_lancarExcecao_quando_pesoGeneroForInvalido() {

        assertThrows(
                PesoInvalidoException.class,
                () -> perfilCinefilo.cadastrarPesoDeGenero(
                        Genero.COMEDIA,
                        1.5
                )
        );
    }

    @Test
    @DisplayName("Teste 4: Deve registrar nota válida para filme")
    void deve_registrarNota_quando_notaForValida() {

        when(filmeMock.getTitulo()).thenReturn("Filme Mockado");

        perfilCinefilo.registrarNota(filmeMock, 4.5);

        assertEquals(
                4.5,
                perfilCinefilo.getMapaDeNotas().get(filmeMock)
        );
    }

    @Test
    @DisplayName("Teste 5: Deve lançar exceção quando nota for inválida")
    void deve_lancarExcecao_quando_notaForInvalida() {

        assertThrows(
                NotaInvalidaException.class,
                () -> perfilCinefilo.registrarNota(filmeMock, 6.0)
        );
    }

    @Test
    @DisplayName("Teste 6: Deve atualizar duração corretamente")
    void deve_atualizarDuracao_quando_valoresForemValidos() {

        perfilCinefilo.setDuracao(100, 200);

        assertAll(
                () -> assertEquals(
                        100,
                        perfilCinefilo.getDuracaoMinima()
                ),

                () -> assertEquals(
                        200,
                        perfilCinefilo.getDuracaoMaxima()
                )
        );
    }

    @Test
    @DisplayName("Teste 7: Deve lançar exceção quando duração for inválida")
    void deve_lancarExcecao_quando_duracaoForInvalida() {

        assertThrows(
                DuracaoInvalidaException.class,
                () -> perfilCinefilo.setDuracao(300, 100)
        );
    }

    @Test
    @DisplayName("Teste 8: Deve atualizar perfil corretamente")
    void deve_atualizarPerfil_quando_dadosForemValidos() {

        List<Idioma> idiomas = List.of(Idioma.JAPONES);

        List<Filme> historico = new ArrayList<>();

        perfilCinefilo.cadastrarPerfilCinefilo(
                ClassificacaoEtaria.DEZESSEIS,
                80,
                150,
                idiomas,
                historico
        );

        assertAll(
                () -> assertEquals(
                        ClassificacaoEtaria.DEZESSEIS,
                        perfilCinefilo.getClassificacaoMaxima()
                ),

                () -> assertEquals(
                        80,
                        perfilCinefilo.getDuracaoMinima()
                ),

                () -> assertEquals(
                        150,
                        perfilCinefilo.getDuracaoMaxima()
                ),

                () -> assertEquals(
                        Idioma.JAPONES,
                        perfilCinefilo.getIdiomas().get(0)
                )
        );
    }

    @Test
    @DisplayName("Teste 9: Deve lançar exceção quando perfil estiver incompleto")
    void deve_lancarExcecao_quando_perfilEstiverIncompleto() {

        assertThrows(
                PerfilIncompletoException.class,
                () -> perfilCinefilo.cadastrarPerfilCinefilo(
                        null,
                        90,
                        120,
                        List.of(),
                        new ArrayList<>()
                )
        );
    }
}