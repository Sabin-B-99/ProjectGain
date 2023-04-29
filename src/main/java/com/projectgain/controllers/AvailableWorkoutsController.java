package com.projectgain.controllers;

import com.projectgain.manager.AppManager;
import com.projectgain.manager.DatabaseManager;
import com.projectgain.models.AvailableWorkouts;
import com.projectgain.models.WorkRoutine;
import com.projectgain.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AvailableWorkoutsController extends BaseController implements Initializable {

    private AppManager appManager;
    private AvailableWorkouts model;

    @FXML
    private Pane availableRoutinesRootAnchorPane;

    @FXML
    private TableView<WorkRoutine> availableRoutinesTableView;

    @FXML
    private TableColumn<WorkRoutine, String> workRoutineTableColumn;

    @FXML
    private TableColumn<WorkRoutine, String> workoutTimeTableColumn;

    @FXML
    private TableColumn<WorkRoutine, Void> workoutStartButtonTableColumn;

    @FXML
    private TableColumn<WorkRoutine, Void> workoutDelButtonTableColumn;

    @FXML
    private TableColumn<WorkRoutine, Void> workoutEdtButtonTableColumn;

    public AvailableWorkoutsController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    public AvailableWorkoutsController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager, AvailableWorkouts model) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
        this.model = model;
        this.model.setRoutines(FXCollections.observableArrayList(this.appManager.getDatabaseManager().loadWorkRoutinesFromDatabaseLazily()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workRoutineTableColumn.setCellValueFactory(new PropertyValueFactory<WorkRoutine, String>("title"));
        workoutTimeTableColumn.setCellValueFactory(new PropertyValueFactory<WorkRoutine, String>("workoutDurationInString"));
        addButtonToRoutineTableColumn(workoutStartButtonTableColumn, "Start");
        addButtonToRoutineTableColumn(workoutDelButtonTableColumn, "Delete");
        addButtonToRoutineTableColumn(workoutEdtButtonTableColumn, "Edit");
        availableRoutinesTableView.setItems(model.getRoutines());
    }


    private void addButtonToRoutineTableColumn(TableColumn<WorkRoutine, Void> columnToAddBtnTo, String btnLabel) {
        Callback<TableColumn<WorkRoutine, Void>, TableCell<WorkRoutine, Void>> cellFactory =
                new Callback<TableColumn<WorkRoutine, Void>, TableCell<WorkRoutine, Void>>() {
                    @Override
                    public TableCell<WorkRoutine, Void> call(TableColumn<WorkRoutine, Void> workRoutineVoidTableColumn) {
                        final TableCell<WorkRoutine, Void> cell = new TableCell<WorkRoutine, Void>() {
                            private final Button btn = new Button(btnLabel);

                            {
                                btn.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        WorkRoutine workRoutine = getTableView().getItems().get(getIndex());
                                        appManager.getDatabaseManager().loadAllRelatedEntities(workRoutine);
                                        if (btnLabel.equalsIgnoreCase("start")) {
                                            Pane timer = viewFactory.getCountDownTimerForRoutine(workRoutine);
                                            appManager.getLandingWindowActivePanes().add(timer);
                                        } else if (btnLabel.equalsIgnoreCase("edit")) {
                                            //TODO: Show EDIT menu and implement edit operations
                                            appManager.editRoutine(workRoutine, viewFactory);
                                        } else if (btnLabel.equalsIgnoreCase("delete")) {
                                            appManager.getDatabaseManager().deleteWorkRoutineRoutine(workRoutine);
                                        } else {
                                            throw new RuntimeException("Button can be start, edit and delete only");
                                        }
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                        return cell;
                    }
                };
        columnToAddBtnTo.setCellFactory(cellFactory);
    }
}