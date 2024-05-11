package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.GestioDades;
import com.mycompany.projecte_app.model.Taula;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class QuaternaryControll {

    GestioDades gestiodades = new GestioDades();

    @FXML
    ComboBox<Taula> ComboTaula;

    @FXML
    TextArea QuantitatTotal;

    @FXML
    TextArea QuantitatPersona;

    @FXML
    private void initialize() {
        ComboTaula.setItems(gestiodades.llistaTaules());
    }

    public void ActualitzarPreus() {
        Taula taulaseleccionada = ComboTaula.getValue();
        double precioTotal = gestiodades.ObtenirPreuTotalTaula(taulaseleccionada);
        int numPersonas = taulaseleccionada.getNum_Clients();
        double preuPerPersona = precioTotal / numPersonas;

        QuantitatTotal.setText(String.valueOf(precioTotal));
        QuantitatPersona.setText(String.valueOf(preuPerPersona));
    }
    
    public void FinalitzarTaula() {
        Taula taula = ComboTaula.getValue();
        boolean ok = gestiodades.FinalitzarTaula(taula); //Esborrar les comandes de la BD abans d'entrar al terciary;

        if (ok == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Taula Finalitzada");
            alert.setHeaderText("Les comandes associades a la taula s'han eliminat");
            Optional<ButtonType> result = alert.showAndWait();

        }
    }
    
    @FXML
    private void panellPrincipal() throws IOException{
        App.setRoot("terciary");
    }
}

   