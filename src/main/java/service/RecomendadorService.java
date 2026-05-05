package service;

import model.*;
import util.GeradorAleatorio;

import java.util.*;

public class RecomendadorService {
    private NotificadorPush notificador;
    private GeradorAleatorio gerador;
    private HistoricoUsuarioRepository historicoUsuarioRepository;
    private PerfilCinefilo perfilCinefilo;
    private FiltroFilmes filtroFilmes;
    private CalculadoraScore calculadoraScore;

    public RecomendadorService(NotificadorPush notificador, GeradorAleatorio gerador, CatalogoFilmesAPI catalogoFilmesAPI, HistoricoUsuarioRepository historicoUsuarioRepository, PerfilCinefilo perfilCinefilo, CalculadoraScore calculadoraScore) {
        this.notificador = notificador;
        this.gerador = gerador;
        this.historicoUsuarioRepository = historicoUsuarioRepository;
        this.perfilCinefilo = perfilCinefilo;
        this.calculadoraScore = calculadoraScore;
        this.filtroFilmes = new FiltroFilmes(catalogoFilmesAPI, perfilCinefilo);
    }

    public List<Recomendacao> recomendar(Usuario usuario, int topN) {
        List<Filme> filmes = filtrarFilmes();
        if (filmes == null || filmes.isEmpty()) return Collections.emptyList();

        List<Recomendacao> recomendacoes = calcularScore(filmes);
        List<Recomendacao> recomendacoesOrdenadas = ordenarPorScore(recomendacoes);
        List<Recomendacao> recomendacoesTopN = new ArrayList<>();

        for (int i = 0; i < topN; i++) {
            if (i < filmes.size()) {
                recomendacoesTopN.add(recomendacoesOrdenadas.get(i));
            } else {
                break;
            }
        }

        historicoUsuarioRepository.registrarRecomendacao(recomendacoesTopN);

        if (usuario.isNotificacoesHabilitadas()) {
            notificador.enviarAviso("Nova lista de Recomendação disponivel", usuario);
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

        List<Recomendacao> recomendacaosDesempatasPorPopularidade;
        List<Recomendacao> recomendacaosOrdenadasAleatorio;

        for (int i = 0; i < recomendacoes.size(); i++) {
            for (int j = 0; j < recomendacoes.size(); j++) {
                if (recomendacoes.get(i).getScoreCalculado() > recomendacoes.get(j).getScoreCalculado()) {
                    Collections.swap(recomendacoes, i, j);
                }
            }
        }

        recomendacaosDesempatasPorPopularidade = desempatarPorPopularidade(recomendacoes);
        recomendacaosOrdenadasAleatorio = desempatarPorAleatoriedade(recomendacaosDesempatasPorPopularidade);
        return recomendacaosOrdenadasAleatorio;
    }


    private List<Recomendacao> desempatarPorPopularidade(List<Recomendacao> recomendacoes) {

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
