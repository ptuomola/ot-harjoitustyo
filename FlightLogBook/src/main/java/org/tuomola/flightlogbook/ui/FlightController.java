package org.tuomola.flightlogbook.ui;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.DateHelper;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.FormatHelper;
import org.tuomola.flightlogbook.ui.util.StageHelper;

@Component
@FxmlView("flightScene.fxml")
public class FlightController {
    
    @Autowired
    private FxWeaver fxWeaver;
    
    @Autowired
    private FlightService fs;
    
    @Autowired
    private FlightLogService fls;
    
    @Autowired
    private LoggedInUserService lius;
    
    private Flight f;
    private Timer timer;
    
    @FXML private Label currentTimeLabel;
    @FXML private Label flightTimeLabel;
    @FXML private Label totalTimeLabel;
    @FXML private Label departureTimeLabel;
    @FXML private Label arrivalTimeLabel;
    @FXML private Label takeOffTimeLabel;
    @FXML private Label landingTimeLabel;
    @FXML private Label numTakeOffsLabel;
    @FXML private Label numLandingsLabel;
    @FXML private Button nextActionButton;
    @FXML private Button touchAndGoButton;
    
    public FlightController(FxWeaver fxWeaver, FlightService fs, FlightLogService fls, LoggedInUserService lius) {
        this.fxWeaver = fxWeaver;
        this.fs = fs;
        this.fls = fls;
        this.lius = lius;
    }
    
    public void initialize() {
        currentTimeLabel.setText(DateHelper.toTimeString(Instant.now()));
        
        FlightLog fl = fls.findOrCreateLog(lius.getLoggedInPilot());
        f = fls.addNewFlight(fl);
        
        displayFlight();
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()-> {
                    updateDurations(); 
                });
            }
        }, 0, 1000);
    }
    
    private void updateDurations()
    {
        Instant now = Instant.now();
        currentTimeLabel.setText(DateHelper.toTimeString(now));

        if(f.getDuration() != null) {
            totalTimeLabel.setText(FormatHelper.formatDuration(f.getDuration()));
        } else if(f.getDepartureTime() != null) {
            totalTimeLabel.setText(FormatHelper.formatDuration(Duration.between(f.getDepartureTime(), now)));
        } else {
            totalTimeLabel.setText("00:00:00");
        }
        
        if(f.getFlightDuration() != null) {
            flightTimeLabel.setText(FormatHelper.formatDuration(f.getFlightDuration()));
        } else if(f.getTakeOffTime() != null) {
            flightTimeLabel.setText(FormatHelper.formatDuration(Duration.between(f.getTakeOffTime(), now)));
        } else {
            flightTimeLabel.setText("00:00:00");
        }
    }
    
    public void handleNextActionButtonAction(ActionEvent event) {
        switch(f.getFlightState()) {
            case INITIAL: 
                fs.startTaxi(f);
                displayFlight();
                break;
            case PRE_TAXI: 
                fs.takeOff(f);
                displayFlight();
                break;
            case FLIGHT:
                fs.land(f);
                displayFlight();
                break;
            case POST_TAXI:
                fs.stopFlight(f);
                displayFlight();
                break;
            case ENDED:
                StageHelper.switchToView(fxWeaver.loadView(MainController.class), event);                
                break;
        }
    }

    public void handleTouchAndGoButtonAction(ActionEvent event) {
        fs.touchAndGo(f);
        displayFlight();
    }

    private void displayFlight() {
        departureTimeLabel.setText(DateHelper.toTimeString(f.getDepartureTime()));
        arrivalTimeLabel.setText(DateHelper.toTimeString(f.getArrivalTime()));
        takeOffTimeLabel.setText(DateHelper.toTimeString(f.getTakeOffTime()));
        landingTimeLabel.setText(DateHelper.toTimeString(f.getLandingTime()));
        numTakeOffsLabel.setText("" + f.getNumTakeOffs());
        numLandingsLabel.setText("" + f.getNumLandings());
                
        switch(f.getFlightState()) {
            case INITIAL: 
                nextActionButton.setText("Start taxi");
                touchAndGoButton.setDisable(true);
                break;
            case PRE_TAXI: 
                nextActionButton.setText("Take off");
                touchAndGoButton.setDisable(true);
                break;
            case FLIGHT:
                nextActionButton.setText("Land");
                touchAndGoButton.setDisable(false);
                break;
            case POST_TAXI:
                nextActionButton.setText("Finish taxi");
                flightTimeLabel.setText(FormatHelper.formatDuration(f.getFlightDuration()));
                touchAndGoButton.setDisable(true);
                break;
            case ENDED:
                timer.cancel();
                nextActionButton.setText("End flight");
                flightTimeLabel.setText(FormatHelper.formatDuration(f.getFlightDuration()));
                totalTimeLabel.setText(FormatHelper.formatDuration(f.getDuration()));
                touchAndGoButton.setDisable(true);
                break;
        }
    }
}



