package com.mycompany.projecte_app;

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
        
        if (!comandas.isEmpty()) {
            CambrerAssociat.setText(gestiodades.ObtenirCambrerDeTaula(taulaSeleccionada));
        
        }else{
            CambrerAssociat.setText("");
        }
    }

    public void BotoAfegir() {
        if (!ComboTaula.getItems().isEmpty() && !ComboProducte.getItems().isEmpty() && !NumeroClients.getText().isEmpty() && !QuantitatProducte.getText().isEmpty()
                && Integer.parseInt(NumeroClients.getText()) >= 1 && Integer.parseInt(QuantitatProducte.getText()) >= 1) {
            try {
                // Intenta convertir el text de NumeroClients a un enter
                int numClients = Integer.parseInt(NumeroClients.getText());
                int quantitatProducte = Integer.parseInt(QuantitatProducte.getText());

                Taula taulaSeleccionada = ComboTaula.getValue();
                Producte producteSeleccionat = ComboProducte.getValue();

                // Construir l'objecte comanda
                boolean ok1 = gestiodades.actualizarNumClients(taulaSeleccionada, numClients);
                boolean ok2 = gestiodades.afegirComanda(taulaSeleccionada, producteSeleccionat, quantitatProducte);

                if (ok1 && ok2) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Comanda Afegida");
                    alert.setHeaderText("La comanda s'ha afegit amb èxit.");
                    Optional<ButtonType> result = alert.showAndWait();
                    this.actualizarListaComandas(taulaSeleccionada);
                }
            } catch (NumberFormatException e) {
                // En cas d'error, mostra un missatge d'alerta
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("'Numero de clients' i 'Quantitat del producte' han de ser números enters.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        } else {
            // Mostra missatge d'alerta si falta algun valor
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Si us plau, omple tots els camps.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    
    public void borrarComanda(){
        if (!TextCompte.getSelectionModel().isEmpty()) {
            
            Comanda comandaSeleccionada = (Comanda) TextCompte.getSelectionModel().getSelectedItem();
            boolean ok = gestiodades.borrarComanda(comandaSeleccionada);
        
            if (ok == true) {
                this.actualizarListaComandas( ComboTaula.getValue());
            }
        
        } else {
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("SELECCIONA UNA COMANDA");
              alert.setHeaderText("Has de seleccionar la comanda a eliminar");
              Optional<ButtonType> result = alert.showAndWait();
        }
           
    }
    
    public void modificarComanda() {
        if (!TextCompte.getSelectionModel().isEmpty() && !NovaQuantitat.getText().isEmpty() && Integer.parseInt(NovaQuantitat.getText()) >= 1) {
            try {
                Comanda comandaSeleccionada = (Comanda) TextCompte.getSelectionModel().getSelectedItem();
                int novaQuantitat = Integer.parseInt(NovaQuantitat.getText());

                boolean ok = gestiodades.modificarComanda(comandaSeleccionada, novaQuantitat);

                if (ok) {
                    this.actualizarListaComandas(ComboTaula.getValue());
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("La nova quantitat ha de ser un número enter.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SELECCIONA UNA COMANDA");
            alert.setHeaderText("Has de seleccionar la comanda a modificar i indicar una nova quantitat.");
            alert.setHeaderText("La quantitat ha de ser major o igual a 1");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    
    @FXML
    private void Quaternary() throws IOException{
        App.setRoot("quaternary");
    }
}
