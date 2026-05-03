package service;

import model.Recomendacao;

import java.util.List;

/**
 * Interface que define as operações de persistência do histórico de recomendações.
 * Responsável por armazenar e recuperar todas as sugestões enviadas aos usuários.
 * * @author Gabriel Câncio
 */
public interface HistoricoUsuarioRepository {

    /**
     * Armazena uma nova recomendação no histórico do sistema.
     * * @param recomendacao O objeto contendo o filme sugerido e os detalhes do cálculo.
     */
    void salvar (Recomendacao recomendacao);

    /**
     * Recupera a lista completa de todas as recomendações já realizadas.
     * * @return Uma {@link List} contendo todos os objetos {@link Recomendacao} registrados.
     */
    List<Recomendacao> consultarTudo();
}