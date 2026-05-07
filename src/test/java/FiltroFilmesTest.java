import model.Filme;
import model.FiltroFilmes;
import model.PerfilCinefilo;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CatalogoFilmesAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FiltroFilmesTest {

    @Mock private CatalogoFilmesAPI catalogoMock;

    @InjectMocks
    FiltroFilmes filtroFilmes;

    List<Filme> filmesDoCatalogo;
    PerfilCinefilo perfilCinefilo;

    Filme interestelar;
    Filme oAutoDaCompadecida;
    Filme coringa;
    Filme parasita;
    Filme shrek;
    Filme bobEsponja;

    @BeforeEach
    public void setup() {
        List<Genero> generosInterestelar = List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA);
        List<Genero> generosAutoDaCompadecida = List.of(Genero.COMEDIA);
        List<Genero> generosCoringa = List.of(Genero.DRAMA, Genero.ACAO);
        List<Genero> generosParasita = List.of(Genero.DRAMA, Genero.TERROR);
        List<Genero> generosShrek = List.of(Genero.COMEDIA, Genero.FICCAO_CIENTIFICA);
        List<Genero> generosBobEsponja = List.of(Genero.COMEDIA);
        List<Genero> generosMatrix = List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO);

        List<Filme> filmesNoHistorico = List.of(new Filme(7L,"Matrix", 1999, 136,generosMatrix, Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 100),
        new Filme(6L, "Bob Esponja", 2004, 87, generosBobEsponja, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 40));

        interestelar = new Filme(1L,"Interestelar", 2014, 169,generosInterestelar, Idioma.INGLES, ClassificacaoEtaria.LIVRE, 95);
        oAutoDaCompadecida = new Filme(2L,"O Auto da Compadecida", 2000, 104,generosAutoDaCompadecida, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 98);
        coringa = new Filme(3L, "Coringa", 2019, 122,generosCoringa, Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 92);
        parasita = new Filme(4L, "Parasita", 2019, 132,generosParasita, Idioma.JAPONES, ClassificacaoEtaria.DEZOITO, 94);
        shrek = new Filme(5L,"Shrek", 2001, 90,generosShrek, Idioma.INGLES, ClassificacaoEtaria.LIVRE, 85);
        bobEsponja = new Filme(6L, "Bob Esponja", 2004, 87,generosBobEsponja, Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 40);

        List<Idioma> idiomas = List.of(Idioma.PORTUGUES, Idioma.INGLES);
        filmesDoCatalogo = new ArrayList<>();

        perfilCinefilo =
                new PerfilCinefilo(ClassificacaoEtaria.DEZESSEIS, 90, 150, idiomas, filmesNoHistorico);
    }

    @Test
    @DisplayName("Teste 1: Deve remover o filme da lista se o usuário já o assistiu anteriormente")
    public void deve_RemoverFilme_Quando_JaFoiAssistido() throws IOException {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filmesDoCatalogo.add(shrek);
        filmesDoCatalogo.add(coringa);
        filmesDoCatalogo.add(bobEsponja);

        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);
        filtroFilmes.filtrarFilmes();

        assertNotEquals("Bob Esponja", filtroFilmes.getFilmesFiltrados().getLast().getTitulo());
    }

    @Test
    @DisplayName("Teste 2: Deve restringir filmes com classificação etária superior à permitida no perfil")
    public void deve_RemoverFilme_Quando_UltrapassarClassificacaoEtaria() throws IOException {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filmesDoCatalogo.add(shrek);
        filmesDoCatalogo.add(parasita);
        filmesDoCatalogo.add(interestelar);

        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmes();

        assertNotEquals(filmesDoCatalogo, filtroFilmes.getFilmesFiltrados());
    }

    @Test
    @DisplayName("Teste 3: Deve filtrar e remover filmes cujos idiomas não constam na lista de preferências do perfil")
    public void deve_RemoverFilme_Quando_IdiomaNaoAceito() throws IOException {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filmesDoCatalogo.add(parasita);
        filmesDoCatalogo.add(oAutoDaCompadecida);
        filmesDoCatalogo.add(coringa);

        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmes();

        assertNotEquals(filmesDoCatalogo, filtroFilmes.getFilmesFiltrados());
    }


    @Test
    @DisplayName("Teste 4: Deve excluir da lista filmes que pertençam a gêneros marcados com peso zero")
    public void deve_RemoverFilme_Quando_FilmeComPesoZero() throws IOException {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filmesDoCatalogo.add(interestelar);
        filmesDoCatalogo.add(oAutoDaCompadecida);
        filmesDoCatalogo.add(shrek);

        perfilCinefilo.cadastrarPesoDeGenero(Genero.ACAO, 1.0);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.COMEDIA, 0.5);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.DRAMA, 0.0);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 0.7);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.COMEDIA_ROMANTICA, 1.0);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.DOCUMENTARIO, 0.2);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.ROMANCE, 0.8);
        perfilCinefilo.cadastrarPesoDeGenero(Genero.TERROR, 0.5);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);

        filtroFilmes.filtrarFilmes();

        assertEquals(2,filtroFilmes.getFilmesFiltrados().size());
    }

    @Test
    @DisplayName("Teste 5: Deve retornar uma lista vazia instanciada quando o catálogo de filmes estiver vazio")
    public void deve_RetornarUmaListaVazia_Quando_ListaEstiverVazia() throws IOException {
        when(catalogoMock.buscarFilmes()).thenReturn(filmesDoCatalogo);
        filtroFilmes = new FiltroFilmes(catalogoMock, perfilCinefilo);
        assertNotNull(filtroFilmes.filtrarFilmes());
    }
}