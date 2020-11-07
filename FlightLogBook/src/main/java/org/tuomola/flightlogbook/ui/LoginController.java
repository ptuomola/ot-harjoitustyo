package org.tuomola.flightlogbook.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button createUserButton;
    
    public void initialize() {
    }
    
    public void handleLoginButtonAction(ActionEvent event) {
        System.out.println("login");
    }

    public void handleCreateUserButtonAction(ActionEvent event) throws IOException {
        System.out.println("create user");
    }
}