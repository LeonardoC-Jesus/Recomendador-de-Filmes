package service;

import model.*;
import util.GeradorAleatorio;

import java.util.*;

public class RecomendadorService {
    private CatalogoFilmesAPI catalogoFilmesAPI;
    private HistoricoUsuarioRepository historicoUsuarioRepository;
    private PerfilCinefilo perfilCinefilo;
    private FiltroFilmes filtroFilmes;
    private CalculadoraScore calculadoraScore;

    public RecomendadorService(CatalogoFilmesAPI catalogoFilmesAPI, HistoricoUsuarioRepository historicoUsuarioRepository, PerfilCinefilo perfilCinefilo, CalculadoraScore calculadoraScore) {
        this.catalogoFilmesAPI = catalogoFilmesAPI;
        this.historicoUsuarioRepository = historicoUsuarioRepository;
        this.perfilCinefilo = perfilCinefilo;
        this.calculadoraScore = calculadoraScore;
        this.filtroFilmes = new FiltroFilmes(catalogoFilmesAPI, perfilCinefilo);
    }

    private  GeradorAleatorio gerador = new GeradorAleatorio() {
        @Override
        public int sortear(int limite) {
            Random random = new Random();
            return random.nextInt(limite);
        }
    };

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

    public Optional<Filme> recomendarAleatorio() {
        List<Filme> filmes = filtrarFilmes();

        int numeroAleatorio = gerador.sortear(filmes.size());
        return Optional.ofNullable(filmes.get(numeroAleatorio));
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

        for (int i = 0; i < recomendacoes.size(); i++) {
            for (int j = 0; j < recomendacoes.size(); j++) {
                if (recomendacoes.get(i).getScoreCalculado() > recomendacoes.get(j).getScoreCalculado()) {
                    Collections.swap(recomendacoes, i, j);
                }
            }
        }

        recomendacoes = desempatarPorPopularidade(recomendacoes);
        recomendacoes = desempatarPorAleatoriedade(recomendacoes);
        return recomendacoes;
    }


    private List<Recomendacao> desempatarPorPopularidade(List<Recomendacao> recomendacoes) {
        Recomendacao recomendacaoDeApoio;

        for (int i = 0; i < recomendacoes.size(); i++) {
            for (int j = 0; j < recomendacoes.size(); j++) {
                if (recomendacoes.get(i).getScoreCalculado() == recomendacoes.get(j).getScoreCalculado()
                        && recomendacoes.get(i).getFilme().getPopularidade() > recomendacoes.get(j).getFilme().getPopularidade()) {
                    Collections.swap(recomendacoes, i, j);
                }
            }
        }
        return recomendacoes;
    }

    private List<Recomendacao> desempatarPorAleatoriedade(List<Recomendacao> recomendacoes) {
        Recomendacao recomendacaoDeApoio;

        int numeroSorteado1 = gerador.sortear(100);
        int numeroSorteado2 = gerador.sortear(100);

        for (int i = 0; i < recomendacoes.size(); i++) {
            for (int j = 0; j < recomendacoes.size(); j++) {
                if (recomendacoes.get(i).getFilme().getPopularidade() == recomendacoes.get(j).getFilme().getPopularidade()
                        && numeroSorteado1 > numeroSorteado2) {
                    Collections.swap(recomendacoes, i, j);
                }
            }
        }

        return recomendacoes;
    }
}
