package org.tuomola.flightlogbook.ui;

import org.tuomola.flightlogbook.ui.util.StageHelper;
import java.io.IOException;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.FormatHelper;

/**
 * Controller for the Main scene.
 * @author ptuomola
 */
@Component
@FxmlView("mainScene.fxml")
public class MainController {
    
    @Autowired
    private final FxWeaver fxWeaver;

    @Autowired
    private final LoggedInUserService lius;
    
    @Autowired
    private final FlightLogService fls;
    
    @FXML private Label usernameLabel;
    @FXML private Label totalFlightTimeLabel;
    @FXML private Label totalTakeOffsLabel;
    @FXML private Label totalLandingsLabel;
    @FXML private Label last12mFlightTimeLabel;
    @FXML private Label last3mTakeOffsLabel;
    @FXML private Label last3mLandingsLabel;
    @FXML private Label totalFlightsLabel;
    @FXML private Label last12mFlightsLabel;
    @FXML private Label warningsLabel;
    
    public MainController(FxWeaver fxWeaver, LoggedInUserService lius, FlightLogService fls) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
        this.fls = fls;
    }
    
    public void initialize() {
        Pilot p = lius.getLoggedInPilot();
        FlightLog fl = fls.findOrCreateLog(p);
        
        usernameLabel.setText(p.getCompositeName());
        totalFlightTimeLabel.setText(FormatHelper.formatDuration(fl.getTotalFlightTime()));
        totalFlightsLabel.setText("" + fl.getTotalFlights());
        totalTakeOffsLabel.setText("" + fl.getTotalTakeOffs());
        totalLandingsLabel.setText("" + fl.getTotalLandings());
        last12mFlightsLabel.setText("" + fl.getFlightsDuringLast(Period.ofDays(365)));
        
        Duration flightTimeDuringLast12Months = fl.getFlightTimeDuringLast(Period.ofDays(365));
        last12mFlightTimeLabel.setText(FormatHelper.formatDuration(flightTimeDuringLast12Months));
                
        int takeOffsDuringLast3Months = fl.getTakeOffsDuringLast(Period.ofDays(90));
        last3mTakeOffsLabel.setText("" + takeOffsDuringLast3Months);

        int landingsDuringLast3Months = fl.getLandingsDuringLast(Period.ofDays(90));
        last3mLandingsLabel.setText("" + landingsDuringLast3Months);

        setWarnings(flightTimeDuringLast12Months, landingsDuringLast3Months, takeOffsDuringLast3Months);
    }

    private void setWarnings(Duration flightTimeDuringLast12Months, int landingsDuringLast3Months, int takeOffsDuringLast3Months) {

        if (flightTimeDuringLast12Months.compareTo(Duration.of(12, ChronoUnit.HOURS)) < 0) {
            last12mFlightTimeLabel.setTextFill(Color.RED);
        } 

        if (takeOffsDuringLast3Months < 3) {
            last3mTakeOffsLabel.setTextFill(Color.RED);
        } 

        if (landingsDuringLast3Months < 3) {
            last3mLandingsLabel.setTextFill(Color.RED);
        }

        String warnings = determineWarningLabel(flightTimeDuringLast12Months, landingsDuringLast3Months, takeOffsDuringLast3Months);
        
        if(!warnings.equals("")) {
            warningsLabel.setTextFill(Color.RED);
            warningsLabel.setText(warnings);
        }
    }

    private String determineWarningLabel(Duration flightTimeDuringLast12Months, int landingsDuringLast3Months, int takeOffsDuringLast3Months) {

        if (flightTimeDuringLast12Months.compareTo(Duration.of(12, ChronoUnit.HOURS)) < 0) {
            if((landingsDuringLast3Months < 3) || (takeOffsDuringLast3Months < 3)) {
                return "Not able to renew, no passengers";
            } else {
                return "Not able to renew";
            }
        } else {
            if((landingsDuringLast3Months < 3) || (takeOffsDuringLast3Months < 3)) {
                return "No passengers";
            } 
        }
        
        return "";
    }
    
    public void handleStartFlightButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(FlightController.class), event);
    }

    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handleViewAircraftButtonAction(ActionEvent event) throws IOException {
        StageHelper.switchToView(fxWeaver.loadView(AircraftsViewController.class), event);
    }

    public void handleViewAirportsButtonAction(ActionEvent event) throws IOException {
        StageHelper.switchToView(fxWeaver.loadView(AirportsViewController.class), event);
    }

    public void handleViewFlightsButtonAction(ActionEvent event) throws IOException {
        StageHelper.switchToView(fxWeaver.loadView(FlightsViewController.class), event);
    }
}