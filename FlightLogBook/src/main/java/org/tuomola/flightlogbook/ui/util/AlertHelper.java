package org.tuomola.flightlogbook.ui.util;

import javafx.scene.control.Alert;

/**
 * Helper class for displaying an Alert popup.
 * @author ptuomola
 */
public class AlertHelper {
    /**
     * Display an Alert popup.
     * @param alertTitle title of the alert
     * @param alertContent content of the alert
     * @param alertType type of the alert
     */
    public static void displayAlert(String alertTitle, String alertContent, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertTitle);
        alert.setContentText(alertContent);
        alert.showAndWait();    
    }
}
