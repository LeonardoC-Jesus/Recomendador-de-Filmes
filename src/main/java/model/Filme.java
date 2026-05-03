package model;

import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String titulo;
    private int ano;
    private int duracao;
    private List<Genero> generos;
    private List<Idioma> idiomas;
    private ClassificacaoEtaria classificacaoEtaria;
    private int popularidade;

    public Filme(String titulo, int ano, int duracao, ClassificacaoEtaria classificacaoEtaria, int popularidade) {
        this.titulo = titulo;
        this.ano = ano;
        this.duracao = duracao;
        this.generos = new ArrayList<>();
        this.idiomas = new ArrayList<>();
        this.classificacaoEtaria = classificacaoEtaria;
        this.popularidade = popularidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public ClassificacaoEtaria getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public void setClassificacaoEtaria(ClassificacaoEtaria classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public int getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(int popularidade) {
        this.popularidade = popularidade;
    }
}
