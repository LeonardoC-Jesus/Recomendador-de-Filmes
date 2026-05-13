package model.enums;

import exception.IdiomaInvalidoException;

import java.text.Normalizer;
import java.util.regex.Pattern;

public enum Idioma {
    PORTUGUES("portugues"),
    INGLES("ingles"),
    JAPONES("japones"),
    ESPANHOL("espanhol"),
    FRANCES("frances"),
    ITALIANO("italiano");

    public final String valor;

    Idioma(String valor) {
        this.valor = valor;
    }

    public static Idioma pegarPorValor(String idiomaUsuario) {
        String idiomaNormalizado = Normalizer.normalize(idiomaUsuario, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        idiomaUsuario = pattern.matcher(idiomaNormalizado).replaceAll("").toLowerCase();

        for (Idioma idioma: Idioma.values()) {
            if (idioma.getValor().equals(idiomaUsuario)) {
                return idioma;
            }
        }
        throw new IdiomaInvalidoException();
    }

    public String getValor() {
        return valor;
    }
}
