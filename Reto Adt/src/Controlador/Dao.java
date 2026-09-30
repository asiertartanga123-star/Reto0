/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;
import java.util.List;
import java.sql.SQLException;

/** Define las operaciones de acceso a datos para clientes y aerolíneas. */
public interface Dao {

    /** Registra un cliente y devuelve si se insertó correctamente. */
    public boolean registrarCliente(Cliente clien) throws SQLException;

    /** Indica si ya existe un cliente con el ID indicado. */
    boolean existeCliente(int id)  throws SQLException;

    /** Devuelve los IDs de todos los clientes registrados. */
    List<Integer> listarIdsClientes()  throws SQLException;

    /** Devuelve los IDs de todas las aerolíneas registradas. */
    List<Integer> listarIdsAerolinea()  throws SQLException;

    /** Registra una aerolínea en la base de datos. */
    void registrarAerolinea(Aerolinea aerolinea) throws SQLException;
    
}
