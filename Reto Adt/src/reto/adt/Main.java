/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Util.Util;

/**
 *
 * @author Asier.Prieto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

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
                // registrarVuelo();
                break;
            case 4:
                // reservarVuelo();
                break;
            case 5:
                // consultarVuelosFuturos();
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

    private static void registrarCliente() {
        int id = Util.leerInt("Introduce el id del cliente");
        String nombre = Util.introducirCadena("Introduce el nombre: ");
        
    }
    }
    

