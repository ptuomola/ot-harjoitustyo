package org.tuomola.flightlogbook.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.FlightService;

/**
 *
 * @author ptuomola
 */

@Component
@FxmlView("flightDetailsDialogScene.fxml")
public class FlightDetailsDialogController {
    
    private Flight flight;
    
    @FXML private TextField originField;
    @FXML private TextField destinationField;
    @FXML private TextField aircraftField;
    @FXML private TextField pilotField;
    
    @Autowired
    private final FlightService fs;
    
    @Autowired
    private final AirportService apts; 

    @Autowired
    private final AircraftService acs; 

    private String origDestination;
    private String origOrigin;
    private String origAircraft;
    private String origPilot;
    
    public FlightDetailsDialogController(FlightService fs, AirportService apts,  AircraftService acs) {
        this.fs = fs;
        this.apts = apts;
        this.acs = acs; 
    }    
    
    public void initialize() {
        originField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        
        destinationField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        
        aircraftField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));        
    }
        
    public void setFlight(Flight flight) {
        this.flight = flight;
        
        if (flight.getOrigin() != null) {
            origOrigin = flight.getOrigin().getCode();
            originField.setText(origOrigin);
        } else {
            origOrigin = null;
        }
        
        if (flight.getDestination() != null) {
            origDestination = flight.getDestination().getCode();
            destinationField.setText(origDestination);
        } else {
            origDestination = null;
        }
        
        if (flight.getAircraft() != null) {
            origAircraft = flight.getAircraft().getIdentifier();
            aircraftField.setText(origAircraft);
        } else {
            origAircraft = null;
        }
        
        if (flight.getPic() != null) {
            origPilot = flight.getPic().getFullName();
            pilotField.setText(origPilot);
        } else {
            origPilot = null;
        }
    }   

    public void handleSaveActionButton(ActionEvent event) {
        if (hasFieldChanged(origOrigin, originField)) {
            if (originField.getText() == null || originField.getText().strip().equals("")) {
                flight.setOrigin(null);
            } else {
                Airport newOrigin = apts.findOrCreateAirport(originField.getText().strip());
                flight.setOrigin(newOrigin);
            }          
        }
        
        if (hasFieldChanged(origDestination, destinationField)) {
            if (destinationField.getText() == null || destinationField.getText().strip().equals("")) {
                flight.setDestination(null);
            } else {
                Airport newDestination = apts.findOrCreateAirport(destinationField.getText().strip());
                flight.setDestination(newDestination);
            }          
        }
        
        if (hasFieldChanged(origAircraft, aircraftField)) {
            if (aircraftField.getText() == null || aircraftField.getText().strip().equals("")) {
                flight.setAircraft(null);
            } else {
                Aircraft newAircraft = acs.findOrCreateAircraft(aircraftField.getText().strip());
                flight.setAircraft(newAircraft);
            }          
        }
        
        fs.saveFlight(flight);
        closeStage(event);
    }
    
    public void handleCancelActionButton(ActionEvent event) {
        closeStage(event);
    }
    
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean hasFieldChanged(String origValue, TextField field) {
        String newValue = field.getText();     
        if (origValue == null && newValue != null) { 
            return true;
        } else if (newValue == null && origValue != null) {
            return true;
        } else if (!origValue.equals(newValue)) { 
            return true;
        } else {
            return false;
        }
    }
}
