package model;

import service.NotificadorPush;

public class Usuario {
    private String nome;
    private int idade;
    private PerfilCinefilo perfilCinefilo;
    private Recomendacao recomendacao;
    private boolean notificacoesHabilitadas;

    public Usuario(String nome, int idade, boolean notificacoesHabilitadas) {
        this.nome = nome;
        this.idade = idade;
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public Recomendacao getRecomendacao() {
        return recomendacao;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public boolean isNotificacoesHabilitadas() {
        return notificacoesHabilitadas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setRecomendacao(Recomendacao recomendacao) {
        this.recomendacao = recomendacao;
    }

    public void setPerfilCinefilo(PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
    }

    public void setNotificacoesHabilitadas(boolean notificacoesHabilitadas) {
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }
}
