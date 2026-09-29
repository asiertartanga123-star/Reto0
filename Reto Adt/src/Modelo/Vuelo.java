package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

<<<<<<< HEAD
/**
 *
 * @author Asier.Prieto
 */
public class Vuelo implements Serializable {
<<<<<<< HEAD
    
=======
public class Vuelo implements Serializable {

>>>>>>> Ekain
=======

    private static final long serialVersionUID = 1L;

>>>>>>> Ricardo
    private int id;
    private int id_C;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private int num_plazas;
    private Clase clase;
    private int id_A;
<<<<<<< HEAD
    public int getId_c;

<<<<<<< HEAD
    public Vuelo(int id, int id_C, String origen, String destino, LocalDate fechaSalida, int num_plazas, Clase clase) {
=======
    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida,
                 int num_plazas, Clase clase, int id_A) {
>>>>>>> Ekain
=======
    private ArrayList<Integer> idReservados;

    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida,
            int num_plazas, Clase clase, int id_A,
            ArrayList<Integer> idReservados) {
>>>>>>> Ricardo
        this.id = id;
        this.id_C = id_C;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
        this.id_A = id_A;
<<<<<<< HEAD
=======
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
>>>>>>> Ricardo
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

<<<<<<< HEAD
    public int getId_A() {
        return id_A;
    }

    public void setId_A(int id_A) {
        this.id_A = id_A;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "Vuelo{" + "id=" + id + ", id_C=" + id_C + ", origen=" + origen + ", destino=" + destino + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas + ", clase=" + clase + '}';
=======
        return "Vuelo{" + "id=" + id + ", origen=" + origen + ", destino=" + destino
                + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas
                + ", clase=" + clase + ", id_A=" + id_A + '}';
>>>>>>> Ekain
    }
}
=======
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
>>>>>>> Ricardo
