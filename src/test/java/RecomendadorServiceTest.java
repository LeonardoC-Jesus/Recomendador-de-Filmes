import model.*;
import model.enums.ClassificacaoEtaria;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.*;
import util.GeradorAleatorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecomendadorServiceTest {

    @Mock
    private CatalogoFilmesAPI catalogoFilmesAPI;
    // Justificativa: Evita chamadas de rede lentas e garante um catálogo controlado para o teste.

    @Mock
    private HistoricoUsuarioRepository historicoUsuarioRepository;
    // Justificativa: Impede a escrita real em banco de dados ou disco durante a execução dos testes.

    @Mock
    private NotificadorPush notificadorPush;
    // Justificativa: Garante que notificações não sejam enviadas de verdade aos usuários durante o desenvolvimento.

    @Mock
    private GeradorAleatorio geradorAleatorio;
    // Justificativa: Torna o comportamento aleatório determinante, permitindo testar sorteios com previsibilidade.

    private CalculadoraScore calculadoraScore;
    private FiltroFilmes filtroFilmes;
    private Usuario maria;

    @InjectMocks
    private RecomendadorService recomendadorService;

    @BeforeEach
    void setUp() {
        PerfilCinefilo perfilMaria = new PerfilCinefilo(
                ClassificacaoEtaria.DEZOITO,
                90,
                150,
                List.of(Idioma.PORTUGUES, Idioma.INGLES),
                new ArrayList<>()
        );

        maria = new Usuario("Maria", 20, true);
        maria.setPerfilCinefilo(perfilMaria);

        calculadoraScore = new CalculadoraScore();

        filtroFilmes = new FiltroFilmes(catalogoFilmesAPI, maria.getPerfilCinefilo());
    }

    @Test
    @DisplayName("Teste 1: TopN = 2, deve retornar exatamente 2 itens")
    void deve_RetornarDoisItens_Quando_TopNForDois() {

        List<Filme> listaFilmes = Arrays.asList(
                new Filme(1L, "F1", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100),
                new Filme(2L, "F2", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100),
                new Filme(3L, "F3", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100),
                new Filme(4L, "F4", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100),
                new Filme(5L, "F5", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100)
        );

        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(listaFilmes);

        List<Recomendacao> resultado = recomendadorService.recomendar(maria, 2);

        assertEquals(2, resultado.size(), "O resultado deve respeitar o limite topN");
    }

    @Test
    @DisplayName("Teste 2: as recomendações devem vir por score decrescente")
    void deve_OrdenarPorScoreDesc_Quando_ScoresSaoDiferentes() {

        Filme filme1 = new Filme(1L, "Filme 1", 2024, 600, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 0);
        Filme filme2 = new Filme(2L, "Filme 2", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100);
        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(Arrays.asList(filme1, filme2));

        List<Recomendacao> resultado = recomendadorService.recomendar(maria, 5);

        assertTrue(resultado.get(0).getScoreCalculado() >= resultado.get(1).getScoreCalculado());
    }

    @Test
    @DisplayName("Teste 3: Resiliência quando catálogo lança exceção")
    void deve_RetornarVazio_Quando_APIFalha() {

        when(catalogoFilmesAPI.buscarFilmes()).thenThrow(new RuntimeException("IOException simulada"));

        List<Recomendacao> resultado = recomendadorService.recomendar(maria, 5);

        assertTrue(resultado.isEmpty());
        verify(notificadorPush, never()).enviarAviso(anyString(), any(Usuario.class));
    }

    @Test
    @DisplayName("Teste 4: deve enviar push quando habilitado")
    void deve_Notificar_Quando_PushHabilitado() {

        maria.setNotificacoesHabilitadas(true);
        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(List.of(
                new Filme(1L, "Filme", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100)
        ));

        recomendadorService.recomendar(maria, 1);

        verify(notificadorPush, atLeastOnce()).enviarAviso(anyString(), eq(maria));
    }

    @Test
    @DisplayName("Teste 5: não deve enviar push quando desabilitado")
    void deve_NaoNotificar_Quando_PushDesabilitado() {

        maria.setNotificacoesHabilitadas(false);
        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(List.of(mock(Filme.class)));

        recomendadorService.recomendar(maria, 1);

        verify(notificadorPush, never()).enviarAviso(anyString(), any(Usuario.class));
    }

    @Test
    @DisplayName("Uso de ArgumentCaptor: validar lista no repositório")
    void deve_ValidarDadosGravados_UsandoArgumentCaptor() {

        Filme f = new Filme(1L, "Filme", 2010, 148, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 90);
        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(List.of(f));
        ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);

        recomendadorService.recomendar(maria, 1);

        verify(historicoUsuarioRepository).registrarRecomendacao(captor.capture());
        List<Recomendacao> listaCapturada = captor.getValue();

        assertAll("Verificação da Recomendação Capturada",
                () -> assertEquals(1, listaCapturada.size()),
                () -> assertEquals("Filme", listaCapturada.get(0).getFilme().getTitulo())
        );
    }

    @Test
    @DisplayName("Teste surpreenda-me: retorna filme no índice 2 sorteado")
    void deve_RetornarFilmeSorteado_NoModoSurpreendaMe() {

        Filme filme = new Filme(3L, "Filme", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100);
        List<Filme> filmes = Arrays.asList(mock(Filme.class), mock(Filme.class), filme);

        when(catalogoFilmesAPI.buscarFilmes()).thenReturn(filmes);

        when(geradorAleatorio.sortear(anyInt())).thenReturn(2);

        Optional<Filme> resultado = recomendadorService.recomendarAleatorio();

        assertTrue(resultado.isPresent());
        assertEquals(filme, resultado.get());
    }
}