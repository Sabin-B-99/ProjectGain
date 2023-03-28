package com.projectgain.controllers;

import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/*
 TODO:
  Generate random card background later
  Remove the static yellow background style from scenebuilder
  Add these style into a separate CSS file
**/

public class WorkCardController extends BaseController{

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

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    @FXML
    protected void onCardDeleteButtonPressed(){
        workCardRootAnchorPane.getChildren().clear();
        workCardRootAnchorPane = null;
    }
}
