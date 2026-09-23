/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Ekain.Bedoya
 */
public class DaoImplementacionEkain {

    // Atributos
    private Connection con;
    private PreparedStatement stmt;
    
    //Sentencias SQL
    
    final String REGISTRAR_CLIENTE = "INSERT INTO CLIENTE (id_C, nombre_C, email, telefono, ruta) VALUES (?,?,?,?,?)";
    final String EXISTECLIEN = "SELECT id_C FROM CLIENTE WHERE id_C = ?";

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

}

