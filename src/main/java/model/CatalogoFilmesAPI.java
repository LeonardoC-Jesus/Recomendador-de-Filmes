package model;

import java.util.List;

/**
 * Interface que define o contrato de comunicação com a fonte de dados de filmes.
 * Responsável por obter a lista bruta de filmes que serão processados pelo sistema
 * de recomendação.
 * * @author Gabriel Câncio
 */
public interface CatalogoFilmesAPI {

    /**
     * Realiza a busca de todos os filmes disponíveis no catálogo.
     * * @return Uma {@link List} contendo os objetos {@link Filme}.
     * Retorna uma lista vazia caso nenhum filme seja encontrado.
     */
    List<Filme> buscarFilmes();
}
