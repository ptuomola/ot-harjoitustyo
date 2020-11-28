package org.tuomola.flightlogbook.ui.util;

import javafx.scene.control.Alert;

/**
 *
 * @author ptuomola
 */
public class AlertHelper {
    public static void displayAlert(String alertTitle, String alertContent, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();    
    }
}
