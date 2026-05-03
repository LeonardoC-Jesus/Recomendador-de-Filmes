package service;

import model.Recomendacao;

import java.util.List;

public interface HistoricoUsuarioRepository {

    void salvar (Recomendacao recomendacao);

    List<Recomendacao> consultarTudo();
}