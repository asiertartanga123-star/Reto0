package exception;

/** Representa un error de validación de un número de teléfono. */
public class TelefonoInvalidoException extends Exception {

    /** Crea la excepción con el motivo del error. */
    public TelefonoInvalidoException(String message) {
        super(message);
    }
}