package org.tuomola.flightlogbook.ui;

import org.tuomola.flightlogbook.ui.util.AlertHelper;
import org.tuomola.flightlogbook.ui.util.StageHelper;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.PasswordService;
import org.tuomola.flightlogbook.service.PilotService;

/**
 * Controller for handling the Create Pilot scene.
 * @author ptuomola
 */
@Component
@FxmlView("createPilotScene.fxml")
public class CreatePilotController {
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private PasswordField repeatPassword;
    @FXML private TextField emailField;
    @FXML private TextField fullNameField;
    @FXML private DatePicker dobField;
    
    @Autowired
    private final PilotService ps;
    
    @Autowired
    private final PasswordService pwds;
    
    @Autowired
    private final FxWeaver fxWeaver;
    
    public CreatePilotController(PilotService ps, PasswordService pwds, FxWeaver fxWeaver) {
        this.ps = ps;
        this.pwds = pwds;
        this.fxWeaver = fxWeaver;
    }
    
    public void initialize() {
        
        dobField.getEditor().focusedProperty().addListener((obj, wasFocused, isFocused)-> {
            if (!isFocused) {
                try {
                    dobField.setValue(dobField.getConverter().fromString(dobField.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    dobField.getEditor().setText(dobField.getConverter().toString(dobField.getValue()));
                }
            }
        });
    }
    
    public void handleCreatePilotAction(ActionEvent event) {
        
        String username = user.getText(); 
        if (isInvalidUsername(username)) return;
        
        String passwordStr = password.getText();
        if (isInvalidPassword(passwordStr)) return;       
        
        String encryptedPassword = pwds.encrypt(passwordStr);
        
        Pilot p = new Pilot();
        p.setUserName(username);
        p.setPassword(encryptedPassword);
        p.setFullName(fullNameField.getText());
        p.setEmail(emailField.getText());
        
        if (dobField.getValue() != null) {
            p.setDateOfBirth(Date.from(dobField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));            
        }
        
        ps.savePilot(p);
        
        AlertHelper.displayAlert("Pilot created", "New pilot created with name '" + username + "'", AlertType.INFORMATION);
        
        StageHelper.switchToView(fxWeaver.loadView(LoginController.class), event);
    }

    private boolean isInvalidUsername(String username) {
        if (username == null || username.length() < 3) {
            AlertHelper.displayAlert("Invalid username", "Must be at least 3 characters", AlertType.ERROR);
            return true;
        }
        if (ps.isUsernameInUse(username)) {
            AlertHelper.displayAlert("Invalid username", "Username already in use", AlertType.ERROR);
            return true;
        }
        return false;
    }

    private boolean isInvalidPassword(String passwordStr) {
        if (passwordStr == null || !pwds.isValid(passwordStr)) {
            AlertHelper.displayAlert("Invalid password", "Need to be at least 8 characters, and contain uppercase, lowercase, digit and special character", AlertType.ERROR);
            return true;
        }
        String repeatPasswordStr = repeatPassword.getText();
        if (repeatPasswordStr == null || (repeatPasswordStr != null && !repeatPasswordStr.equals(passwordStr))) {
            AlertHelper.displayAlert("Invalid password", "Passwords do not match", AlertType.ERROR);
            return true;
        }
        return false;
    }

    public void handleCancelAction(ActionEvent event) throws IOException {
        StageHelper.switchToView(fxWeaver.loadView(LoginController.class), event);
    }
}