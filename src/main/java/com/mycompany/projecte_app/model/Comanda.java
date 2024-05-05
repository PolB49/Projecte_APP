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
    private double preuProducte;

    public Comanda(Taula TaulaSeleccionada, Producte ProducteSeleccionat, int quantitatProducte) {
        this.TaulaSeleccionada = TaulaSeleccionada;
        this.ProducteSeleccionat = ProducteSeleccionat;
        this.quantitatProducte = quantitatProducte;
        this.preuProducte = ProducteSeleccionat.getPreu();
    }

    @Override
public String toString() {
    // Obté el nom del producte, el preu unitari i la quantitat de la comanda
    String nomProducte = ProducteSeleccionat.getNom();
    double preuUnitari = ProducteSeleccionat.getPreu();
        System.out.println("PREU UNITARI: " + ProducteSeleccionat.getNom() + "-- " + preuProducte);
    int quantitat = quantitatProducte;

    // Construeix una cadena amb el nom del producte, el preu unitari i la quantitat
    String representacioComanda = String.format("%s - Preu unitari: %.2f - Quantitat: %d", nomProducte, preuProducte, quantitat);

    return representacioComanda;
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
