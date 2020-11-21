package org.tuomola.flightlogbook;

import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.tuomola.flightlogbook.ui.TextUI;

/**
 *
 * @author ptuomola
 */

//public class Main extends Application {

@EnableJpaRepositories
@SpringBootApplication
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
        
        // Start off UI
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        TextUI ui = ctx.getBean(TextUI.class);
        ui.execute(new Scanner(System.in));
    }
}
