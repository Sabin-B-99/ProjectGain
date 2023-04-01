package com.projectgain.controllers;

import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO: Add card copy functionality
 *
 * */
public class WorkCardController extends BaseController implements Initializable {

    private WorkRoutineManager manager;
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

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Color cardColor = manager.generateWorkCardColor();
        cardBGColorSelectColorPicker.setValue(cardColor);
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(cardColor));
    }
    @FXML
    protected void onCardDeleteButtonPressed(){
       manager.deleteWorkCardPane(workCardRootAnchorPane);
    }

    @FXML
    protected void changeCardBackgroundColor(){
        Color updatedColor = cardBGColorSelectColorPicker.getValue();
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(updatedColor));
    }

    @FXML
    protected void onCardCopyButtonClicked(){
        manager.copyWorkCard(workCardRootAnchorPane);
    }
}
