package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.Cambrer;
import com.mycompany.projecte_app.model.Comanda;
import com.mycompany.projecte_app.model.GestioDades;
import com.mycompany.projecte_app.model.Producte;
import com.mycompany.projecte_app.model.Taula;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TerciaryControll {
//TAULELL PRINCIPAL AFEGIR
    
        GestioDades gestiodades = new GestioDades(); //MOLT IMPORTANT -- INSTANCIAR LA CLASSE
    
    
    @FXML
    ComboBox ComboTaula;
    @FXML
    ComboBox ComboProducte;
    @FXML
    TextField NumeroClients;
    @FXML
    TextField QuantitatProducte;
    @FXML
    TextField CambrerAssociat;
    @FXML
    ListView TextCompte;
    
    @FXML
    private void Registrarse() throws IOException {
        App.setRoot("primary");
    }
    
    public void initialize() {
        ComboTaula.setItems(gestiodades.llistaTaules()); //Vincula el ComboBox de taules amb les Taules del restaurant de la BD
        ComboProducte.setItems(gestiodades.llistaProductes()); //Vincula el ComboBox de taules amb les Taules del restaurant de la BD
    }
     
    public void BotoAfegir(){
        Taula TaulaSeleccionada = (Taula) ComboTaula.getValue(); //Aconseguir l'objecte Taula marcat en el combobox deTaules
        Producte ProducteSeleccionat = (Producte) ComboProducte.getValue(); //Aconseguir l'objecte Producte del combobox de Productes
        int NumClients = Integer.parseInt(NumeroClients.getText()); //Aconseguir el numero de clients del TextField
        int quantitatProducte = Integer.parseInt(QuantitatProducte.getText()); //Aconseguir la quantitat del producte seleccionat del combobox
        
        boolean ok1 = gestiodades.actualizarNumClients(TaulaSeleccionada, NumClients); //Actualitzar nombre de clients de la taula
        
        boolean ok2 = gestiodades.afegirComanda( TaulaSeleccionada, ProducteSeleccionat, quantitatProducte); //Crear una nova comanda
        
        if (ok1 == true && ok2 == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Comanda Enviada");
                alert.setHeaderText("Acceptar");
                Optional<ButtonType> result = alert.showAndWait();
                
                Comanda comanda = new Comanda (TaulaSeleccionada, ProducteSeleccionat, quantitatProducte); //Recuperala comanda enviada
                
                TextCompte.getItems().add(comanda.toString()); //Actualitzar ListView
            
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Dades");
                alert.setHeaderText("No s'ha pogut tramitar la comanda");
                Optional<ButtonType> result = alert.showAndWait();
        }

    }
}
