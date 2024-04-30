/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alumne
 */
public class GestioDades {
    
    private ObservableList <Taula> llistaTaules;
    private ObservableList <Producte> llistaProductes;
    
        public static ObservableList<Taula> llistaTaules() { //TERCIARY CONTROLLER - Guardar les taules del restaurant desde la BD a una ObservableList
        ObservableList<Taula> llistaTaules = FXCollections.observableArrayList();
        String sql = "SELECT Nom_Taula, Num_Clients, Cambrer_associat FROM Taula";
        //String sql="select nom from usuaris";
        Connection connection = new Connexio_BD().connecta();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                llistaTaules.add(
                        new Taula(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getString(3)
                        )
                );
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return llistaTaules;
    }
        
        public static ObservableList<Producte> llistaProductes() { //TERCIARY CONTROLLER - Guardar els productes del restaurant desde la BD a una ObservableList
        ObservableList<Producte> llistaProductes = FXCollections.observableArrayList();
        String sql = "SELECT Nom, Preu FROM Producte";
        //String sql="select nom from usuaris";
        Connection connection = new Connexio_BD().connecta();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                llistaProductes.add(
                        new Producte(
                                resultSet.getString(1),
                                resultSet.getDouble(2)
                        )
                );
            }

            connection.close();

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }
        return llistaProductes;
    }
        
        
        //Metode del botó afegir --> Vincular la taula amb el producte i la quantitat per passar-ho dins de la taula Comanda de la BD
        //
        
        
        
        
    
     public boolean consultarCambrer(String Nom, String Contrasenya) { //PRIMARY CONTROLLER - Comprovar si l'usuari existeix a la BD
         boolean ok = false; //Boolea que comprovarà si les dades passades als TextFields coincideixen amb dades de la BD
        Cambrer cambrer = null;
        String sql = "SELECT Nom, Contrasenya FROM Cambrer where Nom=? and Contrasenya=?"; //Consulta SQL on els "?" representen les variables del codi JAVA
        Connection connection = new Connexio_BD().connecta(); // Connexió amb la base de dades
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, Nom);
            ordre.setString(2, Contrasenya);
            ResultSet resultSet = ordre.executeQuery(); //Comprova si les dades introduides i les de la BD coincideixen
            if (resultSet.next()) { //Si coincideixen fes que el boleà sigui true
                ok = true;
            }
        } catch (SQLException e) {
            System.out.println("Error SQL:" + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
        return ok; //Retorna el valor del booleà
    }
     
     public static boolean afegeixCambrer(Cambrer cambrer) throws SQLException, FileNotFoundException, IOException { //SECONDARY CONTROLLER - Afegir un nou usuari a la BD
        boolean ok = false;
        Connection connection = new Connexio_BD().connecta();
        String sql = "INSERT INTO Cambrer (Nom, Contrasenya) VALUES (?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setString(1, cambrer.getNom());
            ordre.setString(2, cambrer.getContrasenya());
            ordre.execute();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
}
