package com.projectgain.controller;

import com.projectgain.FxmlViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private Label welcomeText;


    @FXML
    private AnchorPane workRoutineDisplayAnchorPane;
    @FXML
    protected void onAddButtonClicked() {
        Pane workRoutinePane = FxmlViewLoader.getPage("WorkRoutine");
        workRoutineDisplayAnchorPane.getChildren().add(workRoutinePane);
    }
}