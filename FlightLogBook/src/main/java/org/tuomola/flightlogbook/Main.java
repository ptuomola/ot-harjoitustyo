package org.tuomola.flightlogbook;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tuomola.flightlogbook.service.LogBookService;
import org.tuomola.flightlogbook.ui.TextUI;

/**
 *
 * @author ptuomola
 */
//public class Main extends Application {
public class Main {
      
/*
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/styles.css").toExternalForm());
        
        stage.setTitle("Flight Log Book");
        stage.setScene(scene);
        stage.show();
    }
*/
    
    public static void main(String[] args) {
        // TODO: Graphic UI disabled for now - implementing business logic with text UI
        // launch(args);

        TextUI ui = new TextUI(new Scanner(System.in));
        ui.execute(new LogBookService());
    }
}
