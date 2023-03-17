package com.projectgain.controller;

import com.projectgain.FxmlViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WorkCardGroupController {

    @FXML
    TextField workCardSetNumTextField;

    @FXML
    VBox workCardDisplayVBox;
    @FXML
    Button addWorkCardButton;

    @FXML
    protected void onAddNewWorkCardButtonClicked(){
        Pane newWorkCardPane = FxmlViewLoader.getPage("WorkCard");
        workCardDisplayVBox.getChildren().add(newWorkCardPane);
    }
}

