/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

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

      
    public void consultarvuelos(Cliente cliente) throws Exception {
        boolean encontrado = false;

        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream("vuelos.dat"))) {
            while (true) {
                Vuelo vuelo = (Vuelo) entrada.readObject();
                if (vuelo.getId_c == cliente.getId_C()) {
                    System.out.println(vuelo);
                    encontrado = true;
                }
            }   
        } catch (EOFException e) {
            // Fin normal del fichero.
        } catch (FileNotFoundException e) {
            System.out.println("No existe el fichero vuelos.dat");
            return;
        }

        if (!encontrado) {
            System.out.println("El cliente no tiene vuelos registrados.");
        }
}}

