package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/** Vuelo serializable, con sus datos y los IDs de clientes que lo han reservado. */
public class Vuelo implements Serializable {

    // Mantiene la compatibilidad con los vuelos guardados por versiones anteriores.
    private static final long serialVersionUID = -1381670417836086862L;

    private int id;
    private int id_C;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private int num_plazas;
    private Clase clase;
    private int id_A;
    private ArrayList<Integer> idReservados;

    /** Crea un vuelo sin reservas ni cliente asociado directamente. */
    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida,
            int num_plazas, Clase clase, int id_A) {
        this(id, 0, origen, destino, fechaSalida, num_plazas, clase, id_A, new ArrayList<>());
    }

    /** Crea un vuelo restaurando también sus reservas y el cliente asociado. */
    public Vuelo(int id, int id_C, String origen, String destino, LocalDate fechaSalida,
            int num_plazas, Clase clase, int id_A, ArrayList<Integer> idReservados) {
        this.id = id;
        this.id_C = id_C;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
        this.id_A = id_A;
        this.idReservados = idReservados == null ? new ArrayList<>() : idReservados;
    }

    /** Devuelve las reservas e inicializa la lista si el flujo antiguo era nulo. */
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

    public int getId_A() {
        return id_A;
    }

    public void setId_A(int id_A) {
        this.id_A = id_A;
    }

    @Override
    public String toString() {
        return "Vuelo{" + "id=" + id + ", origen=" + origen + ", destino=" + destino
                + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas
                + ", clase=" + clase + ", id_A=" + id_A + ", idReservados=" + idReservados + '}';
    }
}

