package Modelo;

import java.io.Serializable;

/** Representa la relación entre un cliente y un vuelo reservado. */
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_C;
    private int id_V;

    /** Crea una reserva para el cliente y el vuelo indicados. */
    public Reserva(int id_C, int id_V) {
        this.id_C = id_C;
        this.id_V = id_V;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public int getId_V() {
        return id_V;
    }

    public void setId_V(int id_V) {
        this.id_V = id_V;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id_C=" + id_C + ", id_V=" + id_V + '}';
    }
}

