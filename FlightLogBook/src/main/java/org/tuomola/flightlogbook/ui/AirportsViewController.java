package org.tuomola.flightlogbook.ui;

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
import org.tuomola.flightlogbook.dto.PilotAirportDTO;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.DateCellFactory;

/**
 * Controller for handling the View Airports scene.
 * @author ptuomola
 */
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
    @FXML private TableColumn<PilotAirportDTO, Date> lastVisit;
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
        
        final ObservableList<PilotAirportDTO> data = FXCollections.observableArrayList(as.getAllAirportsWithVisits(p));
        code.setCellValueFactory(new PropertyValueFactory<PilotAirportDTO, String>("code"));
        name.setCellValueFactory(new PropertyValueFactory<PilotAirportDTO, String>("name"));

        lastVisit.setCellFactory(column -> DateCellFactory.<PilotAirportDTO>createCell());
        lastVisit.setCellValueFactory(new PropertyValueFactory<>("lastVisit"));

        numDepartures.setCellValueFactory(new PropertyValueFactory<PilotAirportDTO, Integer>("numDepartures"));
        numArrivals.setCellValueFactory(new PropertyValueFactory<PilotAirportDTO, Integer>("numArrivals"));
        
        table.setItems(data);
        
        code.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(code);
        table.sort();
    }
    
    public void handleOkButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(MainController.class), event);
    }
}