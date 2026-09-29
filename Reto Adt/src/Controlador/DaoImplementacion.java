/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;
import Modelo.Vuelo;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ekain.Bedoya
 */
public class DaoImplementacion implements DaoRicardo {

    // Atributos
    private Connection con;
    private PreparedStatement stmt;

    //Sentencias SQL
    final String REGISTRAR_CLIENTE = "INSERT INTO CLIENTE (id_C, nombre_C, email, telefono, ruta) VALUES (?,?,?,?,?)";
    final String EXISTECLIEN = "SELECT id_C FROM CLIENTE WHERE id_C = ?";
    final String LISTAR_IDS_CLIENTES = "SELECT id_C FROM CLIENTE";
    String REGISTRAR_AEROLINEA = "INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA) VALUES (?, ?, ?, ?)";
    final String LISTAR_IDS_AEROLINEA = "SELECT id_A FROM AEROLINEA";

    private void openConnection() {
        try {
            con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/volaredb?serverTimezone=Europe/Madrid&useSSL=false", "root",
                    "abcd*1234");
        } catch (SQLException e) {
            System.out.println("Error al intentar abrir la BD");
            e.printStackTrace();
        }
    }

    private void closeConnection() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    @Override
    public boolean registrarCliente(Cliente clien) throws SQLException {
        openConnection();
        boolean insertado = false;
        try {
            stmt = (PreparedStatement) con.prepareStatement(REGISTRAR_CLIENTE);
            stmt.setInt(1, clien.getId_C());
            stmt.setString(2, clien.getNombre_V());
            stmt.setString(3, clien.getMail());
            stmt.setString(4, clien.getTlf());
            stmt.setString(5, clien.getRuta());
            stmt.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return insertado;
    }

    @Override
    public boolean existeCliente(int id) throws SQLException {
        boolean existe = false;
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(EXISTECLIEN);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            existe = rs.next();
        } finally {
            closeConnection();
        }
        return existe;
    }

    @Override
    public List<Integer> listarIdsClientes() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(LISTAR_IDS_CLIENTES);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_C"));
            }
            rs.close();
        } finally {
            closeConnection();
        }
        return ids;
    }

    @Override
    public void registrarAerolinea(Aerolinea aerolinea) throws  SQLException {
           openConnection();
            stmt = (PreparedStatement) con.prepareStatement(REGISTRAR_AEROLINEA);
            stmt.setInt(1, aerolinea.getId_A());
            stmt.setString(2, aerolinea.getNombre_A());
            stmt.setString(3, aerolinea.getPais());
            stmt.setString(4, aerolinea.getCodigoIATA());
            stmt.executeUpdate();
    }

    @Override
    public List<Integer> listarIdsAerolinea() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(LISTAR_IDS_AEROLINEA);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id_A"));
            }
            rs.close();
        } finally {
            closeConnection();
        }
        return ids;
        
    }

}
