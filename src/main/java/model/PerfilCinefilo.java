package model;

import model.enums.ClassificacaoEtaria;
import model.enums.Idioma;

import java.util.ArrayList;
import java.util.List;

public class PerfilCinefilo {
    private ClassificacaoEtaria classificacaoMaxima;
    private double peso;
    private int duracaoMininma;
    private int duracaoMaxima;
    private List<Idioma> idiomas;
    private List<Filme> historicoDeFilmes;
    private List<Double> mapaDeNotas;

    public PerfilCinefilo(ClassificacaoEtaria classificacaoMaxima, double peso, int duracaoMininma, int duracaoMaxima, List<String> idiomas, List<String> historicoDeFilmes, List<String> mapaDeNotas) {
        this.classificacaoMaxima = classificacaoMaxima;
        this.peso = peso;
        this.duracaoMininma = duracaoMininma;
        this.duracaoMaxima = duracaoMaxima;
        this.idiomas = new ArrayList<>();
        this.historicoDeFilmes = new ArrayList<>();
        this.mapaDeNotas = new ArrayList<>();
    }

    public void cadastrarPesoDeGenero() {

    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public double getPeso() {
        return peso;
    }

    public int getDuracaoMininma() {
        return duracaoMininma;
    }

    public int getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public List<Filme> getHistoricoDeFilmes() {
        return historicoDeFilmes;
    }

    public List<Double> getMapaDeNotas() {
        return mapaDeNotas;
    }

    public void setClassificacaoMaxima(ClassificacaoEtaria classificacaoMaxima) {
        this.classificacaoMaxima = classificacaoMaxima;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setDuracaoMininma(int duracaoMininma) {
        this.duracaoMininma = duracaoMininma;
    }

    public void setDuracaoMaxima(int duracaoMaxima) {
        this.duracaoMaxima = duracaoMaxima;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public void setHistoricoDeFilmes(List<Filme> historicoDeFilmes) {
        this.historicoDeFilmes = historicoDeFilmes;
    }

    public void setMapaDeNotas(List<Double> mapaDeNotas) {
        this.mapaDeNotas = mapaDeNotas;
    }
}
