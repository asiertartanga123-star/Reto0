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
import java.sql.ResultSet;
import java.util.List;

public class DaoImplementacionEkain implements DaoEkain {

    // ==================== ATRIBUTOS ====================

    private Connection con;
    private PreparedStatement stmt;

    // ==================== SENTENCIAS SQL ====================

    final String REGISTRAR_CLIENTE = "INSERT INTO CLIENTE (id_C, nombre_C, email, telefono, ruta) VALUES (?,?,?,?,?)";
    final String EXISTECLIEN = "SELECT id_C FROM CLIENTE WHERE id_C = ?";
    final String REGISTRAR_AEROLINEA = "INSERT INTO AEROLINEA (id_A, nombre_A, pais, codigoIATA) VALUES (?,?,?,?)";
    final String OBTENER_AEROLINEA = "SELECT id_A, nombre_A, pais, codigoIATA FROM AEROLINEA WHERE id_A = ?";

    // ==================== CONEXIÓN ====================

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

    // ==================== CLIENTES ====================

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

    // ==================== AEROLÍNEAS ====================

    public void registrarAerolinea(Aerolinea aerolinea) throws SQLException {
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(REGISTRAR_AEROLINEA);
            stmt.setInt(1, aerolinea.getId_A());
            stmt.setString(2, aerolinea.getNombre_A());
            stmt.setString(3, aerolinea.getPais());
            stmt.setString(4, aerolinea.getCodigoIATA());
            stmt.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Aerolinea obtenerAerolinea(int id) throws SQLException {
        Aerolinea aero = null;
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(OBTENER_AEROLINEA);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                aero = new Aerolinea(
                        rs.getInt("id_A"),
                        rs.getString("nombre_A"),
                        rs.getString("pais"),
                        rs.getString("codigoIATA"));
            }
        } finally {
            closeConnection();
        }
        return aero;
    }

    // ==================== VUELOS ====================

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
    }
}