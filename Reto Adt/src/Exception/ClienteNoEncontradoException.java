package exception;

/** Se lanza cuando se consulta un cliente que no está registrado. */
public class ClienteNoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción indicando el ID de cliente que falta. */
    public ClienteNoEncontradoException(int idCliente) {
        super("No existe ningun cliente con id " + idCliente);
    }
}
