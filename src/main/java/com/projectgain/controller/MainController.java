package com.projectgain.controller;

import com.projectgain.FxmlViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML
    private Label welcomeText;


    @FXML
    private AnchorPane routineListAnchorPane;
    @FXML
    protected void onAddButtonClicked() {
       Pane card = FxmlViewLoader.getPage("WorkCard");
       routineListAnchorPane.getChildren().add(card);
    }
}