/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;

/**
 *
 * @author alumne
 */
public class Cambrer {
    
    private String Nom;
    private String Contrasenya;

    //Constructor temporal
    public Cambrer(String Nom, String Contrasenya) {
        this.Nom = Nom;
        this.Contrasenya = Contrasenya;
    }

    public Cambrer() {
    }

    public String getNom() {
        return this.Nom;
    }

    public String getContrasenya() {
        return Contrasenya;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public void setContrasenya(String Contrasenya) {
        this.Contrasenya = Contrasenya;
    }
    
    
    
    
}
