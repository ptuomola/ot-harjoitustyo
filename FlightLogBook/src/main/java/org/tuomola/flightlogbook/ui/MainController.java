package org.tuomola.flightlogbook.ui;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
    
    public void initialize() {
    }
    
    public void handleStartFlightButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/flightScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/styles.css").toExternalForm());
        
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Platform.exit();
    }
}