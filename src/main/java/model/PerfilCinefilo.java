package model;

import exception.*;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilCinefilo {
    private Map<Genero, Double> pesoPorGenero;
    private ClassificacaoEtaria classificacaoMaxima;
    private int duracaoMinima;
    private int duracaoMaxima;
    private List<Idioma> idiomas;
    private List<Filme> historicoDeFilmes;
    private Map<Filme, Double> mapaDeNotas;

    public PerfilCinefilo(ClassificacaoEtaria classificacaoMaxima, int duracaoMinima, int duracaoMaxima, List<Idioma> idiomas, List<Filme> historicoDeFilmes) {
        this.classificacaoMaxima = classificacaoMaxima;
        this.pesoPorGenero = new HashMap<>();
        setDuracao(duracaoMinima, duracaoMaxima);
        this.idiomas = idiomas;
        this.historicoDeFilmes = historicoDeFilmes;
        this.mapaDeNotas = new HashMap<>();
    }

    public void cadastrarPesoDeGenero(Genero genero, double peso) {
        double pesoMinimo = 0.0;
        double pesoMaximo = 1.0;

        if (peso < pesoMinimo || peso > pesoMaximo) {
            throw new PesoInvalidoException();
        }

        this.pesoPorGenero.put(genero, peso);
    }

    public void registrarNota(Filme filme, double nota) {
        double notaMinima = 1;
        double notaMaxima = 5;

        if (nota < notaMinima || nota > notaMaxima) {
            throw new NotaInvalidaException();
        }

        this.mapaDeNotas.put(filme, nota);
    }

    public void setDuracao(int duracaoMinima, int duracaoMaxima) {
        if (duracaoMinima > duracaoMaxima || duracaoMinima <= 0) {
            throw new DuracaoInvalidaException();
        }

        this.duracaoMinima = duracaoMinima;
        this.duracaoMaxima = duracaoMaxima;
    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public void setClassificacaoMaxima(ClassificacaoEtaria classificacaoMaxima) {
        this.classificacaoMaxima = classificacaoMaxima;
    }

    public int getDuracaoMinima() {
        return duracaoMinima;
    }

    public int getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public Map<Genero, Double> getPesoPorGenero() {
        return pesoPorGenero;
    }

    public void setPesoPorGenero(Map<Genero, Double> pesoPorGenero) {
        this.pesoPorGenero = pesoPorGenero;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Filme> getHistoricoDeFilmes() {
        return historicoDeFilmes;
    }

    public void setHistoricoDeFilmes(List<Filme> historicoDeFilmes) {
        this.historicoDeFilmes = historicoDeFilmes;
    }

    public Map<Filme, Double> getMapaDeNotas() {
        return mapaDeNotas;
    }

    public void setMapaDeNotas(Map<Filme, Double> mapaDeNotas) {
        this.mapaDeNotas = mapaDeNotas;
    }
}