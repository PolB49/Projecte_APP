/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;

/**
 *
 * @author alumne
 */
public class Producte {
   
    private int ID_P;
    private String Nom;
    private double Preu;

    public Producte(int ID_P, String Nom, double Preu) {
        this.ID_P = ID_P;
        this.Nom = Nom;
        this.Preu = Preu;
    }

    @Override
    public String toString() {
        return Nom + " - " + Preu;
    }
    
    
}
