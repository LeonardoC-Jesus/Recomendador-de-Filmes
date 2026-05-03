package model;

import model.enums.Idioma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiltroFilmes {
    private List<Filme> filmesCatalogo;
    private List<Filme> filmesFiltrados;
    private PerfilCinefilo perfilCinefilo;
    private CatalogoFilmesAPI catalogoFilmesAPI;

    public FiltroFilmes(CatalogoFilmesAPI catalogoFilmesAPI, PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
        this.filmesCatalogo = catalogoFilmesAPI.buscarFilmes();
    }

    public List<Filme> filtrarFilmes() {
        if (filmesCatalogo.isEmpty()) {
            return Collections.emptyList();
        }
        filtrarFilmesPorClassificacao();
        filtrarFilmesPorIdioma();
        filtrarFilmesJaAssistidos();
        return filmesFiltrados;
    }

    private void filtrarFilmesPorClassificacao() {
        for (Filme filme : filmesCatalogo) {
            if (filme.getClassificacaoEtaria().getCLASSIFICACAO() > perfilCinefilo.getClassificacaoMaxima().getCLASSIFICACAO()) {
                filmesCatalogo.remove(filme);
            }
        }
        filmesFiltrados = filmesCatalogo;
    }

    private void filtrarFilmesPorIdioma(){
        for (Filme filme: filmesFiltrados) {
            for (Idioma idioma: perfilCinefilo.getIdiomas()) {
                if (filme.getIdioma() != idioma) {
                    filmesFiltrados.remove(filme);
                }
            }
        }
    }

    private void filtrarFilmesJaAssistidos() {
        List<Filme> filmesAssistidos = perfilCinefilo.getHistoricoDeFilmes();

        for (Filme filme: filmesFiltrados) {
            if (filmesAssistidos.contains(filme)) {
                filmesFiltrados.remove(filme);
            }
        }
    }

    private void filtrarFilmesPorGenero(){

    }

}
