package com.projectgain.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/*
 TODO:
  Generate random card background later
  Remove the static yellow background style from scenebuilder
  Add these style into a separate CSS file
**/

public class WorkCardController {

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

}
