package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.GestioDades;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class TerciaryControll {
//TAULELL PRINCIPAL AFEGIR
    
    
    @FXML
    ComboBox ComboTaula;
    @FXML
    ComboBox ComboProducte;
    @FXML
    ListView TextCompte;
    
    @FXML
    private void Registrarse() throws IOException {
        App.setRoot("primary");
    }
    
    public void initialize() {
        ComboTaula.setItems(GestioDades.llistaTaules()); //Vincula el ComboBox de taules amb les Taules del restaurant de la BD
        ComboProducte.setItems(GestioDades.llistaProductes()); //Vincula el ComboBox de taules amb les Taules del restaurant de la BD
    }
}
