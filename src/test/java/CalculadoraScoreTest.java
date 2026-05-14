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
import service.CalculadoraScore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculadoraScoreTest {

    private CalculadoraScore calculadoraScore;
    private PerfilCinefilo perfilCinefilo;

    @BeforeEach
    void setUp() {
        calculadoraScore = new CalculadoraScore();
        perfilCinefilo = new PerfilCinefilo(ClassificacaoEtaria.DEZOITO,
                90,
                120,
                List.of(Idioma.PORTUGUES),
                new ArrayList<>()
        );
    }

    @Nested
    @DisplayName("Testes de Afinidade de Gênero")
    class TestesGenero {

        @Test
        @DisplayName("Teste 1: Gêneros com peso 1.0 devem somar 50 pontos no score final")
        void deve_testarScore_quando_pesoForMaximo() {
            List<Genero> generos = List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO);
            Filme filme = new Filme(
                    1L,
                    "Sci-Fi e Ação Sem Limites",
                    2024,
                    300,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    0
            );
            perfilCinefilo.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 1.0);
            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 1.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(50.0, score);
        }

        @Test
        @DisplayName("Teste 2: Gêneros com pesos baixo devem somar pontos baixos no score final")
        void deve_testarScore_quando_pesoForBaixo() {
            List<Genero> generos = List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO);
            Filme filme = new Filme(
                    1L,
                    "Sci-Fi e Ação Sem Limites",
                    2024,
                    300,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    0
            );
            perfilCinefilo.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 0.0);
            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(0.0, score);
        }

        @ParameterizedTest(name = "Peso {0} para o gênero deve resultar em score {1}")
        @CsvSource({
                "1.0, 50.0",
                "0.8, 40.0",
                "0.5, 25.0",
                "0.2, 10.0",
                "0.0, 0.0"
        })
        @DisplayName("Teste Parametrizado: Cálculo de Score por Peso de Gênero")
        void deve_CalcularScoreProporcional_AoPesoDoGenero(double peso, double scoreEsperado) {
            Filme filme = new Filme(1L, "Filme Teste", 2024, 300, List.of(Genero.ACAO), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 0);
            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, peso);

            double scoreObtido = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(scoreEsperado, scoreObtido, 0.1);
        }
    }

    @Nested
    @DisplayName("Testes de Componente de Duração")
    class TestesDuracao {

        @Test
        @DisplayName("Teste 3: Filme no limite máximo da duração deve dar componente 100")
        void deve_testarScore_quando_estiverDentroDaDuracaoPreferida() {
            List<Genero> generos = List.of(Genero.ACAO);
            Filme filme = new Filme(
                    1L,
                    "Ação Sem Limites",
                    2024,
                    120,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    0
            );
            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(20.0, score);
        }

        @Test
        @DisplayName("Teste 4: Filme acima do limite máximo da duração deve dar componente abaixo de 100")
        void deve_testarScore_quando_estiverAcimaDaDuracaoPreferida() {
            List<Genero> generos = List.of(Genero.ACAO);
            Filme filme = new Filme(
                    1L,
                    "Ação Sem Limites",
                    2024,
                    150,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    0
            );

            perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(8.0, score);
        }
    }

    @Nested
    @DisplayName("Testes de Limites de Score")
    class TestesLimite {

        @Test
        @DisplayName("Teste 5: Filme não deve passar de 100 de score")
        void deve_testarLimiteDeScore_quando_estaAcimaDoMaximo() {
            List<Genero> generos = List.of(Genero.COMEDIA);
            Filme filme = new Filme(
                    1L,
                    "Filme Absolute Cinema",
                    2024,
                    100,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    100
            );

            perfilCinefilo.cadastrarPesoDeGenero(Genero.COMEDIA, 1.0);

            Filme antigo = new Filme(1L,"Antigo", 2020, 90,generos, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100);
            perfilCinefilo.registrarNota(antigo, 5.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertTrue(score >= 0.0 && score <= 100.0);
            assertEquals(100.0, score);
        }

        @Test
        @DisplayName("Teste 6: Filme não deve ser menor que 0 de score")
        void deve_testarLimiteDeScore_quando_estaAbaixoDoMinimo() {
            List<Genero> generos = List.of(Genero.DOCUMENTARIO);
            Filme filme = new Filme(
                    1L,
                    "Filme Ruim",
                    2024,
                    500,
                    generos,
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZ,
                    0
            );
            perfilCinefilo.cadastrarPesoDeGenero(Genero.DOCUMENTARIO, 0.0);

            double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

            assertEquals(0.0, score);
        }
    }
}