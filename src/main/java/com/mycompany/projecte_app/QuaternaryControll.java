package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.GestioDades;
import com.mycompany.projecte_app.model.Taula;
import java.io.IOException;
import java.text.DecimalFormat;
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
        
            if (!ComboTaula.getItems().isEmpty()) {
        ComboTaula.setValue(ComboTaula.getItems().get(0)); //Selecciona el valor per defecte del primer element del combobox
        }
 
         QuantitatTotal.setText("0.00 €");
         QuantitatPersona.setText("0.00 €");
    }

    public void ActualitzarPreus() {
        Taula taulaseleccionada = ComboTaula.getValue();
        double preuTotal = gestiodades.ObtenirPreuTotalTaula(taulaseleccionada);

        // Comprovar si la taula seleccionada té comandes
        if (preuTotal == 0.00) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("La taula seleccionada no té comandes.");
            alert.showAndWait();
        
        }else{
            // Continuar amb el càlcul dels preus si la taula té comandes
            int numPersones = taulaseleccionada.getNum_Clients();
            double preuPerPersona = preuTotal / numPersones;
            preuPerPersona = Math.round(preuPerPersona * 100.0) / 100.0; //Aproximar a 2 decimals
            
            String preuTotalFormatat = String.format("%.2f", preuTotal);
            String preuPerPersonaFormatat = String.format("%.2f", preuPerPersona);

            QuantitatTotal.setText(String.valueOf(preuTotalFormatat) + " €");
            QuantitatPersona.setText(String.valueOf(preuPerPersonaFormatat) + " €");
        }

        
    }

    
    public void FinalitzarTaula() {
        Taula taula = ComboTaula.getValue();
        boolean ok = gestiodades.FinalitzarTaula(taula); //Esborrar les comandes de la BD abans d'entrar al terciary;

        if (ok == true) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Taula Finalitzada");
            alert.setHeaderText("Les comandes associades a la taula s'han eliminat");
            Optional<ButtonType> result = alert.showAndWait();
            
            QuantitatTotal.setText("0.00 €");
            QuantitatPersona.setText("0.00 €");
        }
    }
    
    @FXML
    private void panellPrincipal() throws IOException{
        App.setRoot("terciary");
    }
}

   