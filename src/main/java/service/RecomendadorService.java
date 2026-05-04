package service;

import model.*;
import util.GeradorAleatorio;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Filme> filmes = filtrarFilmes();
        if (filmes == null) return Collections.emptyList();

        List<Recomendacao> recomendacoes = calcularScore(filmes);
        List<Recomendacao> recomendacoesOrdenadas = ordenarPorScore(recomendacoes);
        List<Recomendacao> recomendacoesTopN = new ArrayList<>();

        for (int i = 0; i < topN; i++) {
            recomendacoesTopN.add(recomendacoesOrdenadas.get(i));
        }
        return recomendacoesTopN;
    }

    private List<Filme> filtrarFilmes() {
        return filtroFilmes.filtrarFilmes();
    }

    private List<Recomendacao> calcularScore(List<Filme> filmesFiltrados) {
        List<Filme> filmes = filmesFiltrados;
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
                }
            }
        }
        recomendacoes = desempatarPorPopularidade(recomendacoes);
        recomendacoes = desempatarPorAleatoriedade(recomendacoes);
        return recomendacoes;
    }


    private List<Recomendacao> desempatarPorPopularidade(List<Recomendacao> recomendacoes) {
        Recomendacao recomendacaoDeApoio;

        for (Recomendacao recomendacao: recomendacoes) {
            for (Recomendacao recomendacaoDeComparacao: recomendacoes) {
                if (recomendacao.getScoreCalculado() == recomendacaoDeComparacao.getScoreCalculado()
                        && recomendacao.getFilme().getPopularidade() > recomendacaoDeComparacao.getFilme().getPopularidade()) {
                    recomendacaoDeApoio = recomendacao;
                    recomendacao = recomendacaoDeComparacao;
                    recomendacaoDeComparacao = recomendacaoDeApoio;
                }
            }
        }
        return recomendacoes;
    }

    private List<Recomendacao> desempatarPorAleatoriedade(List<Recomendacao> recomendacoes) {
        Recomendacao recomendacaoDeApoio;

        GeradorAleatorio gerador = new GeradorAleatorio() {
            @Override
            public int sortear(int limite) {
                Random random = new Random();
                return random.nextInt(limite);
            }
        };

        int numeroSorteado1 = gerador.sortear(100);
        int numeroSorteado2 = gerador.sortear(100);

        for (Recomendacao recomendacao: recomendacoes) {
            for (Recomendacao recomendacaoDeComparacao: recomendacoes) {
                if (recomendacao.getFilme().getPopularidade() == recomendacaoDeComparacao.getFilme().getPopularidade()
                        && numeroSorteado1 > numeroSorteado2) {
                    recomendacaoDeApoio = recomendacao;
                    recomendacao = recomendacaoDeComparacao;
                    recomendacaoDeComparacao = recomendacaoDeApoio;
                }
            }
        }
        return recomendacoes;
    }
}
