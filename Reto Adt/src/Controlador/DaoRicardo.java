/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author Ricardo.Soza
 */
public interface DaoRicardo {
        // Cliente(BD)
    
    public boolean registrarCliente(Cliente clien) throws SQLException;
    boolean existeCliente(int id)  throws SQLException;
    List<Integer> listarIdsClientes()  throws SQLException;
    List<Integer> listarIdsAerolinea()  throws SQLException;
     void registrarAerolinea(Aerolinea aerolinea) throws SQLException;
    
}
