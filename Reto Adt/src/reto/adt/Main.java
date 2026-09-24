/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Modelo.Clase;
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
import java.time.LocalDate;

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

    private static void ejecutar(int opcion, File fichVuelo) throws Exception {
        switch (opcion) {
            //case 1: registrarAerolinea(); break;
            //case 2: registrarCliente(); break;
            case 3:
                registrarVuelo(fichVuelo);
                break;
            case 4:
                reservarVuelo();
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

    private static void reservarVuelo() {
        
        
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
