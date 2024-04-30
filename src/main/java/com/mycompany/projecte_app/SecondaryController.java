package com.mycompany.projecte_app;

import com.mycompany.projecte_app.model.Cambrer;
import com.mycompany.projecte_app.model.GestioDades;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class SecondaryController {
    //REGISTRE DE CAMBRER
    
    @FXML
    TextField TextUsuariRegistre;
    @FXML
    TextField TextContrasenyaRegistre;

    @FXML
    private void IniciSessio() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void Registrarse() throws IOException {
        App.setRoot("terciary");
    }
    
    public void afegir() throws SQLException, FileNotFoundException, IOException {
        if (!TextUsuariRegistre.getText().isEmpty() && !TextContrasenyaRegistre.getText().isEmpty()) {
            Cambrer cambrer = new Cambrer(TextContrasenyaRegistre.getText(), TextUsuariRegistre.getText());
            boolean ok = GestioDades.afegeixUsuari(cambrer);
            
            if (ok == true) {
                this.Registrarse();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No s'ha pogut afegir l'usuari");
                alert.setHeaderText("Intentar-ho de nou");
                Optional<ButtonType> result = alert.showAndWait();
            }
            } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Es necessari completar els camps buits");
            alert2.setHeaderText("Completar Dades");
            Optional<ButtonType> result = alert2.showAndWait();
        }

    }
    
}