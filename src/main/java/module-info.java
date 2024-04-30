module com.mycompany.projecte_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql; 
    requires javafx.swing;
    requires java.desktop; 
    requires javafx.base;

    opens com.mycompany.projecte_app to javafx.fxml;
    exports com.mycompany.projecte_app;
}
