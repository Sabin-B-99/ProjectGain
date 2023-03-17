package com.projectgain.controller;

import com.projectgain.FxmlViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WorkRoutineController {
    @FXML
    private VBox workGroupDisplayVBox;

    @FXML
    private Button addNewWorkGroupButton;

    @FXML
    protected void onAddNewWorkGroupButtonClicked(){
        Pane workCardGroup = FxmlViewLoader.getPage("WorkCardGroup");
        workGroupDisplayVBox.getChildren().add(workCardGroup);
    }
}
