/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author Ricardo.Soza
 */
/** Indica que un identificador no cumple las reglas de validación. */
public class IdInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción con el motivo de la validación fallida. */
    public IdInvalidoException(String mensaje) {
        super(mensaje);
    }
}