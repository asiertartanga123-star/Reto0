<<<<<<< HEAD
package reto.adt;

import Controlador.DaoImplementacionEkain;
import Exceptions.IdDuplicadoException;
import Exceptions.IdInvalidoException;
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
import Exceptions.TelefonoInvalidoException;
public class Main {

    public static void main(String[] args) {
        File fichVuelo = new File("vuelos.dat");

        int opcion;

        do {
            System.out.println("===== MENÚ VOLARE =====");
            System.out.println("1. Registrar aerolínea");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Registrar vuelo");
            System.out.println("4. Reservar un vuelo");
            System.out.println("5. Consultar vuelos futuros");
            System.out.println("6. Consultar vuelos de un cliente");
            System.out.println("7. Ver historial de vuelos de un cliente");
            System.out.println("0. Salir");

            opcion = Util.leerInt(0, 7, "Elige una opción: ");

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
                    // reservarVuelo();
                    break;
                case 5:
                    consultarVuelosFuturos();
                    break;
                case 6:
                    // consultarVuelosDeCliente();
                    break;
                case 7:
                    // historialVuelosCliente();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    private static int pedirIdValido(DaoImplementacionEkain dao) {
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
    
        private static String pedirTelefonoValido() {
        String tlf = "";
        boolean tlfOk = false;

        do {
            try {
                tlf = Util.introducirCadena("Introduce el numero de telefono: ");

                if (!tlf.matches("\\d{9}")) {
                    throw new TelefonoInvalidoException("El teléfono debe tener 9 dígitos numéricos.");
                }

                tlfOk = true;

            } catch (TelefonoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (!tlfOk);

        return tlf;
    }

    private static void registrarAerolinea() {
        DaoImplementacionEkain dao = new DaoImplementacionEkain();

        int id = -1;
        boolean idOk = false;
        do {
            try {
                id = Util.leerInt("Introduce el id de la aerolínea: ");
                if (id < 0) {
                    System.out.println("El id no puede ser negativo.");
                } else if (dao.obtenerAerolinea(id) != null) {
                    System.out.println("Ya existe una aerolínea con ese id.");
                } else {
                    idOk = true;
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar el id: " + e.getMessage());
            }
        } while (!idOk);

        String nombre = Util.introducirCadena("Introduce el nombre de la aerolínea: ");
        String pais = Util.introducirCadena("Introduce el país: ");
        String codigoIATA = Util.introducirCadena("Introduce el código IATA: ");

        Aerolinea aerolinea = new Aerolinea(id, nombre, pais, codigoIATA);

        try {
            dao.registrarAerolinea(aerolinea);
            System.out.println("Aerolínea registrada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar la aerolínea: " + e.getMessage());
        }
    }

    private static void registrarCliente() {
        DaoImplementacionEkain dao = new DaoImplementacionEkain();

        int id = pedirIdValido(dao);
        String nombre = Util.introducirCadena("Introduce el nombre: ");
        String email = Util.validarEmail("Introduce el email: ");
        String tlf = pedirTelefonoValido();

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

    private static void consultarVuelosFuturos() {
        File fichVuelo = new File("vuelos.dat");

        if (!fichVuelo.exists()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }

        DaoImplementacionEkain dao = new DaoImplementacionEkain();
        ObjectInputStream ois = null;
        boolean hayFuturos = false;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            while (true) {
                Vuelo v = (Vuelo) ois.readObject();
                if (v.getFechaSalida().isAfter(LocalDate.now())) {
                    System.out.println(v);
                    try {
                        Aerolinea aero = dao.obtenerAerolinea(v.getId_A());
                        if (aero != null) {
                            System.out.println("   Aerolínea: " + aero.getNombre_A()
                                    + " (" + aero.getCodigoIATA() + ") - " + aero.getPais());
                        } else {
                            System.out.println("   Aerolínea: no encontrada");
                        }
                    } catch (SQLException e) {
                        System.out.println("   Error al obtener la aerolínea: " + e.getMessage());
                    }
                    hayFuturos = true;
                }
            }
        } catch (EOFException e) {
            // fin del fichero, no es un error
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

    private static void registrarVuelo(File fichVuelo) {

        int id;

        ObjectOutputStream oos = null;
        DaoImplementacionEkain dao = new DaoImplementacionEkain();

        Vuelo v;
        int mas;
        try {
            if (fichVuelo.exists()) {
                oos = new AñadirObjetoSinCabecera(new FileOutputStream(fichVuelo, true));
            } else {
                oos = new ObjectOutputStream(new FileOutputStream(fichVuelo));
            }
            do {

                id = Util.leerInt("Introduce el id del vuelo");

                v = obtenerVuelo(fichVuelo, id);
                if (v != null) {
                    System.out.println("Ya existe un vuelo con ese id");
                } else {

                    System.out.println("AÑADIENDO VUELO NUEVO");
                    String origen = Util.introducirCadena("El origen del vuelo");
                    String destino = Util.introducirCadena("El destino del vuelo");
                    LocalDate fechSalida = Util.pidoFechaDMA("La fecha de salida del vuelo");
                    int num_plaza = Util.leerInt("Introduce el número de plazas del vuelo");
                    Clase clas = null;
                    while (clas == null) {
                        for (Clase c : Clase.values()) {
                            System.out.print(c + " - ");
                        }
                        String post = Util.introducirCadena("Introduce la clase que tendrá el vuelo");
                        for (Clase c : Clase.values()) {
                            if (c.name().equals(post)) {
                                clas = c;
                                break;
                            }
                        }
                    }

                    int idAerolinea = -1;
                    boolean aeroOk = false;
                    do {
                        try {
                            idAerolinea = Util.leerInt("Introduce el id de la aerolínea");
                            if (dao.obtenerAerolinea(idAerolinea) != null) {
                                aeroOk = true;
                            } else {
                                System.out.println("No existe ninguna aerolínea con ese id.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al comprobar la aerolínea: " + e.getMessage());
                        }
                    } while (!aeroOk);

                    v = new Vuelo(id, origen, destino, fechSalida, num_plaza, clas, idAerolinea);
                    oos.writeObject(v);
                }

                mas = Util.leerInt("Quieres añadir un vuelo mas? (1=Si/2=No)");

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
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Controlador.DaoImplementacion;
import Controlador.DaoRicardo;
import Exception.IdDuplicadoException;
import Exception.IdInvalidoException;
import Modelo.Aerolinea;
import Modelo.Clase;
import Modelo.Cliente;
import Modelo.Reserva;
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
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asier.Prieto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    //Registrar vuelo, reservar vuelo.
    private int opc;

    public static void main(String[] args) {
        // TODO code application logic here

        File fichVuelo = new File("vuelos.dat");

        int opc = -1;
        do {
            mostrarMenu();
            opc = Util.leerInt("Elige una opcion");
            try {
                ejecutar(opc, fichVuelo);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opc != 0);

    }

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
            case 8:
                listarVuelo(fichVuelo);
            //case 5: consultarVuelosFuturos(); break;
            //case 6: consultarVuelosDeUsuario(); break;
            //case 7: historialCliente(); break;
            case 0:
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

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
                //verifica que exista ese vuelo
                if (v != null) {
                    System.out.println("Ya existe un vuelo con ese id");
                } else {

                    System.out.println("AÑADIENDO VUELO NUEVO");
                    String origen = Util.introducirCadena("El origen del vuelo");
                    String destino = Util.introducirCadena("El destino del vuelo");
                    LocalDate fechSalida = Util.pidoFechaDMA("La fecha de salida del vuelo");
                    int num_plaza = Util.leerInt("Introduce el número de plazas del vuelo");
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

                        int id_A = Util.leerInt("Introduce el id de la aerolinea");
                        //verifica que exista el id_A
                        if (!listarIdsAerolinea(id_A)) {
                            v = new Vuelo(id, origen, destino, fechSalida, num_plaza, clas, id_A);
                        } else {
                            System.out.println("No existe una aerolinea con ese id");
                            return;
                        }

                    }
                    
                    oos.writeObject(v);

                }

                mas = Util.leerInt("Quieres añadir un vuelo mas? (1=Si/2=No)");

            } while (mas == 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void reservarVuelo(File fichVuelo) throws SQLException {

        Vuelo v;
        int idC = Util.leerInt("Introduce el id del cliente");

        if (listarIdsClientes(idC)) {
            int idV = Util.leerInt("Introduce el id del vuelo");

            v = obtenerVuelo(fichVuelo, idV);
            if (v == null) {
                System.out.println("No existe ese vuelo con ese id");

            } else {

                //pasamos los valores a v
                v = obtenerVuelo(fichVuelo, idV);
                //añadimos el idC a la memoria
                v.getIdReservados().add(idC);
                //Sobreescribo el fichero añadiendo el id reservado
                actualizarVueloEnFichero(fichVuelo, v);


                System.out.println("Reserva añadida correctamente");

            }

        } else {
            System.out.println("No hay un cliente con ese id");
        }
    }

    private static boolean listarIdsAerolinea(int ides) throws SQLException {
        DaoRicardo dao = new DaoImplementacion();
        List<Integer> ids = dao.listarIdsAerolinea();
        if (ids.isEmpty()) {
            System.out.println("No existe ese id de vuelo.");
            return false;
        } else {
            /*System.out.println("IDs de clientes:");
        for (int id : ids) {
            System.out.println(id);
        }*/
            for (int id : ids) {
                if (id == ides) {
                    return true;
                }
            }

        }
        return false;
    }

    private static boolean listarIdsClientes(int ides) throws SQLException {
        DaoRicardo dao = new DaoImplementacion();
        List<Integer> ids = dao.listarIdsClientes();
        if (ids.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return false;
        } else {
            /*System.out.println("IDs de clientes:");
        for (int id : ids) {
            System.out.println(id);
        }*/
            for (int id : ids) {
                if (id == ides) {
                    return true;
                }
            }

        }
        return false;

    }

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

    private static void registrarCliente() {
        DaoImplementacion dao = new DaoImplementacion();

        int id = pedirIdValido(dao);
        String nombre = Util.introducirCadena("Introduce el nombre: ");
        String email = Util.validarEmail("Introduce el email: ");
        String tlf = Util.introducirCadena("Introduce el numero de telefono: ");

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
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void listarVuelo(File fichVuelo) {

        if (!fichVuelo.exists()) {
            System.out.println("No hay vuelos registrados todavía.");
            return;
        }

        ObjectInputStream ois = null;
        Vuelo vue;
        int contador = 0;
        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            System.out.println("\n===== LISTADO DE VUELOS =====");
            while (true) {
                vue = (Vuelo) ois.readObject();
                System.out.println(vue);
                contador++;
            }
        } catch (EOFException e) {
            // Fin del fichero, no es un error real
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

    private static void actualizarVueloEnFichero(File fichVuelo, Vuelo vueloActualizado) {
        ArrayList<Vuelo> vuelos = new ArrayList<>();
        ObjectInputStream ois = null;

        // 1. Leer todos los vuelos
        try {
            ois = new ObjectInputStream(new FileInputStream(fichVuelo));
            while (true) {
                Vuelo v = (Vuelo) ois.readObject();
                if (v.getId() == vueloActualizado.getId()) {
                    vuelos.add(vueloActualizado);   // sustituimos el modificado
                } else {
                    vuelos.add(v);
                }
            }
        } catch (EOFException e) {
            // fin del fichero, normal
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 2. Reescribir todo el fichero (sobreecribiendo)
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichVuelo, false)); // false = sobreescribe
            for (Vuelo v : vuelos) {
                oos.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
>>>>>>> Ricardo
