/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Util.AñadirObjetoSinCabecera;
import Controlador.DaoImplementacionAsier;
import Modelo.Aerolinea;
import Modelo.Clase;
import Modelo.Cliente;
import Modelo.Vuelo;
import Util.Util;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

/**
 *
 * @author Asier.Prieto
 */
public class RetoAdt {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         File fichVuelo = new File("vuelos.dat");

        int opcion;
        do {
            mostrarMenu();

            opcion = Util.leerInt("Elige una opción: ");
            try {
                ejecutar(opcion, fichVuelo);
            } catch (Exception e) {
                System.out.println("⚠ Error: " + e.getMessage());
            }
        } while (opcion != 0);
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
        System.out.println("0. Salir");
    }

    private static void ejecutar(int opcion,File fichVuelo) throws Exception {
        switch (opcion) {
            case 1: registrarAerolinea(); break;
            case 2: registrarCliente(); break;
            case 3: registrarVuelo(fichVuelo); break;
            case 4: reservarVuelo(); break;
            case 5: consultarVuelosFuturos(); break;
            case 6: consultarVuelosDeUsuario(); break;
            case 7: historialVuelos(); break;
            case 0: break;
            default: System.out.println("Opción no válida");
        }
    }   

   private static void registrarAerolinea() throws Exception {
    System.out.println("--- Registrar aerolínea ---");
    int id = Util.leerInt("Id: ");
    String nombre = Util.introducirCadena("Nombre: ");
    String pais = Util.introducirCadena("País: ");
    String iata = Util.introducirCadena("Código IATA: ");

    Aerolinea aerolinea = new Aerolinea(id, nombre, pais, iata);

    DaoImplementacionAsier dao = DaoImplementacionAsier.getInstance();
    dao.registrarAerolinea(aerolinea);

    System.out.println("Aerolínea registrada correctamente.");
}

    private static void registrarCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   private static void registrarVuelo(File fichVuelo) throws IOException {

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
                    int idCliente = Util.leerInt("Id del cliente: ");
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
                    v = new Vuelo(id, origen, destino, fechSalida, idCliente, clas);
                    v.setNum_plazas(num_plaza);
                    //v.setDatos(id,clas);
                    oos.writeObject(v);

                }

                mas = Util.leerInt("Quieres añadir un vuelo mas? (1=Si/2=No)");

            } while (mas == 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oos.close();
        }

    }

    private static void reservarVuelo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void consultarVuelosFuturos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void consultarVuelosDeUsuario() throws Exception {
        System.out.println("--- Consultar vuelos de un cliente ---");
        int idCliente = Util.leerInt("Id del cliente: ");

        Cliente cliente = new Cliente(idCliente, "", "", "", "");
        DaoImplementacionAsier dao = DaoImplementacionAsier.getInstance();
        dao.consultarvuelos(cliente);
    }
    private static void historialVuelos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
