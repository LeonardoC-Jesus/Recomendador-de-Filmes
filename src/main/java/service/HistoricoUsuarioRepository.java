package service;

import model.Filme;
import model.Recomendacao;

import java.util.List;

/**
 * Interface que define as operações de persistência do histórico de recomendações.
 * Responsável por armazenar e recuperar todas as sugestões enviadas aos usuários.
 * @author Gabriel Câncio
 */
public interface HistoricoUsuarioRepository {

    /**
     * Armazena um novo filme no histórico do sistema.
     * @param filme O objeto contendo o filme sugerido e os detalhes do cálculo.
     */
    void salvar (Filme filme);

    /**
     * Recupera a lista completa de todas as recomendações já realizadas.
     * @return Uma {@link List} contendo todos os objetos {@link Recomendacao} registrados.
     */
    List<Filme> consultarTudo();

    /**
     * Armazena todas as ultimas recomendações no histórico do sistema
     * @param recomendacoes uma lista de Objetos de recomendacoes.
     */
     void registrarRecomendacao(List<Recomendacao> recomendacoes);
}