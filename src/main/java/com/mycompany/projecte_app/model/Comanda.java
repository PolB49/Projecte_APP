/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;

/**
 *
 * @author polbv
 */
public class Comanda {
    private Taula TaulaSeleccionada;
    private Producte ProducteSeleccionat;
    private int quantitatProducte;

    public Comanda(Taula TaulaSeleccionada, Producte ProducteSeleccionat, int quantitatProducte) {
        this.TaulaSeleccionada = TaulaSeleccionada;
        this.ProducteSeleccionat = ProducteSeleccionat;
        this.quantitatProducte = quantitatProducte;
    }

    @Override
    public String toString() {
        return "Producte: " + ProducteSeleccionat + "  -  " + "Preu: " + ProducteSeleccionat.getPreu() + "  -  "  + "Quantitat: " + quantitatProducte;
    }

    public Taula getTaulaSeleccionada() {
        return TaulaSeleccionada;
    }

    public Producte getProducteSeleccionat() {
        return ProducteSeleccionat;
    }

    public int getQuantitatProducte() {
        return quantitatProducte;
    }

    public void setQuantitatProducte(int quantitatProducte) {
        this.quantitatProducte = quantitatProducte;
    }
    
    
    
    
}
