package model;

import model.enums.Genero;
import model.enums.Idioma;
import service.CatalogoFilmesAPI;

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

    public void filtrarFilmesPorClassificacao() {
        for (Filme filme : filmesFiltrados) {
            if (filme.getClassificacaoEtaria().getCLASSIFICACAO() > perfilCinefilo.getClassificacaoMaxima().getCLASSIFICACAO()) {
                filmesFiltrados.remove(filme);
            }
        }
    }

    public void filtrarFilmesPorIdioma(){
        for (Filme filme: filmesFiltrados) {
            for (Idioma idioma: perfilCinefilo.getIdiomas()) {
                if (filme.getIdioma() != idioma) {
                    filmesFiltrados.remove(filme);
                }
            }
        }
    }

    public void filtrarFilmesJaAssistidos() {
        List<Filme> filmesAssistidos = perfilCinefilo.getHistoricoDeFilmes();

        for (Filme filme: filmesFiltrados) {
            if (filmesAssistidos.contains(filme)) {
                filmesFiltrados.remove(filme);
            }
        }
    }

    public void filtrarFilmesPorGenero(){
        for (Map.Entry<Genero, Double> genero: perfilCinefilo.getPesoPorGenero().entrySet()) {
            if (genero.getValue() == 0.0) {
                for (Filme filme: filmesFiltrados) {
                    for (Genero generoFilme: filme.getGeneros()) {
                        if (generoFilme == genero.getKey()) {
                            filmesFiltrados.remove(filme);
                        }
                    }
                }
            }
        }
    }

    public List<Filme> getFilmesFiltrados() {
        return filmesFiltrados;
    }
}
