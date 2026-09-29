/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author Ricardo.Soza
 */
/** Indica que un identificador ya está en uso. */
public class IdDuplicadoException extends Exception {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción con el detalle del identificador duplicado. */
    public IdDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
