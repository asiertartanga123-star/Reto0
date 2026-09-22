/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto.adt;

import Controlador.DaoImplementacionAsier;
import Modelo.Aerolinea;
import Util.Util;

/**
 *
 * @author Asier.Prieto
 */
public class RetoAdt {

    private static Aerolinea aerolinea;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();

            opcion = Util.leerInt("Elige una opción: ");
            try {
                ejecutar(opcion);
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

    private static void ejecutar(int opcion) throws Exception {
        switch (opcion) {
            case 1: registrarAerolinea(); break;
            case 2: registrarCliente(); break;
            case 3: registrarVuelo(); break;
            case 4: reservarVuelo(); break;
            case 5: consultarVuelosFuturos(); break;
            case 6: consultarVuelosDeUsuario(); break;
            case 7: historialCliente(); break;
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
        DaoImplementacionAsier.registrarAerolinea(aerolinea);
        System.out.println("Aerolínea registrada correctamente.");
    }
}
