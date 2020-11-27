package org.tuomola.flightlogbook.ui;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.service.LoggedInUserService;

@Component
@FxmlView("mainScene.fxml")
public class MainController {
    
    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private LoggedInUserService lius;
    
    @FXML private Label usernameLabel;
    
    public MainController(FxWeaver fxWeaver, LoggedInUserService lius) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
    }
    
    public void initialize() {
        usernameLabel.setText(lius.getLoggedInPilot().getUserName());
    }
    
    public void handleStartFlightButtonAction(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(FlightController.class);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Platform.exit();
    }

    public void handleViewAircraftButtonAction(ActionEvent event) throws IOException {
        System.out.println("view aircraft");
    }

    public void handleViewAirportsButtonAction(ActionEvent event) throws IOException {
        System.out.println("view airports");
    }

    public void handleViewFlightsButtonAction(ActionEvent event) throws IOException {
        System.out.println("view flights");
    }
}