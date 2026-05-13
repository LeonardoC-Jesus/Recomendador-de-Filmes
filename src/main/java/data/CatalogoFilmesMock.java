package data;

import model.Filme;
import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoFilmesMock implements service.CatalogoFilmesAPI {

    List<Filme> catalogoMock = new ArrayList<>();

    Filme interestelar = new Filme(1L, "Interestelar", 2014, 169,
            List.of(Genero.FICCAO_CIENTIFICA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 95);

    Filme autoDaCompadecida = new Filme(2L, "O Auto da Compadecida", 2000, 104,
            List.of(Genero.COMEDIA), Idioma.PORTUGUES,
            ClassificacaoEtaria.LIVRE, 98);


    Filme clubeDaLuta = new Filme(3L, "Clube da Luta", 1999, 139,
            List.of(Genero.DRAMA), Idioma.INGLES,
            ClassificacaoEtaria.DEZOITO, 96);


    Filme ultimato = new Filme(4L, "Vingadores: Ultimato", 2019, 181,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 94);


    Filme coringa = new Filme(5L, "Coringa", 2019, 122,
            List.of(Genero.DRAMA), Idioma.INGLES,
            ClassificacaoEtaria.DEZOITO, 91);


    Filme parasita = new Filme(6L, "Parasita", 2019, 132,
            List.of(Genero.TERROR), Idioma.FRANCES,
            ClassificacaoEtaria.DEZESSEIS, 97);


    Filme cidadeDeDeus = new Filme(7L, "Cidade de Deus", 2002, 130,
            List.of(Genero.DRAMA), Idioma.PORTUGUES,
            ClassificacaoEtaria.DEZOITO, 98);


    Filme matrix = new Filme(8L, "Matrix", 1999, 136,
            List.of(Genero.FICCAO_CIENTIFICA), Idioma.INGLES,
            ClassificacaoEtaria.DEZESSEIS, 95);


    Filme gladiador = new Filme(9L, "Gladiador", 2000, 155,
            List.of(Genero.ACAO), Idioma.INGLES, ClassificacaoEtaria.DEZESSEIS, 93);


    Filme avatar = new Filme(10L, "Avatar", 2009, 162,
            List.of(Genero.FICCAO_CIENTIFICA), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 89);


    Filme titanic = new Filme(11L, "Titanic", 1997, 195,
            List.of(Genero.ROMANCE), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 92);


    Filme toyStory = new Filme(12L, "Toy Story", 1995, 81,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 90);


    Filme shrek = new Filme(13L, "Shrek", 2001, 90,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 91);


    Filme procurandoNemo = new Filme(14L, "Procurando Nemo", 2003, 100,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 89);


    Filme homemAranha = new Filme(15L, "Homem-Aranha", 2002, 121,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 87);


    Filme cavaleiroDasTrevas = new Filme(16L, "Batman: O Cavaleiro das Trevas", 2008, 152,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 99);


    Filme harryPotter = new Filme(17L, "Harry Potter e a Pedra Filosofal", 2001, 152,
            List.of(Genero.DRAMA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 88);


    Filme senhorDosAneis = new Filme(18L, "Senhor dos Anéis", 2001, 178,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 98);


    Filme starWars = new Filme(19L, "Star Wars", 1977, 121,
            List.of(Genero.FICCAO_CIENTIFICA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 94);


    Filme djangoLivre = new Filme(20L, "Django Livre", 2012, 165,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DEZOITO, 93);


    Filme forrestGump = new Filme(21L, "Forrest Gump", 1994, 142,
            List.of(Genero.DRAMA), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 97);


    Filme rocky = new Filme(22L, "Rocky", 1976, 120,
            List.of(Genero.DRAMA), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 89);


    Filme panteraNegra = new Filme(23L, "Pantera Negra", 2018, 134,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DOZE, 88);


    Filme encanto = new Filme(24L, "Encanto", 2021, 102,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 85);


    Filme up = new Filme(25L, "Up: Altas Aventuras", 2009, 96,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 94);


    Filme ratatouille = new Filme(26L, "Ratatouille", 2007, 111,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 93);

    Filme divertidamente = new Filme(27L, "Divertidamente", 2015, 95,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 96);


    Filme frozen = new Filme(28L, "Frozen", 2013, 102,
            List.of(Genero.COMEDIA), Idioma.INGLES,
            ClassificacaoEtaria.LIVRE, 84);

    Filme deadpool = new Filme(29L, "Deadpool", 2016, 108,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DEZOITO, 90);

    Filme johnWick = new Filme(30L, "John Wick", 2014, 101,
            List.of(Genero.ACAO), Idioma.INGLES,
            ClassificacaoEtaria.DEZESSEIS, 91);

    @Override
    public List<Filme> buscarFilmes() throws IOException {
        catalogoMock.add(interestelar);
        catalogoMock.add(autoDaCompadecida);
        catalogoMock.add(clubeDaLuta);
        catalogoMock.add(ultimato);
        catalogoMock.add(coringa);
        catalogoMock.add(parasita);
        catalogoMock.add(cidadeDeDeus);
        catalogoMock.add(matrix);
        catalogoMock.add(gladiador);
        catalogoMock.add(avatar);
        catalogoMock.add(titanic);
        catalogoMock.add(toyStory);
        catalogoMock.add(shrek);
        catalogoMock.add(procurandoNemo);
        catalogoMock.add(homemAranha);
        catalogoMock.add(cavaleiroDasTrevas);
        catalogoMock.add(harryPotter);
        catalogoMock.add(senhorDosAneis);
        catalogoMock.add(starWars);
        catalogoMock.add(djangoLivre);
        catalogoMock.add(forrestGump);
        catalogoMock.add(rocky);
        catalogoMock.add(panteraNegra);
        catalogoMock.add(encanto);
        catalogoMock.add(up);
        catalogoMock.add(ratatouille);
        catalogoMock.add(divertidamente);
        catalogoMock.add(frozen);
        catalogoMock.add(deadpool);
        catalogoMock.add(johnWick);

        return catalogoMock;
    }
}
