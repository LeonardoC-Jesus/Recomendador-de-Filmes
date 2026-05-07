package model;

public class Usuario {
    private String nome;
    private int idade;
    private PerfilCinefilo perfilCinefilo;
    private boolean notificacoesHabilitadas;

    public Usuario(String nome, int idade, PerfilCinefilo perfilCinefilo, boolean notificacoesHabilitadas) {
        this.nome = nome;
        this.idade = idade;
        this.perfilCinefilo = perfilCinefilo;
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public PerfilCinefilo getPerfilCinefilo() {
        return perfilCinefilo;
    }

    public void setPerfilCinefilo(PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
    }

    public boolean isNotificacoesHabilitadas() {
        return notificacoesHabilitadas;
    }

    public void setNotificacoesHabilitadas(boolean notificacoesHabilitadas) {
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }
}