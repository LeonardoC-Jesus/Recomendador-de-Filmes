package service;

import model.Filme;

import java.util.List;


/**
 * Interface de integração com a API de catálogo de filmes.
 * Define o contrato para recuperação da lista de filmes disponíveis para recomendação.
 * @author Gabriel Câncio
 */
public interface CatalogoFilmesAPI {

    /**
     * Recupera todos os filmes disponíveis no catálogo externo ou banco de dados.
     * @return Uma {@link List} contendo todos os objetos {@link Filme} cadastrados.
     * Retorna uma lista vazia caso não existam filmes disponíveis.
     */
    public List<Filme> buscarFilmes();
}