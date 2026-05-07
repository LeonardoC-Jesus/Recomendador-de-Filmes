import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmeTest {

    private Filme filme;

    @BeforeEach
    void setUp() {
        List<Genero> generos = List.of(Genero.DRAMA);
        filme = new Filme(1L,
                "Oppenheimer",
                2023,
                180,
                generos,
                Idioma.INGLES,
                ClassificacaoEtaria.DEZESSEIS,
                95);
    }

    @Test
    @DisplayName("Teste 1: Filme deve ter todos os atributos preenchidos corretamente")
    void deve_testarFilme_quando_todosOsAtributosSaoPreenchidos() {
        assertAll("Verificação de atributos do filme",
                () -> assertEquals(1L, filme.getId(), "ID incorreto"),
                () -> assertEquals("Oppenheimer", filme.getTitulo(), "Título incorreto"),
                () -> assertEquals(2023, filme.getAno(), "Ano incorreto"),
                () -> assertEquals(180, filme.getDuracao(), "Duração incorreta"),
                () -> assertEquals(Idioma.INGLES, filme.getIdioma(), "Idioma incorreto"),
                () -> assertEquals(ClassificacaoEtaria.DEZESSEIS, filme.getClassificacaoEtaria(), "Classificação incorreta"),
                () -> assertEquals(95, filme.getPopularidade(), "Popularidade incorreta"),
                () -> assertNotNull(filme.getGeneros(), "A lista de gêneros não deve ser nula")
        );
    }

    @Test
    @DisplayName("Deve considerar dois filmes iguais quando possuem o mesmo ID")
    void deve_considerarFilmesIguais_quando_temMesmoId() {
        List<Genero> generos = List.of(Genero.FICCAO_CIENTIFICA);
        Filme outroFilme = new Filme(1L,
                "Filme Diferente",
                1990,
                90,
                generos,
                Idioma.JAPONES,
                ClassificacaoEtaria.DEZOITO,
                10);

        assertEquals(filme, outroFilme, "Filmes com o mesmo ID devem ser considerados iguais pelo equals");
        assertEquals(filme.hashCode(), outroFilme.hashCode(), "Filmes iguais devem gerar o mesmo hashCode");
    }
}