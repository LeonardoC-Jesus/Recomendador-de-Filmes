package data;

import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Idioma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoFilmesAPI implements service.CatalogoFilmesAPI {

    List<Filme> catalogo = new ArrayList<Filme>(List.of(
            new Filme(1L, "Interestelar", 2014, 169, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 95),

            new Filme(2L, "O Auto da Compadecida", 2000, 104, List.of(),
            Idioma.PORTUGUES, ClassificacaoEtaria.LIVRE, 98),

            new Filme(3L, "Clube da Luta", 1999, 139, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZOITO, 96),

            new Filme(4L, "Vingadores: Ultimato", 2019, 181, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 94),

            new Filme(5L, "Coringa", 2019, 122, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZOITO, 91),

            new Filme(6L, "Parasita", 2019, 132, List.of(),
                    Idioma.FRANCES, ClassificacaoEtaria.DEZESSEIS, 97),

            new Filme(7L, "Cidade de Deus", 2002, 130, List.of(),
                    Idioma.PORTUGUES, ClassificacaoEtaria.DEZOITO, 98),

            new Filme(8L, "Matrix", 1999, 136, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 95),

            new Filme(9L, "Gladiador", 2000, 155, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 93),

            new Filme(10L, "Avatar", 2009, 162, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 89),

            new Filme(11L, "Titanic", 1997, 195, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 92),

            new Filme(12L, "Toy Story", 1995, 81, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 90),

            new Filme(13L, "Shrek", 2001, 90, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 91),

            new Filme(14L, "Procurando Nemo", 2003, 100, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 89),

            new Filme(15L, "Homem-Aranha", 2002, 121, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 87),

            new Filme(16L, "Batman: O Cavaleiro das Trevas", 2008, 152, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 99),

            new Filme(17L, "Harry Potter e a Pedra Filosofal", 2001, 152, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 88),

            new Filme(18L, "Senhor dos Anéis", 2001, 178, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 98),

            new Filme(19L, "Star Wars", 1977, 121, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 94),

            new Filme(20L, "Django Livre", 2012, 165, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZOITO, 93),

            new Filme(21L, "Forrest Gump", 1994, 142, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 97),

            new Filme(22L, "Rocky", 1976, 120, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 89),

            new Filme(23L, "Pantera Negra", 2018, 134, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DOZE, 88),

            new Filme(24L, "Encanto", 2021, 102, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 85),

            new Filme(25L, "Up: Altas Aventuras", 2009, 96, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 94),

            new Filme(26L, "Ratatouille", 2007, 111, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 93),

            new Filme(27L, "Divertidamente", 2015, 95, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 96),

            new Filme(28L, "Frozen", 2013, 102, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.LIVRE, 84),

            new Filme(29L, "Deadpool", 2016, 108, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZOITO, 90),

            new Filme(30L, "John Wick", 2014, 101, List.of(),
                    Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 91)
    ));

    @Override
    public List<Filme> buscarFilmes() throws IOException {
        return catalogo;
    }
}
