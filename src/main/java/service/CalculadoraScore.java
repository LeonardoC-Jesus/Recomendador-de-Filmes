package service;

import model.Filme;
import model.PerfilCinefilo;
import model.enums.Genero;

import java.util.Map;

public class CalculadoraScore {
    static final double PESO_GENERO       = 0.50;
    static final double PESO_DURACAO      = 0.20;
    static final double PESO_POPULARIDADE = 0.15;
    static final double PESO_AFINIDADE    = 0.15;

    public CalculadoraScore() {
    }

    public double calcularScore(Filme filme, PerfilCinefilo perfilCinefilo) {
        double scoreFinal = 0.0;

        scoreFinal += calcularComponenteGenero(filme, perfilCinefilo) * PESO_GENERO;

        scoreFinal += calcularComponenteDuracao(filme, perfilCinefilo) * PESO_DURACAO;

        scoreFinal += filme.getPopularidade() * PESO_POPULARIDADE;

        if (temAfinidadeHistorica(filme, perfilCinefilo)) {
            scoreFinal += 100 * PESO_AFINIDADE;
        }

        return aplicarTrava(scoreFinal);
    }

    private boolean temAfinidadeHistorica(Filme filme, PerfilCinefilo perfil) {
        for (Map.Entry<Filme, Double> entrada : perfil.getMapaDeNotas().entrySet()) {
            Filme assistido = entrada.getKey();
            Double nota = entrada.getValue();

            if (nota >= 4.0 && possuiGeneroEmComum(filme, assistido)) {
                return true;
            }
        }
        return false;
    }

    private boolean possuiGeneroEmComum(Filme atual, Filme assistido) {
        for (Genero genero : atual.getGeneros()) {
            if (assistido.getGeneros().contains(genero)) {
                return true;
            }
        }
        return false;
    }

    private double calcularComponenteGenero(Filme filme, PerfilCinefilo perfilCinefilo) {
        double valorVazio = 0.0;
        if (filme.getGeneros().isEmpty()) {
            return valorVazio;
        }

        double soma = 0.0;
        for (Genero genero : filme.getGeneros()) {
            Double pesoCadastrado = perfilCinefilo.getPesoPorGenero().get(genero);
            if (pesoCadastrado != null) {
                soma += pesoCadastrado;
            }
        }
        return (soma / filme.getGeneros().size()) * 100;
    }

    private double calcularComponenteDuracao(Filme filme, PerfilCinefilo perfilCinefilo) {
        int duracao = filme.getDuracao();
        if (duracao >= perfilCinefilo.getDuracaoMinima() && duracao <= perfilCinefilo.getDuracaoMaxima()) {
            return 100.0;
        }

        int diferenca;
        if (duracao < perfilCinefilo.getDuracaoMinima()) {
            diferenca = perfilCinefilo.getDuracaoMinima() - duracao;
        } else {
            diferenca = duracao - perfilCinefilo.getDuracaoMaxima();
        }

        double resultadoMinimo = 0.0;
        double resultado = 100 - (diferenca * 2);
        if (resultado < 0) {
            return resultadoMinimo;
        }

        return resultado;
    }

    private double aplicarTrava(double valor) {
        double travaMaxima = 100.0;
        if (valor > travaMaxima) {
            return travaMaxima;
        }

        double travaMinima = 0.0;
        if (valor < travaMinima) {
            return travaMinima;
        }

        return valor;
    }
}