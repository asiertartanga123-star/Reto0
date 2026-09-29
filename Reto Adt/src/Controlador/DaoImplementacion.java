package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;
import Modelo.Vuelo;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import exception.ClienteNoEncontradoException;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DaoImplementacion implements Dao {

    private static final String URL = "jdbc:mysql://localhost:3306/volaredb?serverTimezone=Europe/Madrid&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "abcd*1234";

    private final File ficheroVuelos;

    private Connection con;
    private PreparedStatement stmt;

    private static final String REGISTRAR_CLIENTE = "INSERT INTO CLIENTE (id_C, nombre_C, email, telefono, ruta) VALUES (?,?,?,?,?)";
    private static final String EXISTE_CLIENTE = "SELECT id_C FROM CLIENTE WHERE id_C = ?";
    private static final String LISTAR_IDS_CLIENTES = "SELECT id_C FROM CLIENTE";
    private static final String REGISTRAR_AEROLINEA = "INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA) VALUES (?, ?, ?, ?)";
    private static final String LISTAR_IDS_AEROLINEA = "SELECT id_A FROM AEROLINEA";
    private static final String OBTENER_AEROLINEA = "SELECT id_A, nombre_A, pais, codigoIATA FROM AEROLINEA WHERE id_A = ?";
    private static final String OBTENER_RUTA_CLIENTE = "SELECT ruta FROM cliente WHERE id_C = ?";

    public DaoImplementacion() {
        this(new File("vuelos.dat"));
    }

    public DaoImplementacion(File ficheroVuelos) {
        this.ficheroVuelos = ficheroVuelos;
    }

    public static DaoImplementacion getInstance() {
        return new DaoImplementacion();
    }

    private void openConnection() throws SQLException {
        con = (Connection) DriverManager.getConnection(URL, USER, PASS);
    }

    private void closeConnection() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
        stmt = null;
        con = null;
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
        } finally {
            closeConnection();
        }
        return insertado;
    }

    @Override
    public boolean existeCliente(int id) throws SQLException {
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(EXISTE_CLIENTE);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Integer> listarIdsClientes() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(LISTAR_IDS_CLIENTES);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_C"));
                }
            }
        } finally {
            closeConnection();
        }
        return ids;
    }

    @Override
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
    public List<Integer> listarIdsAerolinea() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(LISTAR_IDS_AEROLINEA);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("id_A"));
                }
            }
        } finally {
            closeConnection();
        }
        return ids;
    }

    public Aerolinea obtenerAerolinea(int id) throws SQLException {
        Aerolinea aero = null;
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(OBTENER_AEROLINEA);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aero = new Aerolinea(rs.getInt("id_A"), rs.getString("nombre_A"), rs.getString("pais"), rs.getString("codigoIATA"));
                }
            }
        } finally {
            closeConnection();
        }
        return aero;
    }

    public void consultarvuelos(Cliente cliente) throws Exception {
        openConnection();
        try {
            stmt = (PreparedStatement) con.prepareStatement(OBTENER_RUTA_CLIENTE);
            stmt.setInt(1, cliente.getId_C());
            try (ResultSet resultado = stmt.executeQuery()) {
                if (!resultado.next()) {
                    throw new SQLException("No existe el cliente con id " + cliente.getId_C());
                }
                cliente.setRuta(resultado.getString("ruta"));
            }
        } finally {
            closeConnection();
        }

        boolean encontrado = false;
        if (!ficheroVuelos.exists()) {
            System.out.println("No existe el fichero vuelos.dat");
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroVuelos))) {
            while (true) {
                Vuelo vuelo = (Vuelo) entrada.readObject();
                if (vuelo.getIdReservados().contains(cliente.getId_C())) {
                    System.out.println(vuelo);
                    encontrado = true;
                }
            }
        } catch (EOFException e) {
            // fin normal
        } catch (FileNotFoundException e) {
            System.out.println("No existe el fichero vuelos.dat");
            return;
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error leyendo vuelos.dat", e);
        }

        if (!encontrado) {
            System.out.println("El cliente no tiene vuelos registrados.");
        }

        try {
            cliente.abrirImagen();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró la imagen del cliente en la ruta: " + cliente.getRuta());
        }
    }

    public List<Vuelo> verHistorialVuelos(int idCliente) throws ClienteNoEncontradoException, SQLException, IOException {
        if (!existeCliente(idCliente)) {
            throw new ClienteNoEncontradoException(idCliente);
        }
        return leerHistorial(idCliente, LocalDate.now());
    }

    List<Vuelo> leerHistorial(int idCliente, LocalDate hoy) throws IOException {
        List<Vuelo> historial = new ArrayList<>();

        if (!ficheroVuelos.exists()) {
            return historial;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroVuelos))) {
            while (true) {
                Vuelo vuelo = (Vuelo) ois.readObject();
                if (vuelo.getIdReservados().contains(idCliente) && vuelo.getFechaSalida().isBefore(hoy)) {
                    historial.add(vuelo);
                }
            }
        } catch (EOFException e) {
            // fin normal
        } catch (ClassNotFoundException e) {
            throw new IOException("El fichero " + ficheroVuelos.getName() + " contiene objetos no válidos", e);
        }

        historial.sort(Comparator.comparing(Vuelo::getFechaSalida));
        return historial;
    }
}

