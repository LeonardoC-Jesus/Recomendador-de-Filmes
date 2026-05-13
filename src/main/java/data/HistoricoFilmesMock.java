package data;

import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFilmesMock {
    private List<Filme> historicoFilmesMock = new ArrayList<>();

    Filme frozen = new Filme(28L, "Frozen", 2013, 102,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 84);

    Filme ratatouille = new Filme(26L, "Ratatouille", 2007, 111,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 93);

    Filme djangoLivre = new Filme(20L, "Django Livre", 2012, 165,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DEZOITO, 93);

    public List<Filme> consultarHistorico() {

        historicoFilmesMock.add(djangoLivre);
        historicoFilmesMock.add(ratatouille);
        historicoFilmesMock.add(frozen);

        return historicoFilmesMock;
    }
}
