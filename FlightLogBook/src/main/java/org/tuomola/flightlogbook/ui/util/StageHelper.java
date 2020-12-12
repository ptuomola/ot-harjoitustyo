package org.tuomola.flightlogbook.ui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Helper class for switching scenes in a stage.
 * @author ptuomola
 */
public class StageHelper {

    /**
     * Switch the current stage to show the new view.
     * @param newRoot view to show
     * @param event event that has triggered the view change
     */
    public static void switchToView(Parent newRoot, ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.show();
    }
    
}
