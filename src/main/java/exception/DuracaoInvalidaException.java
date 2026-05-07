package exception;

public class DuracaoInvalidaException extends RuntimeException {

    public DuracaoInvalidaException() {
        super("Duração inválida!");
    }
}