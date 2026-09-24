/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Vuelo;
import java.util.List;

/**
 *
 * @author Asier.Prieto
 */
public interface DaoEkain {
    // Cliente(BD)
    public boolean RegistrarCLiente(Cliente clien);
    boolean existeCliente(int id);

    // Vuelo (fichero)
    void registrarVuelo(Vuelo vuelo);
    List<Vuelo> leerTodosVuelos();
    List<Vuelo> consultarVuelosFuturos();
    
}
