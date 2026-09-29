package exception;

public class ClienteNoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    public ClienteNoEncontradoException(int idCliente) {
        super("No existe ningun cliente con id " + idCliente);
    }
}
