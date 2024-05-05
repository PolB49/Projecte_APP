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
    
    //METODES QUE S'EXECUTEN AL INICIALITZAR LA APP
    
    public void borrarComanda() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
        connection = new Connexio_BD().connecta();
        String sql = "DELETE FROM Comanda";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    //PRIMARY CONTROLLER
    
    public static Cambrer CambrerActual = new Cambrer(); //Declarar una variable buida de la classe Cambrer que guardara el cambrer que esta fent servir l'aplicació
    
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
    
       //SECNDARY CONTROLLER
    
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
     
      //TERCIARY CONTROLLER
     
    private ObservableList <Taula> llistaTaules;
    private ObservableList <Producte> llistaProductes;
    
        public ObservableList<Taula> llistaTaules() { //TERCIARY CONTROLLER - Guardar les taules del restaurant desde la BD a una ObservableList
        ObservableList<Taula> llistaTaules = FXCollections.observableArrayList();
        String sql = "SELECT Nom_Taula, Num_Clients, Cambrer_associat FROM Taula";
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
                System.out.println("PRODUCTE DE LA BASE DADES -- " + "Nom: " + resultSet.getString(1) + "Preu" + resultSet.getDouble(2));
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
    
        public boolean afegirComanda(Taula taula, Producte producte, int quantitat) { //TERCIARY CONTROLLER --> Afegir una nova comanda a la base de dades o actualitzar la anterior si la taula i el producte coincideixen
            boolean ok2 = false;
    
        try (Connection conn = new Connexio_BD().connecta()) {
            String sql = "SELECT * FROM Comanda WHERE Taula = ? AND Producte = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, taula.getNom_Taula());
            preparedStatement.setString(2, producte.getNom());
            ResultSet resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) { // Si ja existeix una comanda en aquella taula i producte
                int quantitatActual = resultSet.getInt("Quantitat");
                int quantitatTotal = quantitatActual + quantitat;
            
                sql = "UPDATE Comanda SET Cambrer = ?, Quantitat = ? WHERE Taula = ? AND Producte = ?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, CambrerActual.getNom());
                preparedStatement.setInt(2, quantitatTotal);
                preparedStatement.setString(3, taula.getNom_Taula());
                preparedStatement.setString(4, producte.getNom());
                preparedStatement.executeUpdate();
                
            } else { // Si no existeix una comanda amb aquesta taula i producte, crear-ne una nova
                sql = "INSERT INTO Comanda (Cambrer, Taula, Producte, Quantitat) VALUES (?, ?, ?, ?)";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, CambrerActual.getNom());
                preparedStatement.setString(2, taula.getNom_Taula());
                preparedStatement.setString(3, producte.getNom());
                preparedStatement.setInt(4, quantitat);
                preparedStatement.executeUpdate();
            }
        
            ok2 = true; // Marca la operación como exitosa
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return ok2;
        }

    
    public double ObtenirPreuTotalTaula(Taula taulaSeleccionada) { //TERCIARY CONTROLLER --> Obtenir la suma dels preus dels productes * la quantitat i donar el preu total de la taula
    double preuTotal = 0.0;
    
     try (Connection connection = new Connexio_BD().connecta()) {
                String sql = "SELECT SUM(p.Preu * c.Quantitat) AS Total " +
                                    "FROM Comanda c " +
                                    "JOIN Producte p ON c.Producte = p.Nom " +
                                    "WHERE c.Taula = ?";
                
                PreparedStatement ordre = connection.prepareStatement(sql);
                ordre = connection.prepareStatement(sql);
                ordre.setString(1, taulaSeleccionada.getNom_Taula());
                ResultSet resultSet = ordre.executeQuery();
                
        if (resultSet.next()) {
            preuTotal = resultSet.getDouble("Total");
        }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    return preuTotal;
}
    

    public ObservableList<Comanda> ObtenirComandesPerTaula(Taula taulaSeleccionada) {
    ObservableList<Comanda> comandas = FXCollections.observableArrayList();
    String sql = "SELECT c.*, p.Preu " +
                 "FROM Comanda c " +
                 "JOIN Producte p ON c.Producte = p.Nom " +
                 "WHERE c.Taula = ?";

    Connection connection = new Connexio_BD().connecta();
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, taulaSeleccionada.getNom_Taula());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Producte producte = new Producte(resultSet.getString("Producte"), resultSet.getDouble("Preu"));
            Comanda comanda = new Comanda(
                taulaSeleccionada,
                producte,
                resultSet.getInt("Quantitat")
            );
            comandas.add(comanda);
        }
        connection.close();
    } catch (SQLException throwables) {
        System.out.println("Error: " + throwables.getMessage());
    }
    return comandas;
}
    

}
