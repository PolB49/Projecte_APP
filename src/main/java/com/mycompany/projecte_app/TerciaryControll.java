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
    TextArea QuantitatTotal;
    @FXML
    TextArea QuantitatPersona;
    
    @FXML
    private void Registrarse() throws IOException {
        App.setRoot("primary");
    }
    
    public void initialize() { //Que pasa al arribar al TerciaryControll
    ComboTaula.setItems(gestiodades.llistaTaules()); // Vincula el ComboBox de taules amb les Taules del restaurant de la BD
    ComboProducte.setItems(gestiodades.llistaProductes()); // Vincula el ComboBox de taules amb les Taules del restaurant de la BD
    
    // Comprovar que el ComboBox de taula no canvia de valor, si canvia de valor executar el if
    ComboTaula.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            actualizarListaComandas((Taula) newValue); // Actualiza la lista de comandas al seleccionar una nueva mesa
        }
    });
    
    //Elimina totes les comandes emmagatzemades a la BD un cop es carregui el Tercer Controlador
    gestiodades.borrarComanda();
}
    
    private void actualizarListaComandas(Taula taulaSeleccionada) {
    TextCompte.getItems().clear(); // Borra els elements actuals del ListView
    
    // Recupera una ObservableList de totes les comandes que continguin la taula que es passa al mètode
    ObservableList<Comanda> comandas = gestiodades.ObtenirComandesPerTaula(taulaSeleccionada);
    
    // Actualiza el ListView amb aquesta llista de comandes recuperada
    TextCompte.getItems().addAll(comandas);
}

     
    public void BotoAfegir(){
        Taula TaulaSeleccionada = (Taula) ComboTaula.getValue(); //Aconseguir l'objecte Taula marcat en el combobox deTaules
        Producte ProducteSeleccionat = (Producte) ComboProducte.getValue(); //Aconseguir l'objecte Producte del combobox de Productes
        int NumClients = Integer.parseInt(NumeroClients.getText()); //Aconseguir el numero de clients del TextField
        int quantitatProducte = Integer.parseInt(QuantitatProducte.getText()); //Aconseguir la quantitat del producte seleccionat del combobox
        
        boolean ok1 = gestiodades.actualizarNumClients(TaulaSeleccionada, NumClients); //Actualitzar nombre de clients de la taula
        
        boolean ok2 = gestiodades.afegirComanda( TaulaSeleccionada, ProducteSeleccionat, quantitatProducte); //Crear una nova comanda
        
        /*boolean ok3 = gestiodades.AssociarCambrerTaula() Completar el codi afegint un metode a gestio dades i al control Textfield de cambrer associat mostrar la coluna de cambrer associat de la taula Taula de la BD*/
        
        if (ok1 == true && ok2 == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Comanda Enviada");
                alert.setHeaderText("Acceptar");
                Optional<ButtonType> result = alert.showAndWait();
                
                Comanda comanda = new Comanda (TaulaSeleccionada, ProducteSeleccionat, quantitatProducte); //Recuperala comanda enviada
                
                // Verificar si la comanda ya está presente en la lista (Fer un metode a gestioDades)
                boolean comandaExistente = false;
                for (Object item : TextCompte.getItems()) {
                    if (item instanceof Comanda) {
                        Comanda existingComanda = (Comanda) item;
                    if (existingComanda.getTaulaSeleccionada().equals(comanda.getTaulaSeleccionada())
                            && existingComanda.getProducteSeleccionat().equals(comanda.getProducteSeleccionat())) {
                        // Si ya hay una comanda para esta mesa y producto,
                        // actualiza la cantidad acumulando la nueva cantidad
                        existingComanda.setQuantitatProducte(existingComanda.getQuantitatProducte() + comanda.getQuantitatProducte());
                        comandaExistente = true;
                        break;
                        }
                    }
                 }
                
                // Si la comanda no está presente en la lista, agregarla
                 if (!comandaExistente) {
                    TextCompte.getItems().add(comanda); 
                 }else{
                     this.actualizarListaComandas(TaulaSeleccionada);
                 }
                
                if (CambrerAssociat.getText().isEmpty()) { //Canviar esto per a que mostri el valor de la BD i no en de la variable del programa de JAVA
                CambrerAssociat.setText(gestiodades.CambrerActual.getNom());
            }
            
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Dades");
                alert.setHeaderText("No s'ha pogut tramitar la comanda");
                Optional<ButtonType> result = alert.showAndWait();
        }

    }
    
     public void BotoMostrarCompte(){
         Taula TaulaSeleccionada = (Taula) ComboTaula.getValue(); //Aconseguir l'objecte Taula marcat en el combobox deTaules
          double PreuTaula = gestiodades.ObtenirPreuTotalTaula(TaulaSeleccionada);
          double PreuPersona = PreuTaula / Integer.valueOf(NumeroClients.getText());
          QuantitatTotal.setText(String.valueOf(PreuTaula));
          QuantitatPersona.setText(String.valueOf(PreuPersona));
    }
}
