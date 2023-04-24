package com.projectgain.controllers;

import com.projectgain.manager.DatabaseManager;
import com.projectgain.models.AvailableWorkouts;
import com.projectgain.models.WorkRoutine;
import com.projectgain.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AvailableWorkoutsController extends BaseController implements Initializable {

    private DatabaseManager manager;
    private AvailableWorkouts model;

    @FXML
    private Pane availableRoutinesRootAnchorPane;

    @FXML
    private TableView<WorkRoutine> availableRoutinesTableView;

    @FXML
    private TableColumn<WorkRoutine, String> workRoutineTableColumn;

    @FXML
    private TableColumn<WorkRoutine, String> workoutTimeTableColumn;

    public AvailableWorkoutsController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    public AvailableWorkoutsController(String fxmlViewName, ViewFactory viewFactory, DatabaseManager manager, AvailableWorkouts model) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
        this.model = model;
        this.model.setRoutines(FXCollections.observableArrayList(this.manager.loadWorkRoutinesFromDatabaseLazily()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workRoutineTableColumn.setCellValueFactory(new PropertyValueFactory<WorkRoutine, String>("title"));
        workoutTimeTableColumn.setCellValueFactory(new PropertyValueFactory<WorkRoutine, String>("workoutDurationInString"));
        availableRoutinesTableView.setItems(model.getRoutines());
    }
}
