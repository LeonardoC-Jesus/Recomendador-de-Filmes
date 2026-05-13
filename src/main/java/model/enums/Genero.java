package model.enums;

public enum Genero {
    ACAO("Ação"),
    COMEDIA("Comédia"),
    TERROR("Terror"),
    DOCUMENTARIO("Documentário"),
    FICCAO_CIENTIFICA("Ficção Cientifíca"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    COMEDIA_ROMANTICA("Comédia Romântica");

    private final String valor;

    Genero(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
