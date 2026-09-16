/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Asier.Prieto
 */
public class Cliente {
    
    private int id_C;
    private String nombre_V;
    private String mail;
    private String tlf;
    private String ruta;

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public String getNombre_V() {
        return nombre_V;
    }

    public void setNombre_V (String nombre_V) {
        this.nombre_V = nombre_V;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Cliente(int id_C, String nombre_V, String mail, String tlf, String ruta) {
        this.id_C = id_C;
        this.nombre_V = nombre_V;
        this.mail = mail;
        this.tlf = tlf;
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id_C + ", nombre=" + nombre_V + ", mail=" + mail + ", tlf=" + tlf + ", ruta=" + ruta + '}';
    }
    
    
    
}
