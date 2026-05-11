import model.PerfilCinefilo;
import model.Usuario;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class App {

    private static Usuario usuario;

    public static void main(String[] args) {
       iniciar();
    }

    public static void iniciar() {
        Scanner input = new Scanner(System.in);
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
                case 2:

            }
        } while (opcao != 0);
    }

    public static void cadastrarUsuario(Scanner input) {
        System.out.println("Digite seu Nome:");
        String nome = input.nextLine();
        input.nextLine();
        System.out.println("Digite sua idade:");
        int idade = input.nextInt();

        PerfilCinefilo perfilCinefilo = casdastrarPerfilCinefilo(input);

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
}
