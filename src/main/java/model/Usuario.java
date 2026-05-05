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

    public int getIdade() {
        return idade;
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

    public void setPerfilCinefilo(PerfilCinefilo perfilCinefilo) {
        this.perfilCinefilo = perfilCinefilo;
    }

    public void setNotificacoesHabilitadas(boolean notificacoesHabilitadas) {
        this.notificacoesHabilitadas = notificacoesHabilitadas;
    }
}
