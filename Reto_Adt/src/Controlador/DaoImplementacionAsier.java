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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Asier.Prieto
 */
public class DaoImplementacionAsier implements DaoAsier{
   
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */ 
    // Atributos
	private Connection con;
	private PreparedStatement stmt;
        
        private void openConnection() {
		try {
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/volareDB?serverTimezone=Europe/Madrid&useSSL=false", "root",
					"abcd*1234");
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
		}
	}
        
        // Sentencias
        
        String REGISTRAR_AEROLINEA = "INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA) VALUES (?, ?, ?, ?)";
        
        private void closeConnection() throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
		if (con != null)
			con.close();
	}
        
        
        public void registrarAerolinea(Aerolinea aerolinea) throws SQLException{
              openConnection();
            stmt = (PreparedStatement) con.prepareStatement(REGISTRAR_AEROLINEA);
            stmt.setInt(1, aerolinea.getId_A());
            stmt.setString(2, aerolinea.getNombre_A());
            stmt.setString(3, aerolinea.getPais());
            stmt.setString(4, aerolinea.getCodigoIATA());
            stmt.executeUpdate();
            
        }

    @Override
    public void consultarvuelos(Cliente cliente) throws Exception {
        boolean encontrado = false;

        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream("vuelos.dat"))) {
            while (true) {
                Vuelo vuelo = (Vuelo) entrada.readObject();
                if (vuelo.getId_C() == cliente.getId_C()) {
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
    }

  
    
}

