import exception.DuracaoInvalidaException;
import exception.NotaInvalidaException;
import exception.PesoInvalidoException;
import model.Filme;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Nested
    @DisplayName("Cenários de Pesos Válidos")
    class CenariosPesos {

        @Test
        @DisplayName("Teste 1: Deve criar perfil com pesos válidos")
        void deve_criarPerfil_quando_pesosForemValidos() {

            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.8);
            perfilCinefilo.cadastrarPesoDeGenero(Genero.COMEDIA, 0.5);

            assertAll(

                    () -> assertEquals(
                            0.8,
                            perfilCinefilo.getPesoPorGenero().get(Genero.ACAO)
                    ),

                    () -> assertEquals(
                            0.5,
                            perfilCinefilo.getPesoPorGenero().get(Genero.COMEDIA)
                    )
            );
        }
    }

    @Nested
    @DisplayName("Cenários de Lançamento de exceções")
    class CenariosExcecoes {

        @Test
        @DisplayName("Teste 2: Deve lançar exceção quando peso for menor que 0")
        void deve_lancarExcecao_quando_pesoForMenorQueZero() {

            assertThrows(
                    PesoInvalidoException.class,
                    () -> perfilCinefilo.cadastrarPesoDeGenero(
                            Genero.DRAMA,
                            -0.1
                    )
            );
        }

        @Test
        @DisplayName("Teste 3: Deve lançar exceção quando peso for maior que 1")
        void deve_lancarExcecao_quando_pesoForMaiorQueUm() {

            assertThrows(
                    PesoInvalidoException.class,
                    () -> perfilCinefilo.cadastrarPesoDeGenero(
                            Genero.DRAMA,
                            1.5
                    )
            );
        }

        @Test
        @DisplayName("Teste 4: Deve lançar exceção quando duração mínima for maior que máxima")
        void deve_lancarExcecao_quando_duracaoMinimaForMaiorQueMaxima() {

            assertThrows(
                    DuracaoInvalidaException.class,
                    () -> perfilCinefilo.setDuracao(300, 100)
            );
        }

        @Test
        @DisplayName("Teste 5: Deve lançar exceção quando nota for menor que 1")
        void deve_lancarExcecao_quando_notaForMenorQueUm() {

            assertThrows(
                    NotaInvalidaException.class,
                    () -> perfilCinefilo.registrarNota(filmeMock, 0.5)
            );
        }

        @Test
        @DisplayName("Teste 6: Deve lançar exceção quando nota for maior que 5")
        void deve_lancarExcecao_quando_notaForMaiorQueCinco() {

            assertThrows(
                    NotaInvalidaException.class,
                    () -> perfilCinefilo.registrarNota(filmeMock, 5.5)
            );
        }

        @ParameterizedTest(name = "Nota {0} deve lançar NotaInvalidaException")
        @CsvSource({
                "0.0",
                "0.9",
                "5.1",
                "10.0",
                "-1.0"
        })
        @DisplayName("Teste Parametrizado: Validação de Notas Fora do Intervalo")
        void deve_LancarExcecao_Quando_NotasForemInvalidas(double notaErrada) {
            assertThrows(NotaInvalidaException.class, () -> {
                perfilCinefilo.registrarNota(filmeMock, notaErrada);
            });
        }
    }

    @Nested
    @DisplayName("Cenários de Filmes Assistidos")
    class CenariosHistorico {

        @Test
        @DisplayName("Teste 7: Filme assistido deve aparecer no histórico")
        void deve_adicionarFilmeAoHistorico() {

            perfilCinefilo.getHistoricoDeFilmes().add(filmeMock);

            assertTrue(
                    perfilCinefilo.getHistoricoDeFilmes().contains(filmeMock)
            );
        }
    }
}