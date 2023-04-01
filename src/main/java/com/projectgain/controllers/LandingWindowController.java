package com.projectgain.controllers;

import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.views.ViewFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingWindowController extends BaseController implements Initializable {

    private WorkRoutineManager manager;

    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane workRoutineDisplayAnchorPane;

    public LandingWindowController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    public LandingWindowController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
    }

    @FXML
    protected void onAddButtonClicked() {
        Pane workRoutinePane = viewFactory.getWorkRoutineForm();
        manager.getLandingWindowActivePanes().add(workRoutinePane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        manager.getLandingWindowActivePanes().addListener(new ListChangeListener<Pane>() {
            @Override
            public void onChanged(Change<? extends Pane> change) {
                workRoutineDisplayAnchorPane.getChildren().clear();
                workRoutineDisplayAnchorPane.getChildren().addAll(manager.getLandingWindowActivePanes());
            }
        });
    }
}