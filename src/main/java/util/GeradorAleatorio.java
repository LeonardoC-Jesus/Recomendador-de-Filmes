package util;

/**
 * Interface para componentes que realizam sorteios aleatórios no sistema.
 * Útil para o modo de recomendação "Surpreenda-me" ou desempates.
 * @author Gabriel Câncio
 */
public interface GeradorAleatorio {

    /**
     * Sorteia um número inteiro dentro de um limite especificado.
     * @param limite O valor máximo (exclusivo) para o sorteio.
     * @return Um número inteiro aleatório entre 0 e (limite - 1).
     */
    int sortear(int limite);
}