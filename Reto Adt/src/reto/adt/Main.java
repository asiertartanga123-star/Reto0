/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Controlador.DaoImplementacionEkain;
import Exceptions.IdDuplicadoException;
import Exceptions.IdInvalidoException;
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

/**
 *
 * @author Asier.Prieto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
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
                // registrarAerolinea();
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

    private static void registrarCliente() {
         DaoImplementacionEkain dao = new DaoImplementacionEkain();
         
        
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

    private static void consultarVuelosFuturos() {
    File fichVuelo = new File("vuelos.dat");

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
        // fin del fichero, no es un error
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

    if (!hayFuturos) {
        System.out.println("No hay vuelos futuros.");
    }
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
                    v = new Vuelo(id, origen, destino, fechSalida, num_plaza, clas);
                    //v.setDatos(id,clas);
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
    }
    

