import model.Filme;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("Teste 1: Gêneros com peso 1.0 devem somar 50 pontos no score final")
    void deve_testarScore_quando_pesoForMaximo() {
        Filme filme = new Filme(
                "Sci-Fi e Ação Sem Limites",
                2024,
                300,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                0
        );
        filme.setGeneros(List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 1.0);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 1.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertEquals(50.0, score);
    }

    @Test
    @DisplayName("Teste 2: Gêneros com pesos baixo devem somar pontos baixos no score final")
    void deve_testarScore_quando_pesoForBaixo() {
        Filme filme = new Filme(
                "Sci-Fi e Ação Sem Limites",
                2024,
                300,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                0
        );
        filme.setGeneros(List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 0.0);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertEquals(0.0, score);
    }


    @Test
    @DisplayName("Teste 3: Filme no limite máximo da duração deve dar componente 100")
    void deve_testarScore_quando_estiverDentroDaDuracaoPreferida() {
        Filme filme = new Filme(
                "Ação Sem Limites",
                2024,
                120,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                0
        );
        filme.setGeneros(List.of(Genero.ACAO));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertEquals(20.0, score);
    }

    @Test
    @DisplayName("Teste 4: Filme acima do limite máximo da duração deve dar componente abaixo de 100")
    void deve_testarScore_quando_estiverAcimaDaDuracaoPreferida() {
        Filme filme = new Filme(
                "Ação Sem Limites",
                2024,
                150,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                0
        );
        filme.setGeneros(List.of(Genero.ACAO));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 0.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertEquals(8.0, score);
    }

    @Test
    @DisplayName("Teste 5: Filme não deve passar de 100 de score")
    void deve_testarLimiteDeScore_quando_estaAcimaDoMaximo() {
        Filme filme = new Filme(
                "Filme Absolute Cinema",
                2024,
                100,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                100
        );
        filme.setGeneros(List.of(Genero.COMEDIA));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.COMEDIA, 1.0);

        Filme antigo = new Filme("Antigo", 2020, 90, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100);
        antigo.setGeneros(List.of(Genero.COMEDIA));
        perfilCinefilo.registrarNota(antigo, 5.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertTrue(score >= 0.0 && score <= 100.0);
        assertEquals(100.0, score);
    }

    @Test
    @DisplayName("Teste 6: Filme não deve ser menor que 0 de score")
    void deve_testarLimiteDeScore_quando_estaAbaixoDoMinimo() {
        Filme filme = new Filme(
                "Filme Ruim",
                2024,
                500,
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZ,
                0
        );
        filme.setGeneros(List.of(Genero.DOCUMENTARIO));
        perfilCinefilo.cadastrarPesoDeGenero(Genero.DOCUMENTARIO, 0.0);

        double score = calculadoraScore.calcularScore(filme, perfilCinefilo);

        assertEquals(0.0, score);
    }
}
