package com.projectgain.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/*
 TODO:
  Generate random card background later
  Remove the static yellow background style from scenebuilder
  Add these style into a separate CSS file
**/

public class WorkCardController {

    @FXML
    private AnchorPane workCardRootAnchorPane;

    @FXML
    private TextField cardTitleTextField;

    @FXML
    private ToggleButton cardSetToggleButton;

    @FXML
    private ToggleButton cardTimeToggleButton;

    @FXML
    private Button cardDeleteButton;

    @FXML
    private Button cardCopyButton;

    @FXML
    private Button cardSaveButton;

    @FXML
    private Label cardBGColorSelectLabel;

    @FXML
    private ColorPicker cardBGColorSelectColorPicker;

    @FXML
    protected void onCardDeleteButtonPressed(){
        workCardRootAnchorPane.getChildren().clear();
        workCardRootAnchorPane = null;
    }
}
