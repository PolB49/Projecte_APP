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
   
    private String Nom;
    private double Preu;

    public Producte(String Nom, double Preu) {
        this.Nom = Nom;
        this.Preu = Preu;
    }

    @Override
    public String toString() {
        return Nom;
    }

    public String getNom() {
        return Nom;
    }

    public double getPreu() {
        return Preu;
    }
    
    
    
    
}
