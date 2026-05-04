package service;

import model.*;
import util.GeradorAleatorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecomendadorService {
    private CatalogoFilmesAPI catalogoFilmesAPI;
    private HistoricoUsuarioRepository historicoUsuarioRepository;
    private PerfilCinefilo perfilCinefilo;
    private FiltroFilmes filtroFilmes;
    private CalculadoraScore calculadoraScore;

    public RecomendadorService(CatalogoFilmesAPI catalogoFilmesAPI, HistoricoUsuarioRepository historicoUsuarioRepository, PerfilCinefilo perfilCinefilo, FiltroFilmes filtroFilmes, CalculadoraScore calculadoraScore) {
        this.catalogoFilmesAPI = catalogoFilmesAPI;
        this.historicoUsuarioRepository = historicoUsuarioRepository;
        this.perfilCinefilo = perfilCinefilo;
        this.calculadoraScore = calculadoraScore;
        this.filtroFilmes = new FiltroFilmes(catalogoFilmesAPI, perfilCinefilo);
    }

    public List<Recomendacao> recomendar(Usuario usuario, int topN) {

        return new ArrayList<>();
    }

    private List<Filme> filtrarFilmes() {
        return filtroFilmes.filtrarFilmes();
    }

    private List<Recomendacao> calcularScore() {
        List<Filme> filmes = filtrarFilmes();
        List<Recomendacao> recomendacoes = new ArrayList<>();

        for (Filme filme: filmes) {
            recomendacoes.add(
                    new Recomendacao(
                            filme,
                            calculadoraScore.calcularScore(filme, perfilCinefilo),
                            ""));
        }
        return recomendacoes;
    }

    private List<Recomendacao> ordenarPorScore(List<Recomendacao> recomendacoes) {
        Recomendacao recomendacaoDeApoio;

        for (Recomendacao recomendacao: recomendacoes) {
            for (Recomendacao recomendacaoDeComparacao: recomendacoes) {
                if (recomendacao.getScoreCalculado() > recomendacaoDeComparacao.getScoreCalculado()) {
                    recomendacaoDeApoio = recomendacao;
                    recomendacao = recomendacaoDeComparacao;
                    recomendacaoDeComparacao = recomendacaoDeApoio;
                } else if (recomendacao.getScoreCalculado() == recomendacaoDeComparacao.getScoreCalculado()) {
                    if (recomendacao.getFilme().getPopularidade() > recomendacaoDeComparacao.getFilme().getPopularidade()) {
                        recomendacaoDeApoio = recomendacao;
                        recomendacao = recomendacaoDeComparacao;
                        recomendacaoDeComparacao = recomendacaoDeApoio;
                    } else if (recomendacao.getFilme().getPopularidade() == recomendacaoDeComparacao.getFilme().getPopularidade()) {
                        GeradorAleatorio gerador = new GeradorAleatorio() {
                            @Override
                            public int sortear(int limite) {
                                Random random = new Random();
                                return random.nextInt(limite);
                            }
                        };

                        int numeroSorteado1 = gerador.sortear(10);
                        int numeroSorteado2 = gerador.sortear(10);

                        if (numeroSorteado1 > numeroSorteado2) {
                            recomendacaoDeApoio = recomendacao;
                            recomendacao = recomendacaoDeComparacao;
                            recomendacaoDeComparacao = recomendacaoDeApoio;
                        }
                    }
                }
            }
        }
        return recomendacoes;
    }
}
