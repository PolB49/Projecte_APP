package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.Cambrer;
import com.mycompany.projecte_app.model.Comanda;
import com.mycompany.projecte_app.model.GestioDades;
import com.mycompany.projecte_app.model.Producte;
import com.mycompany.projecte_app.model.Taula;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TerciaryControll {

    GestioDades gestiodades = new GestioDades();

    @FXML
    ComboBox<Taula> ComboTaula;

    @FXML
    ComboBox<Producte> ComboProducte;

    @FXML
    TextField NumeroClients;

    @FXML
    TextField QuantitatProducte;

    @FXML
    TextField CambrerAssociat;

    @FXML
    ListView<Comanda> TextCompte;
    
    @FXML
    TextField NovaQuantitat;

    @FXML
    private void Registrarse() throws IOException {
        App.setRoot("primary");
    }

    public void initialize() {
        ComboTaula.setItems(gestiodades.llistaTaules());
        ComboProducte.setItems(gestiodades.llistaProductes());

        ComboTaula.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarListaComandas(newValue);
            }
        });

    }

    private void actualizarListaComandas(Taula taulaSeleccionada) {
        TextCompte.getItems().clear();

        ObservableList<Comanda> comandas = gestiodades.ObtenirComandesPerTaula(taulaSeleccionada);

        TextCompte.getItems().addAll(comandas);
    }

    public void BotoAfegir() {
        Taula taulaSeleccionada = ComboTaula.getValue();
        Producte producteSeleccionat = ComboProducte.getValue();
        System.out.println("PRODUCTE DEL COMBOBOX TERCIARY: " + producteSeleccionat.getPreu());
        int numClients = Integer.parseInt(NumeroClients.getText());
        int quantitatProducte = Integer.parseInt(QuantitatProducte.getText());
        
        //Contruir l'objecte comanda

        boolean ok1 = gestiodades.actualizarNumClients(taulaSeleccionada, numClients);
        boolean ok2 = gestiodades.afegirComanda(taulaSeleccionada, producteSeleccionat, quantitatProducte);

        if (ok1 && ok2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Comanda Afegida");
            alert.setHeaderText("La comanda s'ha afegit amb èxit.");
            Optional<ButtonType> result = alert.showAndWait();
            this.actualizarListaComandas(taulaSeleccionada);

            if (CambrerAssociat.getText().isEmpty()) {
                CambrerAssociat.setText(gestiodades.CambrerActual.getNom());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Hi ha hagut un error al processar la comanda.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
    
    public void borrarComanda(){
        Comanda comandaSeleccionada = (Comanda) TextCompte.getSelectionModel().getSelectedItem();
        boolean ok = gestiodades.borrarComanda(comandaSeleccionada);
        
        if (ok == true) {
            this.actualizarListaComandas( ComboTaula.getValue());
        } else {
            System.out.println("No s'ha pogut esborrar la comanda");
        }
    }
    
    public void modificarComanda(){
        Comanda comandaSeleccionada = (Comanda) TextCompte.getSelectionModel().getSelectedItem();
        int novaQuantitat = Integer.parseInt(NovaQuantitat.getText());
        
        boolean ok = gestiodades.modificarComanda(comandaSeleccionada, novaQuantitat);
        
        if (ok == true) {
            this.actualizarListaComandas( ComboTaula.getValue());
        } else {
            System.out.println("No s'ha pogut modificar la comanda");
        }
    }
    
    @FXML
    private void Quaternary() throws IOException{
        App.setRoot("quaternary");
    }
}
