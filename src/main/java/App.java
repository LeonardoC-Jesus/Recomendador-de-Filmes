import data.CatalogoFilmesMock;
import data.HistoricoFilmesMock;
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
import java.util.*;

public class App {

    private static Usuario usuario;
    private static RecomendadorService recomendadorService;
    private static PerfilCinefilo perfilCinefilo;
    private static HistoricoFilmesMock historicoFilmesMock= new HistoricoFilmesMock();
    private static CatalogoFilmesMock catalogoFilmesMock = new CatalogoFilmesMock();
    private static List<Filme> filmesDoHistoricoMock = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        iniciar();
        Scanner input = new Scanner(System.in);
        //cadastrarUsuario(input);
    }

    public static void iniciar() throws IOException {
        Scanner input = new Scanner(System.in);

        filmesDoHistoricoMock =  historicoFilmesMock.consultarHistorico();
        //perfilCinefilo = new PerfilCinefilo(ClassificacaoEtaria.DEZESSEIS, 90, 160, List.of(Idioma.PORTUGUES, Idioma.INGLES), filmesDoHistoricoMock);
        //usuario = new Usuario("Leonardo", 20, perfilCinefilo, true);

        int opcao;

        try {
            do {
                System.out.println("Escolha uma opção:");
                System.out.println("[1] Cadastra-se");
                System.out.println("[2] Ver perfil");
                System.out.println("[3] Ativar notificações");
                System.out.println("[4] Desativar notificações");
                System.out.println("[5] Recomendar filmes");
                System.out.println("[6] Ver histórico de filmes");
                System.out.println("[0] Sair do sistema");
                opcao = input.nextInt();

                switch (opcao) {
                    case 1:
                        cadastrarUsuario(input);
                        break;
                    case 2:
                        mostrarUsuario();
                        break;
                    case 3:
                        ativarNotificacoes();
                        break;
                    case 4:
                        desativarNotificacoes();
                        break;
                    case 5:
                        recomendarFilmes();
                        break;
                    case 6:
                        mostrarHistoricoDeFilmes();
                        break;
                }
            } while (opcao != 0);
        } catch (InputMismatchException e) {
          //  System.out.println("Tipo de entrada inválida\nColoque apenas números");
        }
    }

    public static void cadastrarUsuario(Scanner input) {
        try {
            System.out.println("Digite seu Nome:");
            String nome = input.nextLine();
            input.nextLine();
            System.out.println("Digite sua idade:");
            int idade = input.nextInt();

            perfilCinefilo = casdastrarPerfilCinefilo(input);

            cadastrarPesoPorGenero(perfilCinefilo, input);
            usuario = new Usuario(nome, idade, perfilCinefilo, true);
        } catch (InputMismatchException e) {
            System.out.println("Tipo de entrada inválida\n" +
                    "Coloque apenas números");
        }
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
        return new PerfilCinefilo(classificacaoEtaria,duracaoMinima,duracaoMaxima,idiomas,filmesDoHistoricoMock);
    }

    public static void cadastrarPesoPorGenero(PerfilCinefilo perfilCinefilo, Scanner input) {
        System.out.println("Cadastre o peso de cada gênero:");
        System.out.println("Valores aceitos entre 0 e 1");

        for (Genero genero: Genero.values()) {
            System.out.println(genero.getValor() + ":");
            perfilCinefilo.cadastrarPesoDeGenero(genero, input.nextDouble());
        }
    }

    public static void mostrarHistoricoDeFilmes() {
        try {
            for (Filme filme : perfilCinefilo.getHistoricoDeFilmes()) {
                System.out.println(filme);
            }
        } catch (NullPointerException e) {
            System.out.println("Usuário ainda não foi cadastrado");
        }
    }

    public static void ativarNotificacoes() {
        if (usuario != null) usuario.setNotificacoesHabilitadas(true);
    }

    public static void desativarNotificacoes() {
        if (usuario != null) usuario.setNotificacoesHabilitadas(false);
    }

    public static void mostrarUsuario() {
        if (usuario != null) {
            System.out.println(usuario);
        } else  {
            System.out.println("Usuário ainda não foi cadastrado");
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

    public static void recomendarFilmes() {

        try {
            CalculadoraScore calculadoraScore = new CalculadoraScore();

            recomendadorService = new RecomendadorService(
                    notificadorPush,
                    geradorAleatorio,
                    catalogoFilmesMock,
                    historicoUsuarioRepository,
                    perfilCinefilo,
                    calculadoraScore);

            List<Recomendacao> recomendacoes = recomendadorService.recomendar(usuario, 3);

            for (Recomendacao recomendacao : recomendacoes) {
                System.out.println(recomendacao);
            }

            if (recomendacoes.isEmpty()) {
                System.out.println("Sem mais filmes no catálogo!");
            }
        } catch (NullPointerException e) {
            System.out.println("Usuário ainda não foi cadastrado");
        }
    }
}
