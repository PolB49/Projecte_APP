package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.GestioDades;
import com.mycompany.projecte_app.model.Taula;
import java.io.IOException;
import javafx.fxml.FXML;
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
    
    @FXML
    private void panellPrincipal() throws IOException{
        App.setRoot("terciary");
    }
}

   