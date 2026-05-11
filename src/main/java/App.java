import model.Filme;
import model.PerfilCinefilo;
import model.Recomendacao;
import model.Usuario;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import service.*;
import util.GeradorAleatorio;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class App {

    private static Usuario usuario;
    private static RecomendadorService recomendadorService;
    private static PerfilCinefilo perfilCinefilo;
    private static List<Filme> filmesDoHistoricoMock = new ArrayList<>();

    static List<Filme> filmes = List.of(

            new Filme(
                    1L,
                    "Interestelar",
                    2014,
                    169,
                    List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                    Idioma.INGLES,
                    ClassificacaoEtaria.DOZE,
                    95
            ),

            new Filme(
                    2L,
                    "Cidade de Deus",
                    2002,
                    130,
                    List.of(Genero.DRAMA, Genero.ACAO),
                    Idioma.PORTUGUES,
                    ClassificacaoEtaria.DEZOITO,
                    98
            ),

            new Filme(
                    3L,
                    "Parasita",
                    2019,
                    132,
                    List.of(Genero.TERROR, Genero.DRAMA),
                    Idioma.JAPONES,
                    ClassificacaoEtaria.DEZESSEIS,
                    96
            ),

            new Filme(
                    4L,
                    "Vingadores Ultimato",
                    2019,
                    181,
                    List.of(Genero.ACAO, Genero.FICCAO_CIENTIFICA),
                    Idioma.INGLES,
                    ClassificacaoEtaria.DOZE,
                    94
            ),

            new Filme(
                    5L,
                    "Your Name",
                    2016,
                    106,
                    List.of(Genero.ROMANCE, Genero.DRAMA),
                    Idioma.JAPONES,
                    ClassificacaoEtaria.LIVRE,
                    91
            ),

            new Filme(
                    6L,
                    "O Poço",
                    2019,
                    94,
                    List.of(Genero.TERROR),
                    Idioma.ESPANHOL,
                    ClassificacaoEtaria.DEZOITO,
                    85
            ),

            new Filme(
                    7L,
                    "Shrek",
                    2001,
                    90,
                    List.of(Genero.COMEDIA),
                    Idioma.INGLES,
                    ClassificacaoEtaria.LIVRE,
                    93
            ),

            new Filme(
                    8L,
                    "Clube da Luta",
                    1999,
                    139,
                    List.of(Genero.DRAMA),
                    Idioma.INGLES,
                    ClassificacaoEtaria.DEZOITO,
                    97
            )
    );

    public static void main(String[] args) {
       iniciar();
    }

    public static void iniciar() {
        Scanner input = new Scanner(System.in);
        filmesDoHistoricoMock = List.of(new Filme(
                        7L,
                        "Shrek",
                        2001,
                        90,
                        List.of(Genero.COMEDIA),
                        Idioma.INGLES,
                        ClassificacaoEtaria.LIVRE,
                        93
                ),

                new Filme(
                        8L,
                        "Clube da Luta",
                        1999,
                        139,
                        List.of(Genero.DRAMA),
                        Idioma.INGLES,
                        ClassificacaoEtaria.DEZOITO,
                        97
                ));

        int opcao;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] Cadastra-se");
            System.out.println("[2] Ver perfil");
            System.out.println("[3] Ativar/Desativar notificações");
            System.out.println("[4] Recomendar filmes");
            System.out.println("[0] Sair do sistema");
            opcao = input.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarUsuario(input);
                    break;
                case 4:
                    recomendarFilmes();
            }
        } while (opcao != 0);
    }

    public static void cadastrarUsuario(Scanner input) {
        System.out.println("Digite seu Nome:");
        String nome = input.nextLine();
        input.nextLine();
        System.out.println("Digite sua idade:");
        int idade = input.nextInt();

        perfilCinefilo = casdastrarPerfilCinefilo(input);

        cadastrarPesoPorGenero(perfilCinefilo, input);
        usuario = new Usuario(nome, idade, perfilCinefilo, true);
    }

    public static PerfilCinefilo casdastrarPerfilCinefilo(Scanner input) {
        System.out.println("Digite o limite da classificação desejada:");
        System.out.println("|0(LIVRE) | 10 | 12 | 14 | 16 | 18|");
        ClassificacaoEtaria classificacaoEtaria = ClassificacaoEtaria.pegarPeloValor(input.nextInt());

        System.out.println("Digite a duração minima desejada:");
        int duracaoMinima = input.nextInt();

        System.out.println("Digite a duração máxima desejada:");
        int duracaoMaxima = input.nextInt();

        System.out.println("Digite os idiomas que você quer assistir:");
        System.out.println("Quantos são?");
        List<Idioma> idiomas = new ArrayList<>();
        int quantidadeDeIdiomas = input.nextInt();

        input.nextLine();
        for (int i = 0; i < quantidadeDeIdiomas; i++) {
            System.out.println("Digite o idioma:");
            idiomas.add(Idioma.pegarPorValor(input.nextLine()));
        }
        return new PerfilCinefilo(classificacaoEtaria,duracaoMinima,duracaoMaxima,idiomas,new ArrayList<>());
    }

    public static void cadastrarPesoPorGenero(PerfilCinefilo perfilCinefilo, Scanner input) {
        System.out.println("Cadastre o peso de cada gênero:");
        System.out.println("Valores aceitos entre 0 e 1");

        for (Genero genero: Genero.values()) {
            System.out.println(genero.getValor() + ":");
            perfilCinefilo.cadastrarPesoDeGenero(genero, input.nextDouble());
        }
    }

    static NotificadorPush notificadorPush = new NotificadorPush() {
        @Override
        public void enviarAviso(String mensagem, Usuario usuario) {
            if (usuario.isNotificacoesHabilitadas()) {
                System.out.println(mensagem);
            }
        }
    };

    static GeradorAleatorio geradorAleatorio = new GeradorAleatorio() {
        @Override
        public int sortear(int limite) {
            Random random = new Random();

            return random.nextInt(limite);
        }
    };

    static HistoricoUsuarioRepository historicoUsuarioRepository = new HistoricoUsuarioRepository() {
        @Override
        public void salvar(Filme filme) {
            filmesDoHistoricoMock.add(filme);
        }

        @Override
        public List<Filme> consultarTudo() {
            return filmesDoHistoricoMock;
        }

        @Override
        public void registrarRecomendacao(List<Recomendacao> recomendacoes) {
            for (Recomendacao recomendacao: recomendacoes) {
                filmesDoHistoricoMock.add(recomendacao.getFilme());
            }
        }
    };

    static CatalogoFilmesAPI catalogoFilmesAPI = new CatalogoFilmesAPI() {
        @Override
        public List<Filme> buscarFilmes() throws IOException {
            return filmes;
        }
    };

    public static void recomendarFilmes() {

        CalculadoraScore calculadoraScore = new CalculadoraScore();

        recomendadorService = new RecomendadorService(
                notificadorPush,
                geradorAleatorio,
                catalogoFilmesAPI,
                historicoUsuarioRepository,
                perfilCinefilo,
                calculadoraScore);

        List<Recomendacao> recomendacoes = recomendadorService.recomendar(usuario, 3);

        for (Recomendacao recomendacao: recomendacoes) {
            System.out.println(recomendacao);
        }
    }
}
