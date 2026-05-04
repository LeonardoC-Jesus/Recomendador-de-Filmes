import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilmeTest {

    private Filme filme;

    @BeforeEach
    void setUp() {
        filme = new Filme("Oppenheimer",
                2023,
                180,
                Idioma.INGLES,
                ClassificacaoEtaria.DEZESSEIS,
                95);
        filme.setId(1L);
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

        Filme outroFilme = new Filme("Filme Diferente",
                1990,
                90,
                Idioma.JAPONES,
                ClassificacaoEtaria.DEZOITO,
                10);
        outroFilme.setId(1L);

        assertEquals(filme, outroFilme, "Filmes com o mesmo ID devem ser considerados iguais pelo equals");
        assertEquals(filme.hashCode(), outroFilme.hashCode(), "Filmes iguais devem gerar o mesmo hashCode");
    }
}