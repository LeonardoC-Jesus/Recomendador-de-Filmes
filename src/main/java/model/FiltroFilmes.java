package model;

import model.enums.Genero;
import model.enums.Idioma;
import service.CatalogoFilmesAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FiltroFilmes {
    private List<Filme> filmesFiltrados;
    private PerfilCinefilo perfilCinefilo;

    public FiltroFilmes(CatalogoFilmesAPI catalogoFilmesAPI, PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
        this.filmesFiltrados = catalogoFilmesAPI.buscarFilmes();
    }

    public List<Filme> filtrarFilmes() {
        if (filmesFiltrados.isEmpty()) {
            return Collections.emptyList();
        }
        filtrarFilmesPorClassificacao();
        filtrarFilmesPorIdioma();
        filtrarFilmesJaAssistidos();
        filtrarFilmesPorGenero();
        return filmesFiltrados;
    }


    private void filtrarFilmesPorClassificacao() {
        List<Filme> filmesPorClassificacao = new ArrayList<>();

        for (Filme filme : filmesFiltrados) {
            if (filme.getClassificacaoEtaria().getCLASSIFICACAO() <= perfilCinefilo.getClassificacaoMaxima().getCLASSIFICACAO()) {
                filmesPorClassificacao.add(filme);
            }
        }
        filmesFiltrados = filmesPorClassificacao;
    }

    private void filtrarFilmesPorIdioma(){
        List<Filme> filmesPorIdioma = new ArrayList<>();

        for (Filme filme: filmesFiltrados) {
            for (Idioma idioma: perfilCinefilo.getIdiomas()) {
                if (filme.getIdioma() == idioma) {
                    filmesPorIdioma.add(filme);
                }
            }
        }

        filmesFiltrados = filmesPorIdioma;
    }

    private void filtrarFilmesJaAssistidos() {
        List<Filme> filmesDoHistorico = perfilCinefilo.getHistoricoDeFilmes();
        List<Filme> filmesNaoAssistidos = new ArrayList<>();

        for (Filme filmedoCatalogo : filmesDoHistorico) {
            if (!filmesFiltrados.contains(filmedoCatalogo)) {
                filmesNaoAssistidos.add(filmedoCatalogo);
            }
        }

        filmesFiltrados = filmesNaoAssistidos;
    }

    private void filtrarFilmesPorGenero(){
        List<Filme> filmesPorGenero = new ArrayList<>();

        for (Map.Entry<Genero, Double> genero: perfilCinefilo.getPesoPorGenero().entrySet()) {
            filmesFiltrados.removeIf(filme -> filme.getGeneros().contains(genero.getKey()) && genero.getValue() == 0.0);
        }
    }

    public List<Filme> getFilmesFiltrados() {
        return filmesFiltrados;
    }


}
