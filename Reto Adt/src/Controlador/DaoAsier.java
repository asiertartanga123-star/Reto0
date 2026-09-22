/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controlador;

import Modelo.Aerolinea;
import Modelo.Cliente;

/**
 *
 * @author Asier.Prieto
 */
public interface DaoAsier {
   
    void registrarAerolinea(Aerolinea aerolinea) throws Exception;
    void consultarvuelos(Cliente cliente) throws Exception;
}
