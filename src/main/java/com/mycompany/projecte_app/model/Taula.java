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
    
    private int ID_T;
    private String Numero_Taula;
    private int Num_Clients;
    private int ID_C;

    public Taula(int ID_T, String Numero_Taula, int Num_Clients, int ID_C) {
        this.ID_T = ID_T;
        this.Numero_Taula = Numero_Taula;
        this.Num_Clients = Num_Clients;
        this.ID_C = ID_C;
    }

    @Override
    public String toString() {
        return Numero_Taula;
    }
    
    
    
    
    
}
