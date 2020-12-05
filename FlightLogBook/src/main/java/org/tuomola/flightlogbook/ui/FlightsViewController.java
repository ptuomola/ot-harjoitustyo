package org.tuomola.flightlogbook.ui;

import java.time.Instant;
import org.tuomola.flightlogbook.ui.util.StageHelper;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.DateCellFactory;
import org.tuomola.flightlogbook.ui.util.DurationCellFactory;
import org.tuomola.flightlogbook.ui.util.TimeCellFactory;

@Component
@FxmlView("flightsViewScene.fxml")
public class FlightsViewController {
    
    @Autowired
    private final FxWeaver fxWeaver;

    @Autowired
    private final LoggedInUserService lius;
    
    @Autowired
    private final FlightLogService fls;
    
    @FXML private TableColumn dateColumn;
    @FXML private TableColumn deptAirportCode;
    @FXML private TableColumn deptTime;
    @FXML private TableColumn arrAirportCode;
    @FXML private TableColumn arrTime;
    @FXML private TableColumn totalTime;
    @FXML private TableColumn aircraftModel;
    @FXML private TableColumn aircraftReg;
    @FXML private TableColumn picName;
    @FXML private TableColumn numTakeoffs;
    @FXML private TableColumn numLandings;
    @FXML private TableView table;
    
    public FlightsViewController(FxWeaver fxWeaver, LoggedInUserService lius, FlightLogService fls) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
        this.fls = fls;
    }
    
    public void initialize() {
        Pilot p = lius.getLoggedInPilot();
        FlightLog fl = fls.findOrCreateLog(p);
        
        final ObservableList<Flight> data = FXCollections.observableArrayList(fl.getFlights());
        dateColumn.setCellFactory(column -> DateCellFactory.<Flight>createCell());
        dateColumn.setCellValueFactory(new PropertyValueFactory<Flight, Date>("flightDate"));
        numTakeoffs.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("numTakeOffs"));
        numLandings.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("numLandings"));
        totalTime.setCellFactory(column -> DurationCellFactory.<Flight>createCell());
        totalTime.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("duration"));
        deptTime.setCellFactory(column -> TimeCellFactory.<Flight>createCell());
        deptTime.setCellValueFactory(new PropertyValueFactory<Flight, Instant>("departureTime"));
        deptAirportCode.setCellValueFactory(new PropertyValueFactory<Flight, String>("deptAirportCode"));
        arrTime.setCellFactory(column -> TimeCellFactory.<Flight>createCell());
        arrTime.setCellValueFactory(new PropertyValueFactory<Flight, Instant>("arrivalTime"));
        arrAirportCode.setCellValueFactory(new PropertyValueFactory<Flight, String>("arrAirportCode"));
        aircraftModel.setCellValueFactory(new PropertyValueFactory<Flight, String>("aircraftType"));
        aircraftReg.setCellValueFactory(new PropertyValueFactory<Flight, String>("aircraftIdentifier"));
        picName.setCellValueFactory(new PropertyValueFactory<Flight, String>("pilotFullName"));
     
        table.setItems(data);
        dateColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(dateColumn);
        table.sort();
    }
    
    public void handleOkButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(MainController.class), event);
    }
}