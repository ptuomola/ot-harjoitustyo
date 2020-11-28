package org.tuomola.flightlogbook.ui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ptuomola
 */
public class StageHelper {

    public static void switchToView(Parent newRoot, ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.show();
    }
    
}
