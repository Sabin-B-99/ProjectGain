package com.projectgain.controllers;

import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LandingWindowController extends BaseController {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane workRoutineDisplayAnchorPane;

    public LandingWindowController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    @FXML
    protected void onAddButtonClicked() {
        Pane workRoutinePane = viewFactory.getWorkRoutineForm();
        workRoutineDisplayAnchorPane.getChildren().add(workRoutinePane);
    }
}