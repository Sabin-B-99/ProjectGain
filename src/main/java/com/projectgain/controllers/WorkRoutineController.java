package com.projectgain.controllers;

import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WorkRoutineController extends BaseController {
    @FXML
    private VBox workGroupDisplayVBox;

    @FXML
    private ScrollPane workGroupDisplayScrollPane;

    @FXML
    private Button addNewWorkGroupButton;

    public WorkRoutineController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    @FXML
    protected void onAddNewWorkGroupButtonClicked(){
        Pane workCardGroup = viewFactory.getWorkCardGroup();
        workGroupDisplayVBox.getChildren().add(workCardGroup);
        workGroupDisplayVBox.heightProperty().addListener(
                observable -> workGroupDisplayScrollPane.setVvalue(1D)
        );
    }
}
