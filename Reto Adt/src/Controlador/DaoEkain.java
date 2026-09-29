package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;
import java.sql.SQLException;

public interface DaoEkain {

    boolean registrarCliente(Cliente clien) throws SQLException;

    boolean existeCliente(int id) throws SQLException;

    void registrarAerolinea(Aerolinea aerolinea) throws SQLException;

    Aerolinea obtenerAerolinea(int id) throws SQLException;
}