package org.tuomola.flightlogbook.ui;

import java.time.Duration;
import java.util.Date;
import org.tuomola.flightlogbook.ui.util.StageHelper;
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
import org.tuomola.flightlogbook.dao.PilotAircraftDAO;
import org.tuomola.flightlogbook.dao.PilotAirportDAO;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.DateCellFactory;
import org.tuomola.flightlogbook.ui.util.DurationCellFactory;

@Component
@FxmlView("airportsViewScene.fxml")
public class AirportsViewController {
    
    @Autowired
    private final FxWeaver fxWeaver;

    @Autowired
    private final LoggedInUserService lius;
    
    @Autowired
    private final AirportService as;
    
    @FXML private TableColumn code;
    @FXML private TableColumn name;
    @FXML private TableColumn<PilotAirportDAO, Date> lastVisit;
    @FXML private TableColumn numDepartures;
    @FXML private TableColumn numArrivals;
    @FXML private TableView table;
    
    public AirportsViewController(FxWeaver fxWeaver, LoggedInUserService lius, AirportService as) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
        this.as = as;
    }

    public void initialize() {
        Pilot p = lius.getLoggedInPilot();
        
        final ObservableList<PilotAirportDAO> data = FXCollections.observableArrayList(as.getAllAirportsWithVisits(p));
        code.setCellValueFactory(new PropertyValueFactory<PilotAirportDAO, String>("code"));
        name.setCellValueFactory(new PropertyValueFactory<PilotAirportDAO, String>("name"));

        lastVisit.setCellFactory(column -> DateCellFactory.<PilotAirportDAO>createCell());
        lastVisit.setCellValueFactory(new PropertyValueFactory<>("lastVisit"));

        numDepartures.setCellValueFactory(new PropertyValueFactory<PilotAirportDAO, Integer>("numDepartures"));
        numArrivals.setCellValueFactory(new PropertyValueFactory<PilotAirportDAO, Integer>("numArrivals"));
        
        table.setItems(data);
        
        code.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(code);
        table.sort();
    }
    
    public void handleOkButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(MainController.class), event);
    }
}