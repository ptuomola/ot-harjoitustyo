package org.tuomola.flightlogbook.ui;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("flightScene.fxml")
public class FlightController {
    
    @Autowired
    private FxWeaver fxWeaver;
    
    public FlightController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
    
    public void initialize() {
    }
    
}