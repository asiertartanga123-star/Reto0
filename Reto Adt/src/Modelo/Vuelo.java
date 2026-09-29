package Modelo;

import java.io.Serializable;
import java.time.LocalDate;

<<<<<<< HEAD
/**
 *
 * @author Asier.Prieto
 */
public class Vuelo implements Serializable {
    
=======
public class Vuelo implements Serializable {

>>>>>>> Ekain
    private int id;
    private int id_C;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private int num_plazas;
    private Clase clase;
    private int id_A;
    public int getId_c;

<<<<<<< HEAD
    public Vuelo(int id, int id_C, String origen, String destino, LocalDate fechaSalida, int num_plazas, Clase clase) {
=======
    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida,
                 int num_plazas, Clase clase, int id_A) {
>>>>>>> Ekain
        this.id = id;
        this.id_C = id_C;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.num_plazas = num_plazas;
        this.clase = clase;
        this.id_A = id_A;
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
<<<<<<< HEAD
        return "Vuelo{" + "id=" + id + ", id_C=" + id_C + ", origen=" + origen + ", destino=" + destino + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas + ", clase=" + clase + '}';
=======
        return "Vuelo{" + "id=" + id + ", origen=" + origen + ", destino=" + destino
                + ", fechaSalida=" + fechaSalida + ", num_plazas=" + num_plazas
                + ", clase=" + clase + ", id_A=" + id_A + '}';
>>>>>>> Ekain
    }
}