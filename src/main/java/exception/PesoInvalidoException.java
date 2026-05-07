package exception;

public class PesoInvalidoException extends RuntimeException {

    public PesoInvalidoException() {
        super("Peso inválido");
    }
}