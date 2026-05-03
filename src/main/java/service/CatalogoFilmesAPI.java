package service;

import model.Filme;

import java.util.List;

public interface CatalogoFilmesAPI {

    public List<Filme> buscarFilmes();
}