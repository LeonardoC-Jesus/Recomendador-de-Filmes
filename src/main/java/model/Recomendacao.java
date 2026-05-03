package model;

public class Recomendacao {
    private Filme filme;
    private int scoreCalculado;
    private String justificativa;

    public Recomendacao(Filme filme, int scoreCalculado, String justificativa) {
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

    public int getScoreCalculado() {
        return scoreCalculado;
    }

    public void setScoreCalculado(int scoreCalculado) {
        this.scoreCalculado = scoreCalculado;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
}