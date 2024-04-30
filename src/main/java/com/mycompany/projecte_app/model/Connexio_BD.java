/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author alumne
 */
public class Connexio_BD {
    private final String URL = "jdbc:mysql://localhost/Projecte_APP";//nom bd
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "root";
    private final String PASSWD = "";   
   

    public Connection connecta() {
        Connection connexio = null;
        try {
            //Carreguem el driver          
            Class.forName(DRIVER); 
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }    
        return connexio;
    }
}
