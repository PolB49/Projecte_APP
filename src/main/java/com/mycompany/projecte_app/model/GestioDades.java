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
        String sql = "SELECT ID_T, Numero_Taula, Num_Clients, ID_C FROM Taula";
        //String sql="select nom from usuaris";
        Connection connection = new Connexio_BD().connecta();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                llistaTaules.add(
                        new Taula(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getInt(4)
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
        String sql = "SELECT ID_P, Nom, Preu FROM Producte";
        //String sql="select nom from usuaris";
        Connection connection = new Connexio_BD().connecta();
        try {
            Statement ordre = connection.createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                llistaProductes.add(
                        new Producte(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getDouble(3)
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
        
        
        
        
    
     public boolean consultarUsuari(String Nom, String Contrasenya) { //PRIMARY CONTROLLER - Comprovar si l'usuari existeix a la BD
         boolean ok = false; //Boolea que comprovarà si les dades passades als TextFields coincideixen amb dades de la BD
        Cambrer cambrer = null;
        String sql = "select Contrasenya, Nom from Cambrer where Contrasenya=? and Nom=?"; //Consulta SQL on els "?" representen les variables del codi JAVA
        Connection connection = new Connexio_BD().connecta(); // Connexió amb la base de dades
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, Contrasenya);
            ordre.setString(2, Nom);
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
     
     public static boolean afegeixUsuari(Cambrer cambrer) throws SQLException, FileNotFoundException, IOException { //SECONDARY CONTROLLER - Afegir un nou usuari a la BD
        boolean ok = false;
        Connection connection = new Connexio_BD().connecta();
        String sql = "INSERT INTO Cambrer (Contrasenya, Nom) VALUES (?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setString(1, cambrer.getContrasenya());
            ordre.setString(2, cambrer.getNom());
            ordre.execute();
            ok = true;

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
}
