package model;

import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

import java.util.List;

public class Filme {
    private Long id;
    private String titulo;
    private int ano;
    private int duracao;
    private List<Genero> generos;
    private Idioma idioma;
    private ClassificacaoEtaria classificacaoEtaria;
    private int popularidade;
    
    public Filme(String titulo, int ano, int duracao,List<Genero> generos, Idioma idioma, ClassificacaoEtaria classificacaoEtaria, int popularidade) {
        this.titulo = titulo;
        this.ano = ano;
        this.duracao = duracao;
        this.generos = generos;
        this.idioma = idioma;
        this.classificacaoEtaria = classificacaoEtaria;
        this.popularidade = popularidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return id != null && id.equals(filme.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
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
    
    @Override
    public String toString() {
        return "Filme{" +
                 titulo + '\'' +
                '}';
    }
}
