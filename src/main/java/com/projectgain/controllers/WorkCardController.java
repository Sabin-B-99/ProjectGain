package com.projectgain.controllers;

import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/*
 TODO:
  Generate random card background later
  Remove the static yellow background style from scenebuilder
  Add these style into a separate CSS file
**/

public class WorkCardController extends BaseController implements Initializable {

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
    private Label cardBGColorSelectLabel;

    @FXML
    private ColorPicker cardBGColorSelectColorPicker;

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    protected void onCardDeleteButtonPressed(){
        try {
            viewFactory.removeLayoutPane(workCardRootAnchorPane);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void changeCardBackgroundColor(){
        Color updatedColor = cardBGColorSelectColorPicker.getValue();
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(updatedColor));
    }

}
