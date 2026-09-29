/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Asier.Prieto
 */
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private int num_plazas;
    private Clase clase;
    private int id_A;
    private ArrayList<Integer> idReservados;

    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida,
            int num_plazas, Clase clase, int id_A,
            ArrayList<Integer> idReservados) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
        this.id_A = id_A;
        this.idReservados = new ArrayList<>();
    }

    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida, int num_plazas, Clase clase, int id_A) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
        this.id_A = id_A;
    }

    public int getId_A() {
        return id_A;
    }

    public void setId_A(int id_A) {
        this.id_A = id_A;
    }

    public ArrayList<Integer> getIdReservados() {
        if (idReservados == null) {
            idReservados = new ArrayList<>();
        }
        return idReservados;
    }

    public void setIdReservados(ArrayList<Integer> idReservados) {
        this.idReservados = idReservados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /*
    public void setDatos(int id,Clase c) {
		this.id = id;
		this.origen = Util.introducirCadena("Nombre: ");
		this.destino = Util.introducirCadena("Apellido: ");
		this.fechaSalida = Util.pidoFechaDMA("Fecha de salida: ");
		this.num_plazas = Util.leerInt("Número de plazas: ");
		this.clase = c;
	}
     */
    @Override
    public String toString() {
        return "Vuelo{" + "id=" + id + ", origen=" + origen + ", destino=" + destino + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas + ", clase=" + clase + ", id_A=" + id_A + ", idReservados=" + idReservados + '}';
    }
}
