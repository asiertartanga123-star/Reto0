/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Asier.Prieto
 */
public class Aerolinea {

    private int id_A;
    private String nombre_A;
    private String pais;
    private String codigoIATA;

    public Aerolinea(int id_A, String nombre_A, String pais, String codigoIATA) {
        this.id_A = id_A;
        this.nombre_A = nombre_A;
        this.pais = pais;
        this.codigoIATA = codigoIATA;
    }

    public int getId_A() {
        return id_A;
    }

    public void setId_A(int id_A) {
        this.id_A = id_A;
    }

    public String getNombre_A() {
        return nombre_A;
    }

    public void setNombre_A(String nombre_A) {
        this.nombre_A = nombre_A;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoIATA() {
        return codigoIATA;
    }

    public void setCodigoIATA(String codigoIATA) {
        this.codigoIATA = codigoIATA;
    }

    @Override
    public String toString() {
        return "Aerolinea{" + "id_A=" + id_A + ", nombre_A=" + nombre_A + ", pais=" + pais + ", codigoIATA=" + codigoIATA + '}';
    }

}
