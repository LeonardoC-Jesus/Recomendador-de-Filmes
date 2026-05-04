package model;

public class Recomendacao {
    private Filme filme;
    private double scoreCalculado;
    private String justificativa;

    public Recomendacao(Filme filme, double scoreCalculado, String justificativa) {
        this.filme = filme;
        this.scoreCalculado = scoreCalculado;
        this.justificativa = justificativa;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public double getScoreCalculado() {
        return scoreCalculado;
    }

    public void setScoreCalculado(double scoreCalculado) {
        this.scoreCalculado = scoreCalculado;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}