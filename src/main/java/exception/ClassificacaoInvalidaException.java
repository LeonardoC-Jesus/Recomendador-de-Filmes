package exception;

public class ClassificacaoInvalidaException extends RuntimeException {

    public ClassificacaoInvalidaException() {
        super("Classificção etária inválida");
    }

}
