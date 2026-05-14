import model.*;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import service.*;
import util.GeradorAleatorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecomendadorServiceTest {

    @Mock private CatalogoFilmesAPI catalogoFilmesAPI;
    @Mock private HistoricoUsuarioRepository historicoUsuarioRepository;
    @Mock private NotificadorPush notificadorPush;
    @Mock private GeradorAleatorio geradorAleatorio;

    @Spy
    private CalculadoraScore calculadoraScore = new CalculadoraScore();
    private PerfilCinefilo perfilMaria;
    private Usuario maria;

    Filme interestelar;
    Filme oAutoDaCompadecida;

    @InjectMocks
    private RecomendadorService recomendadorService;

    @BeforeEach
    void setUp() {
        List<Filme> historicoFilmes = new ArrayList<>();
        interestelar = new Filme(1L,"Interestelar", 2014, 169,List.of(), Idioma.INGLES, ClassificacaoEtaria.LIVRE, 95);
        oAutoDaCompadecida = new Filme(2L,"O Auto da Compadecida", 2000, 104,List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 98);

        historicoFilmes.add(interestelar);
        historicoFilmes.add(oAutoDaCompadecida);

        perfilMaria = new PerfilCinefilo(
                ClassificacaoEtaria.DEZOITO,
                90,
                150,
                List.of(Idioma.PORTUGUES, Idioma.INGLES),
                historicoFilmes
        );

        maria = new Usuario("Maria", 20, perfilMaria, true);

        calculadoraScore = new CalculadoraScore();
    }

    @Nested
    @DisplayName("Regras de Ranking e Seleção")
    class RegrasRanking {

        @Test
        @DisplayName("Teste 1: TopN = 2, deve retornar exatamente 2 itens")
        void deve_RetornarDoisItens_Quando_TopNForDois() throws IOException {
            List<Filme> listaFilmes = Arrays.asList(
                    new Filme(112L, "F1", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100),
                    new Filme(211L, "F2", 2023, 115, List.of(), Idioma.INGLES, ClassificacaoEtaria.DOZE, 85),
                    new Filme(335L, "F3", 2022, 110, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.DEZOITO, 93),
                    new Filme(405L, "F4", 2021, 105, List.of(), Idioma.ESPANHOL, ClassificacaoEtaria.LIVRE, 100),
                    new Filme(534L, "F5", 2020, 100, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 50)
            );

            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(listaFilmes);
            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );

            List<Recomendacao> resultado = recomendadorService.recomendar(maria, 2);

            assertEquals(2, resultado.size(), "O resultado deve respeitar o limite topN");
        }

        @Test
        @DisplayName("Teste 2: as recomendações devem vir por score decrescente")
        void deve_OrdenarPorScoreDesc_Quando_ScoresSaoDiferentes() throws IOException {
            List<Filme> listaFilmes = Arrays.asList(
                    new Filme(195L, "Filme 1", 2024, 90, List.of(Genero.FICCAO_CIENTIFICA), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 0),
                    new Filme(209L, "Filme 2", 2024, 120, List.of(Genero.ACAO), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100)
            );

            perfilMaria.cadastrarPesoDeGenero(Genero.ACAO, 1.0);
            perfilMaria.cadastrarPesoDeGenero(Genero.COMEDIA, 0.5);
            perfilMaria.cadastrarPesoDeGenero(Genero.DRAMA, 0.0);
            perfilMaria.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 0.7);
            perfilMaria.cadastrarPesoDeGenero(Genero.COMEDIA_ROMANTICA, 1.0);
            perfilMaria.cadastrarPesoDeGenero(Genero.DOCUMENTARIO, 0.2);
            perfilMaria.cadastrarPesoDeGenero(Genero.ROMANCE, 0.8);
            perfilMaria.cadastrarPesoDeGenero(Genero.TERROR, 0.5);

            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(listaFilmes);
            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );

            List<Recomendacao> resultado = recomendadorService.recomendar(maria, 5);

            assertTrue(resultado.get(0).getScoreCalculado() >= resultado.get(1).getScoreCalculado());
        }
    }

    @Nested
    @DisplayName("Notificações e Efeitos Colaterais")
    class Notificacoes {

        @Test
        @DisplayName("Teste 4: deve enviar push quando habilitado")
        void deve_Notificar_Quando_PushHabilitado() throws IOException {
            maria.setNotificacoesHabilitadas(true);
            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(List.of(
                    new Filme(176L, "Filme", 2024, 120, List.of(Genero.ROMANCE), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100)
            ));

            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );
            recomendadorService.recomendar(maria, 1);

            verify(notificadorPush, times(1)).enviarAviso(anyString(), eq(maria));
        }

        @Test
        @DisplayName("Teste 5: não deve enviar push quando desabilitado")
        void deve_NaoNotificar_Quando_PushDesabilitado() throws IOException {
            Filme f = new Filme(197L, "Filme", 2010, 148, List.of(Genero.DOCUMENTARIO), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 90);
            List<Filme> filmes = List.of(f);

            maria.setNotificacoesHabilitadas(false);
            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(filmes);

            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );
            recomendadorService.recomendar(maria, 1);

            verify(notificadorPush, never()).enviarAviso(anyString(), eq(maria));
        }

        @Test
        @DisplayName("Uso de ArgumentCaptor: validar lista no repositório")
        void deve_ValidarDadosGravados_UsandoArgumentCaptor() throws IOException {
            Filme f = new Filme(197L, "Filme", 2010, 148, List.of(Genero.DOCUMENTARIO), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 90);
            List<Filme> filmes = List.of(f);

            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(filmes);
            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );
            ArgumentCaptor<List<Recomendacao>> captor = ArgumentCaptor.forClass(List.class);

            recomendadorService.recomendar(maria, 1);

            verify(historicoUsuarioRepository).registrarRecomendacao(captor.capture());
            List<Recomendacao> listaCapturada = captor.getValue();

            assertAll("Verificação da Recomendação Capturada",
                    () -> assertEquals(1, listaCapturada.size()),
                    () -> assertEquals("Filme", listaCapturada.get(0).getFilme().getTitulo())
            );
        }
    }

    @Nested
    @DisplayName("Resiliência e Algoritmos Alternativos")
    class Resiliencia {

        @Test
        @DisplayName("Teste 3: Resiliência quando catálogo lança exceção")
        void deve_RetornarVazio_Quando_APIFalha() throws IOException  {
            when(catalogoFilmesAPI.buscarFilmes()).thenThrow(new IOException("API offline"));
            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );

            List<Recomendacao> resultado = recomendadorService.recomendar(maria, 5);

            assertTrue(resultado.isEmpty());
            verify(notificadorPush, never()).enviarAviso(anyString(), any(Usuario.class));
        }

        @Test
        @DisplayName("Teste surpreenda-me: retorna filme no índice sorteado")
        void deve_RetornarFilmeSorteado_NoModoSurpreendaMe() throws IOException{
            Filme filme1 = new Filme(356L, "Filme1", 2024, 120, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 100);
            Filme filme2 = new Filme(357L, "Filme2", 2025, 100, List.of(), Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 90);
            List<Filme> filmes = List.of(filme1, filme2);

            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(filmes);

            when(geradorAleatorio.sortear(anyInt())).thenReturn(0);

            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraScore
            );

            Filme resultado = recomendadorService.recomendarAleatorio();

            assertNotNull(resultado);
            assertEquals(filme1, resultado);
        }
    }

    @Nested
    @DisplayName("Verificações de Integração (Spy)")
    class TestesSpy {

        @Test
        @DisplayName("Spy: deve chamar CalculadoraScore para cada filme recomendado")
        void deve_calcularScoreUmaVezPorFilme_quando_recomendar() throws IOException {

            Filme filme1 = new Filme(
                    10L,
                    "Batman",
                    2022,
                    120,
                    List.of(Genero.ACAO),
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.LIVRE,
                    90
            );

            Filme filme2 = new Filme(
                    11L,
                    "Interestelar 2",
                    2025,
                    130,
                    List.of(Genero.FICCAO_CIENTIFICA),
                    Idioma.INGLES,
                    ClassificacaoEtaria.DOZE,
                    95
            );

            List<Filme> filmes = List.of(filme1, filme2);

            when(catalogoFilmesAPI.buscarFilmes()).thenReturn(filmes);

            CalculadoraScore calculadoraSpy = spy(new CalculadoraScore());

            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesAPI,
                    historicoUsuarioRepository,
                    perfilMaria,
                    calculadoraSpy
            );

            recomendadorService.recomendar(maria, 2);

            verify(calculadoraSpy, times(2))
                    .calcularScore(any(Filme.class), eq(perfilMaria));
        }
    }

    @Test
    @org.junit.jupiter.api.Tag("integracao")
    @DisplayName("Integração: pipeline completo deve retornar ranking esperado")
    void deve_ExecutarPipelineCompleto_Quando_GerarRecomendacoes() throws IOException {

        perfilMaria.cadastrarPesoDeGenero(Genero.ACAO, 1.0);
        perfilMaria.cadastrarPesoDeGenero(Genero.FICCAO_CIENTIFICA, 0.9);
        perfilMaria.cadastrarPesoDeGenero(Genero.DRAMA, 0.2);

        Filme filmeMaisRelevante = new Filme(
                100L,
                "Mad Max",
                2024,
                120,
                List.of(Genero.ACAO),
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DOZE,
                100
        );

        Filme filmeMedio = new Filme(
                101L,
                "Interestelar 2",
                2025,
                140,
                List.of(Genero.FICCAO_CIENTIFICA),
                Idioma.INGLES,
                ClassificacaoEtaria.DOZE,
                80
        );

        Filme filmePior = new Filme(
                102L,
                "Drama Triste",
                2020,
                200,
                List.of(Genero.DRAMA),
                Idioma.PORTUGUES,
                ClassificacaoEtaria.DEZOITO,
                20
        );

        when(catalogoFilmesAPI.buscarFilmes())
                .thenReturn(List.of(
                        filmePior,
                        filmeMaisRelevante,
                        filmeMedio
                ));

        when(geradorAleatorio.sortear(anyInt())).thenReturn(1);

        recomendadorService = new RecomendadorService(
                notificadorPush,
                geradorAleatorio,
                catalogoFilmesAPI,
                historicoUsuarioRepository,
                perfilMaria,
                new CalculadoraScore()
        );

        List<Recomendacao> resultado =
                recomendadorService.recomendar(maria, 3);

        assertAll(
                () -> assertEquals(3, resultado.size()),

                () -> assertEquals(
                        "Mad Max",
                        resultado.get(0).getFilme().getTitulo()
                ),

                () -> assertEquals(
                        "Interestelar 2",
                        resultado.get(1).getFilme().getTitulo()
                ),

                () -> assertEquals(
                        "Drama Triste",
                        resultado.get(2).getFilme().getTitulo()
                ),

                () -> assertTrue(
                        resultado.get(0).getScoreCalculado()
                                >= resultado.get(1).getScoreCalculado()
                ),

                () -> assertTrue(
                        resultado.get(1).getScoreCalculado()
                                >= resultado.get(2).getScoreCalculado()
                )
        );
    }
}