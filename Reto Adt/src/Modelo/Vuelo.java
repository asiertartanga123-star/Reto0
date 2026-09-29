/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Asier.Prieto
 */
public class Vuelo implements Serializable {
    
    private int id;
    private int id_C;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private int num_plazas;
    private Clase clase;

    public Vuelo(int id, int id_C, String origen, String destino, LocalDate fechaSalida, int num_plazas, Clase clase) {
        this.id = id;
        this.id_C = id_C;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getNum_plazas() {
        return num_plazas;
    }

    public void setNum_plazas(int num_plazas) {
        this.num_plazas = num_plazas;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    @Override
    public String toString() {
        return "Vuelo{" + "id=" + id + ", id_C=" + id_C + ", origen=" + origen + ", destino=" + destino + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas + ", clase=" + clase + '}';
    }
    
    
}
