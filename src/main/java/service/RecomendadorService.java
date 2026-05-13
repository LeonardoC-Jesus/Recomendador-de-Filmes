package service;

import model.*;
import model.enums.Genero;
import util.GeradorAleatorio;

import java.util.*;
import java.util.stream.Collectors;

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

        List<Recomendacao> recomendacoes = definirJustificativa(calcularScore(filmes), usuario);
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
            notificador.enviarAviso("NOVA LISTA DE RECOMENDAÇÃO DISPONÍVEL!!", usuario);
        }
        return recomendacoesTopN;
    }

    public Filme recomendarAleatorio() {
        List<Filme> filmes = filtrarFilmes();

        int numeroAleatorio = gerador.sortear(filmes.size());
        Filme filmeSorteado = filmes.get(numeroAleatorio);

        return filmeSorteado;
    }

    private List<Filme> filtrarFilmes() {
        return filtroFilmes.filtrarFilmes();
    }

    private List<Recomendacao> definirJustificativa(List<Recomendacao> recomendacoes, Usuario usuario) {
        List<Recomendacao> recomendacoesComJustificativas = new ArrayList<>();
        Map<Genero, Double> generosFiltrados = new HashMap<>();

        for (Map.Entry<Genero, Double> genero: usuario.getPerfilCinefilo().getPesoPorGenero().entrySet()) {
            if (genero.getValue() > 0.5) {
                generosFiltrados.put(genero.getKey(), genero.getValue());
            }
        }

        for (Recomendacao recomendacao : recomendacoes) {
            for (Map.Entry<Genero, Double> genero : generosFiltrados.entrySet()) {
                if (recomendacao.getFilme().getGeneros().contains(genero.getKey())) {
                    recomendacao.setJustificativa("Recomendamos " + recomendacao.getFilme().getTitulo() + " porque você gosta filmes de " + genero.getKey());
                    break;
                } else if (recomendacao.getJustificativa().isEmpty()){
                    recomendacao.setJustificativa("Recomendamos " + recomendacao.getFilme().getTitulo() + " porque sua classificação etária é " + recomendacao.getFilme().getClassificacaoEtaria());
                }
            }
            recomendacoesComJustificativas.add(recomendacao);
        }

        return recomendacoesComJustificativas;
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
