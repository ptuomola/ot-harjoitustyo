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
import org.tuomola.flightlogbook.dto.PilotAircraftDTO;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.DateCellFactory;
import org.tuomola.flightlogbook.ui.util.DurationCellFactory;

/**
 * Controller for handling the "View Aircraft" scene.
 * @author ptuomola
 */
@Component
@FxmlView("aircraftsViewScene.fxml")
public class AircraftsViewController {
    
    @Autowired
    private final FxWeaver fxWeaver;

    @Autowired
    private final LoggedInUserService lius;
    
    @Autowired
    private final AircraftService as;
    
    @FXML private TableColumn identifier;
    @FXML private TableColumn type;
    @FXML private TableColumn<PilotAircraftDTO, Date> lastFlightDate;
    @FXML private TableColumn<PilotAircraftDTO, Duration> hoursFlown;
    @FXML private TableView table;
    
    public AircraftsViewController(FxWeaver fxWeaver, LoggedInUserService lius, AircraftService as) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
        this.as = as;
    }
    
    public void initialize() {
        Pilot p = lius.getLoggedInPilot();
        
        final ObservableList<PilotAircraftDTO> data = FXCollections.observableArrayList(as.getAllAircraftWithFlightData(p));
        identifier.setCellValueFactory(new PropertyValueFactory<PilotAircraftDTO, String>("identifier"));
        type.setCellValueFactory(new PropertyValueFactory<PilotAircraftDTO, String>("type"));

        lastFlightDate.setCellFactory(column -> DateCellFactory.<PilotAircraftDTO>createCell());
        lastFlightDate.setCellValueFactory(new PropertyValueFactory<>("lastFlight"));

        hoursFlown.setCellFactory(column -> DurationCellFactory.<PilotAircraftDTO>createCell());
        hoursFlown.setCellValueFactory(new PropertyValueFactory<>("hoursFlown"));
        
        table.setItems(data);
        
        identifier.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(identifier);
        table.sort();
    }
    
    public void handleOkButtonAction(ActionEvent event) {
        StageHelper.switchToView(fxWeaver.loadView(MainController.class), event);
    }
}