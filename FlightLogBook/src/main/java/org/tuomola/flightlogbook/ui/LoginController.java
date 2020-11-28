package org.tuomola.flightlogbook.ui;

import org.tuomola.flightlogbook.ui.util.AlertHelper;
import org.tuomola.flightlogbook.ui.util.StageHelper;
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
            AlertHelper.displayAlert("Login failed", "Invalid username or password", AlertType.ERROR);
            return;
        }
        
        lius.setLoggedInPilot(pilot);
        StageHelper.switchToView(fxWeaver.loadView(MainController.class), event); 
    }

    public void handleCreateUserButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(CreatePilotController.class), event);
    }
}