package org.tuomola.flightlogbook.ui;

import java.time.Duration;
import java.util.Date;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import org.tuomola.flightlogbook.ui.util.StageHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tuomola.flightlogbook.dao.PilotAircraftDAO;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Pilot;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.LoggedInUserService;
import org.tuomola.flightlogbook.ui.util.DateCellFactory;
import org.tuomola.flightlogbook.ui.util.DurationCellFactory;

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
    @FXML private TableColumn<PilotAircraftDAO, Date> lastFlightDate;
    @FXML private TableColumn<PilotAircraftDAO, Duration> hoursFlown;
    @FXML private TableView table;
    
    public AircraftsViewController(FxWeaver fxWeaver, LoggedInUserService lius, AircraftService as) {
        this.fxWeaver = fxWeaver;
        this.lius = lius;
        this.as = as;
    }


    
    public void initialize() {
        Pilot p = lius.getLoggedInPilot();
        
        final ObservableList<PilotAircraftDAO> data = FXCollections.observableArrayList(as.getAllAircraftWithFlightData(p));
        identifier.setCellValueFactory(new PropertyValueFactory<PilotAircraftDAO, String>("identifier"));
        type.setCellValueFactory(new PropertyValueFactory<PilotAircraftDAO, String>("type"));

        lastFlightDate.setCellFactory(column -> DateCellFactory.<PilotAircraftDAO>createCell());
        lastFlightDate.setCellValueFactory(new PropertyValueFactory<>("lastFlight"));

        hoursFlown.setCellFactory(column -> DurationCellFactory.<PilotAircraftDAO>createCell());
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