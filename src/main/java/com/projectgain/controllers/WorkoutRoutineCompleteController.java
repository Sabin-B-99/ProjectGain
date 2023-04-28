package com.projectgain.controllers;

import com.projectgain.manager.AppManager;
import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutRoutineCompleteController extends BaseController implements Initializable {
    private AppManager appManager;

    @FXML
    private Button homeScreenButton;

    @FXML
    private AnchorPane workoutCompleteScreenRootAnchorPane;

    @FXML
    private Label workoutCompleteMsgLabel;

    public WorkoutRoutineCompleteController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workoutCompleteMsgLabel.textProperty().setValue("Well Done!!!! Workout Complete.");
        workoutCompleteScreenRootAnchorPane.setStyle("-fx-background-color: violet");
    }

    @FXML
    protected void onHomeScreenBtnPressed() {
        appManager.removeWorkoutCompleteScreen(workoutCompleteScreenRootAnchorPane);
    }

}
