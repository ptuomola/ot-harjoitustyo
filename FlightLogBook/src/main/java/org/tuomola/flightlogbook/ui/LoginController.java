package org.tuomola.flightlogbook.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.service.PilotService;

@Component
@FxmlView("loginScene.fxml")
public class LoginController {
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
    @FXML private Button createUserButton;
    
    @Autowired
    private PilotService ps;
    
    @Autowired
    private FxWeaver fxWeaver;

    @Autowired
    private LoggedInUserService lius;

    public LoginController(PilotService ps, FxWeaver fxWeaver, LoggedInUserService lius) {
        this.ps = ps;
        this.fxWeaver = fxWeaver;
        this.lius = lius;
    }
    
    public void initialize() {
    }
    
    public void handleLoginButtonAction(ActionEvent event) {
        Pilot pilot = ps.loginPilot(user.getText(), password.getText());
        
        if(pilot == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText("Login failed");
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
            return;
        }
        
        lius.setLoggedInPilot(pilot);
        
        Parent root = fxWeaver.loadView(MainController.class);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleCreateUserButtonAction(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(CreatePilotController.class);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}