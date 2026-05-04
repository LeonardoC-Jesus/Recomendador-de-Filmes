import model.Filme;
import model.FiltroFilmes;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CatalogoFilmesAPI;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FiltroFilmesTest {

    @Mock
    private CatalogoFilmesAPI catalogoMock;

    @InjectMocks
    FiltroFilmes filtroFilmes;

    List<Filme> filmesDoCatalogo;
    PerfilCinefilo perfilCinefilo;

    @BeforeEach
    public void setup() {
        List<Filme> filmesNoHistorico = List.of(new Filme("Matrix", 1999, 136, Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 100),
        new Filme("Bob Esponja", 2004, 87, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 40));

        // Filme(titulo, ano, duracao, idioma, classificacaoEtaria, popularidade)
        Filme interestelar = new Filme("Interestelar", 2014, 169, Idioma.INGLES, ClassificacaoEtaria.LIVRE, 95);
        Filme oAutoDaCompadecida = new Filme("O Auto da Compadecida", 2000, 104, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 98);
        Filme coringa = new Filme("Coringa", 2019, 122, Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 92);
        Filme parasita = new Filme("Parasita", 2019, 132, Idioma.JAPONES, ClassificacaoEtaria.DEZOITO, 94);
        Filme shrek = new Filme("Shrek", 2001, 90, Idioma.INGLES, ClassificacaoEtaria.LIVRE, 85);
        Filme bobEsponja = new Filme("Bob Esponja", 2004, 87, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 40);

        filmesDoCatalogo = Arrays.asList(interestelar, oAutoDaCompadecida, coringa, parasita, shrek,bobEsponja);

        List<Idioma> idiomas = List.of(Idioma.PORTUGUES, Idioma.INGLES);

        perfilCinefilo =
                new PerfilCinefilo(ClassificacaoEtaria.DEZESSEIS, 90, 150, idiomas, filmesNoHistorico);

        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);
    }

    @Test
    public void deve_RemoverFilme_Quando_JaFoiAssistido() {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);
        filtroFilmes.filtrarFilmesJaAssistidos();

        assertNotEquals("Bob Esponja", filtroFilmes.getFilmesFiltrados().getLast().getTitulo());
    }

    @Test
    public void deve_RemoverFilme_Quando_UltrapassarClassificacaoEtaria() {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmesPorClassificacao();

        assertNotEquals(filmesDoCatalogo, filtroFilmes.getFilmesFiltrados());
    }

    @Test
    public void deve_RemoverFilme_Quando_IdiomaNaoAceito() {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmesPorIdioma();

        assertNotEquals(filmesDoCatalogo, filtroFilmes.getFilmesFiltrados());
    }


    @Test
    public void deve_RemoverFilme_Quando_FilmeComPesoZero() {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmesPorGenero();

        assertNotEquals(filmesDoCatalogo, filtroFilmes.getFilmesFiltrados());
    }
}
