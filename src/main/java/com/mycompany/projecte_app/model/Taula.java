/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;

/**
 *
 * @author alumne
 */
public class Taula {
    
    private String Nom_Taula;
    private int Num_Clients;
    private String Cambrer_associat;

    public Taula(String Nom_Taula, int Num_Clients, String Cambrer_associat) {
        this.Nom_Taula = Nom_Taula;
        this.Num_Clients = Num_Clients;
        this.Cambrer_associat = Cambrer_associat;
    }

    @Override
    public String toString() {
        return Nom_Taula;
    }

    public String getNom_Taula() {
        return Nom_Taula;
    }

    public int getNum_Clients() {
        return Num_Clients;
    }
    
    
    
}


