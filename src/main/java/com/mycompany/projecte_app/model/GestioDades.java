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
    
        public ObservableList<Taula> llistaTaules() { //TERCIARY CONTROLLER - Guardar les taules del restaurant desde la BD a una ObservableList
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
        
        public ObservableList<Producte> llistaProductes() { //TERCIARY CONTROLLER - Guardar els productes del restaurant desde la BD a una ObservableList
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
        
        
        
        private static Cambrer CambrerActual = new Cambrer(); //Declarar una variable buida de la classe Cambrer
    
     public boolean consultarCambrer(String Nom, String Contrasenya) { //PRIMARY CONTROLLER - Comprovar si l'usuari existeix a la BD
         boolean ok = false; //Boolea que comprovarà si les dades passades als TextFields coincideixen amb dades de la BD
        String sql = "SELECT Nom, Contrasenya FROM Cambrer where Nom=? and Contrasenya=?"; //Consulta SQL on els "?" representen les variables del codi JAVA
        Connection connection = new Connexio_BD().connecta(); // Connexió amb la base de dades
        try {
            PreparedStatement ordre = connection.prepareStatement(sql);
            ordre.setString(1, Nom);
            ordre.setString(2, Contrasenya);
            ResultSet resultSet = ordre.executeQuery(); //Comprova si les dades introduides i les de la BD coincideixen
            if (resultSet.next()) { //Si coincideixen fes que el boleà sigui true
                ok = true;
                CambrerActual.setNom(Nom);
                CambrerActual.setContrasenya(Contrasenya);
                System.out.println("CAMBRER CONSULTAT: " + CambrerActual.getNom()); // Añade esta línea para verificar
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
     
     public boolean afegeixCambrer(String Nom ,String Contrasenya) throws SQLException, FileNotFoundException, IOException { //SECONDARY CONTROLLER - Afegir un nou usuari a la BD
        boolean ok = false;
        Connection connection = new Connexio_BD().connecta();
        String sql = "INSERT INTO Cambrer (Nom, Contrasenya) VALUES (?,?)";
        PreparedStatement ordre = connection.prepareStatement(sql);
        try {
            ordre.setString(1, Nom);
            ordre.setString(2, Contrasenya);
            ordre.execute();
            ok = true;
            CambrerActual.setNom(Nom);
            CambrerActual.setContrasenya(Contrasenya);

        } catch (SQLException throwables) {
            System.out.println("Error:" + throwables.getMessage());
        }

        return ok;
    }
    
    public boolean actualizarNumClients(Taula taula, int numClients) { //TERCIARY CONTROLLER --> Actualitzar la taula Taula de la base de dades per actualitzar el nombre de clients actual
        boolean ok1 = false;
        
        try (Connection connection = new Connexio_BD().connecta()) {
            String sql = "UPDATE Taula SET Num_Clients = ? WHERE Nom_Taula = ?";
            PreparedStatement ordre = connection.prepareStatement(sql);
                ordre.setInt(1, numClients);
                ordre.setString(2, taula.getNom_Taula());
                ordre.execute();
                
                ok1 = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ok1;
    }
    
    public boolean afegirComanda(Taula taula, Producte producte, int quantitat) { //TERCIARY CONTROLLER --> Afegir una nova comanda a la taula Comanda de la BD
        boolean ok2 = false;
        
        try (Connection conn = new Connexio_BD().connecta()) {
            String sql = "INSERT INTO Comanda (Cambrer, Taula, Producte, Quantitat) VALUES (?, ?, ?, ?)";
            PreparedStatement ordre = conn.prepareStatement(sql);
            ordre.setString(1, CambrerActual.getNom());
            System.out.println("CAMBRER AMB EL NOM ACTUAL: " + CambrerActual.getNom());
            ordre.setString(2, taula.getNom_Taula());
            ordre.setString(3, producte.getNom());
            ordre.setInt(4, quantitat);
            ordre.execute();
            
            ok2 = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ok2;
    }
     
}
