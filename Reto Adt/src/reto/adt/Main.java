package reto.adt;

import Controlador.DaoImplementacion;
import Controlador.Dao;
import Exception.IdDuplicadoException;
import Exception.IdInvalidoException;
import Modelo.Aerolinea;
import Modelo.Clase;
import Modelo.Cliente;
import Modelo.Vuelo;
import Util.AñadirObjetoSinCabecera;
import Util.Util;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Punto de entrada de VOLAREDB y coordinador de las operaciones del menú.
 * Los clientes y las aerolíneas se gestionan en MySQL; los vuelos se guardan
 * en el fichero serializado {@code vuelos.dat}.
 */
public class Main {

    /** Inicia el menú y procesa opciones hasta que el usuario elige salir. */
    public static void main(String[] args) {
        File fichVuelo = new File("vuelos.dat");
        int opcion;
        do {
            mostrarMenu();
            opcion = Util.leerInt("Elige una opción: ");
            try {
                ejecutar(opcion, fichVuelo);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    /** Imprime las operaciones disponibles en la consola. */
    private static void mostrarMenu() {
        System.out.println("\n===== VOLAREDB =====");
        System.out.println("1. Registrar aerolínea");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Registrar vuelo");
        System.out.println("4. Reservar un vuelo");
        System.out.println("5. Consultar vuelos futuros");
        System.out.println("6. Consultar vuelos de un usuario");
        System.out.println("7. Ver historial de vuelos de un cliente");
        System.out.println("8. Listar vuelos");
        System.out.println("0. Salir");
    }

    /** Dirige la opción elegida a la operación correspondiente. */
    private static void ejecutar(int opcion, File fichVuelo) throws Exception {
        switch (opcion) {
            case 1:
                registrarAerolinea();
                break;
            case 2:
                registrarCliente();
                break;
            case 3:
                registrarVuelo(fichVuelo);
                break;
            case 4:
                reservarVuelo(fichVuelo);
                break;
            case 5:
                consultarVuelosFuturos(fichVuelo);
                break;
            case 6:
                consultarVuelosDeUsuario();
                break;
            case 7:
                historialCliente();
                break;
            case 8:
                listarVuelo(fichVuelo);
                break;
            case 0:
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    /** Solicita los datos de una aerolínea y los inserta en MySQL. */
    private static void registrarAerolinea() throws Exception {
        System.out.println("--- Registrar aerolínea ---");
        int id = Util.leerInt("Id: ");
        String nombre = Util.introducirCadena("Nombre: ");
        String pais = Util.introducirCadena("País: ");
        String iata = Util.introducirCadena("Código IATA: ");

        Aerolinea aerolinea = new Aerolinea(id, nombre, pais, iata);
        DaoImplementacion dao = new DaoImplementacion();
        dao.registrarAerolinea(aerolinea);

        System.out.println("Aerolínea registrada correctamente.");
    }

    /** Valida y registra un cliente en la base de datos. */
    private static void registrarCliente() {
        DaoImplementacion dao = new DaoImplementacion();

        int id = pedirIdValido(dao);
        String nombre = Util.introducirCadena("Introduce el nombre: ");
        String email = Util.validarEmail("Introduce el email: ");
        String tlf = Util.introducirCadena("Introduce el número de teléfono: ");

        Cliente cliente = new Cliente(id, nombre, email, tlf);
        try {
            boolean insertado = dao.registrarCliente(cliente);
            if (insertado) {
                System.out.println("Cliente registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el cliente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }
    }

    /** Repite la solicitud hasta obtener un ID no negativo y no utilizado. */
    private static int pedirIdValido(DaoImplementacion dao) {
        int id = -1;
        boolean idOk = false;

        do {
            try {
                id = Util.leerInt("Introduce el id del cliente: ");
                if (id < 0) {
                    throw new IdInvalidoException("El id no puede ser negativo.");
                }
                if (dao.existeCliente(id)) {
                    throw new IdDuplicadoException("Ya existe un cliente con ese id.");
                }
                idOk = true;
            } catch (IdInvalidoException | IdDuplicadoException e) {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error al comprobar el id: " + e.getMessage());
            }
        } while (!idOk);

        return id;
    }

    /** Crea vuelos y los añade al fichero serializado sin duplicar sus IDs. */
    private static void registrarVuelo(File fichVuelo) {
        int id;
        ObjectOutputStream oos = null;
        Vuelo v;
        int mas;

        try {
            if (fichVuelo.exists()) {
                oos = new AñadirObjetoSinCabecera(new FileOutputStream(fichVuelo, true));
            } else {
                oos = new ObjectOutputStream(new FileOutputStream(fichVuelo));
            }

            do {
                do {
                    id = Util.leerInt("Introduce el id del vuelo");
                } while (id < 1);

                v = obtenerVuelo(fichVuelo, id);
                if (v != null) {
                    System.out.println("Ya existe un vuelo con ese id");
                } else {
                    System.out.println("AÑADIENDO VUELO NUEVO");
                    String origen = Util.introducirCadena("El origen del vuelo");
                    String destino = Util.introducirCadena("El destino del vuelo");
                    LocalDate fechSalida = Util.pidoFechaDMA("La fecha de salida del vuelo");
                    int numPlaza = Util.leerInt("Introduce el número de plazas del vuelo");
                    Clase clas = null;
                    while (clas == null) {
                        for (Clase c : Clase.values()) {
                            System.out.print(c + " - ");
                        }
                        String post = Util.introducirCadena("Introduce la clase que tendrá el vuelo").toUpperCase();
                        for (Clase c : Clase.values()) {
                            if (c.name().equals(post)) {
                                clas = c;
                                break;
                            }
                        }
                    }

                    int idAerolinea = Util.leerInt("Introduce el id de la aerolínea");
                    if (!listarIdsAerolinea(idAerolinea)) {
                        System.out.println("No existe una aerolínea con ese id");
                        return;
                    }

                    v = new Vuelo(id, origen, destino, fechSalida, numPlaza, clas, idAerolinea);
                    oos.writeObject(v);
                }

                mas = Util.leerInt("Quieres añadir un vuelo más? (1=Si/2=No)");
            } while (mas == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Añade el ID del cliente a la lista de reservas del vuelo elegido. */
    private static void reservarVuelo(File fichVuelo) throws SQLException {
        Vuelo v;
        int idCliente = Util.leerInt("Introduce el id del cliente");

        if (!listarIdsClientes(idCliente)) {
            System.out.println("No hay un cliente con ese id");
            return;
        }

        int idVuelo = Util.leerInt("Introduce el id del vuelo");
        v = obtenerVuelo(fichVuelo, idVuelo);
        if (v == null) {
            System.out.println("No existe ese vuelo con ese id");
            return;
        }

        v.getIdReservados().add(idCliente);
        actualizarVueloEnFichero(fichVuelo, v);
        System.out.println("Reserva añadida correctamente");
    }

    /** Comprueba si existe la aerolínea indicada en la base de datos. */
    private static boolean listarIdsAerolinea(int idAerolinea) throws SQLException {
        Dao dao = new DaoImplementacion();
        List<Integer> ids = dao.listarIdsAerolinea();
        if (ids.isEmpty()) {
            return false;
        }
        for (int id : ids) {
            if (id == idAerolinea) {
                return true;
            }
        }
        return false;
    }

    /** Comprueba si existe el cliente indicado en la base de datos. */
    private static boolean listarIdsClientes(int idCliente) throws SQLException {
        Dao dao = new DaoImplementacion();
        List<Integer> ids = dao.listarIdsClientes();
        if (ids.isEmpty()) {
            return false;
        }
        for (int id : ids) {
            if (id == idCliente) {
                return true;
            }
        }
        return false;
    }

    /** Muestra los vuelos cuya fecha de salida es posterior a la fecha actual. */
    private static void consultarVuelosFuturos(File fichVuelo) {
        if (!fichVuelo.exists()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }

        ObjectInputStream ois = null;
        boolean hayFuturos = false;
        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            while (true) {
                Vuelo v = (Vuelo) ois.readObject();
                if (v.getFechaSalida().isAfter(LocalDate.now())) {
                    System.out.println(v);
                    hayFuturos = true;
                }
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!hayFuturos) {
            System.out.println("No hay vuelos futuros.");
        }
    }

    /** Solicita un cliente y muestra los vuelos que tiene reservados. */
    private static void consultarVuelosDeUsuario() throws Exception {
        int idCliente = Util.leerInt("Introduce el id del cliente: ");
        Cliente cliente = new Cliente(idCliente, "", "", "", "");
        DaoImplementacion dao = new DaoImplementacion();
        dao.consultarvuelos(cliente);
    }

    /** Muestra las reservas del cliente cuya fecha de salida ya ha pasado. */
    private static void historialCliente() {
        int idCliente = Util.leerInt("Introduce el id del cliente: ");
        DaoImplementacion dao = new DaoImplementacion();

        try {
            List<Vuelo> historial = dao.verHistorialVuelos(idCliente);
            if (historial.isEmpty()) {
                System.out.println("El cliente " + idCliente + " no tiene vuelos pasados en su historial. Consulte la opción 6 para ver sus reservas.");
            } else {
                System.out.println("Historial de vuelos del cliente " + idCliente + ":");
                for (Vuelo v : historial) {
                    System.out.println(v);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
        }
    }

    /** Busca y devuelve el vuelo con el ID indicado, o {@code null} si no existe. */
    private static Vuelo obtenerVuelo(File fichVuelo, int id) {
        ObjectInputStream ois = null;
        Vuelo vue;
        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            while (true) {
                vue = (Vuelo) ois.readObject();
                if (vue.getId() == id) {
                    return vue;
                }
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /** Imprime todos los vuelos almacenados y el número total encontrado. */
    private static void listarVuelo(File fichVuelo) {
        if (!fichVuelo.exists()) {
            System.out.println("No hay vuelos registrados todavía.");
            return;
        }

        ObjectInputStream ois = null;
        int contador = 0;
        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            System.out.println("\n===== LISTADO DE VUELOS =====");
            while (true) {
                Vuelo vue = (Vuelo) ois.readObject();
                System.out.println(vue);
                contador++;
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (contador == 0) {
            System.out.println("No hay vuelos registrados todavía.");
        } else {
            System.out.println("Total de vuelos: " + contador);
        }
    }

    /** Reescribe el fichero sustituyendo el vuelo con el mismo ID. */
    private static void actualizarVueloEnFichero(File fichVuelo, Vuelo vueloActualizado) {
        ArrayList<Vuelo> vuelos = new ArrayList<>();
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            while (true) {
                Vuelo v = (Vuelo) ois.readObject();
                if (v.getId() == vueloActualizado.getId()) {
                    vuelos.add(vueloActualizado);
                } else {
                    vuelos.add(v);
                }
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichVuelo, false));
            for (Vuelo v : vuelos) {
                oos.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
