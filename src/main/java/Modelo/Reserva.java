/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Asier.Prieto
 */
public class Reserva {
    private int id_C;
    private int id_V;

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
