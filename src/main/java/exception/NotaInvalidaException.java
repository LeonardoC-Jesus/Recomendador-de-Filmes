package exception;

public class NotaInvalidaException extends RuntimeException {

    public NotaInvalidaException() {
        super("Nota inválida");
    }
}
