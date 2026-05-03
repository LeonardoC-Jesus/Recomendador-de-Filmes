package model;

import java.util.ArrayList;
import java.util.List;

public class PerfilCinefilo {
    private double peso;
    private int duracaoMininma;
    private int duracaoMaxima;
    private List<String> idiomas;
    private List<String> historicoDeFilmes;
    private List<String> mapaDeNotas;

    public PerfilCinefilo(double peso, int duracaoMininma, int duracaoMaxima, List<String> idiomas, List<String> historicoDeFilmes, List<String> mapaDeNotas) {
        this.peso = peso;
        this.duracaoMininma = duracaoMininma;
        this.duracaoMaxima = duracaoMaxima;
        this.idiomas = new ArrayList<>();
        this.historicoDeFilmes = new ArrayList<>();
        this.mapaDeNotas = new ArrayList<>();
    }

    public void cadastrarPesoDeGenero() {

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

    public List<String> getIdiomas() {
        return idiomas;
    }

    public List<String> getHistoricoDeFilmes() {
        return historicoDeFilmes;
    }

    public List<String> getMapaDeNotas() {
        return mapaDeNotas;
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

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setHistoricoDeFilmes(List<String> historicoDeFilmes) {
        this.historicoDeFilmes = historicoDeFilmes;
    }

    public void setMapaDeNotas(List<String> mapaDeNotas) {
        this.mapaDeNotas = mapaDeNotas;
    }
}
