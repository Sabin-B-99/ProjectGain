package com.projectgain.controllers;

import com.projectgain.manager.CountDownTimerManager;
import com.projectgain.views.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class CountDownTimerController extends BaseController implements Initializable {

    private CountDownTimerManager manager;

    @FXML
    private AnchorPane countDownTimerRootAnchorPane;

    @FXML
    private Label workTitleLabel;

    @FXML
    private Label numSetRemainingLabel;

    @FXML
    private Label hrsLabel;

    @FXML
    private Label sepColonHrsMinLabel;

    @FXML
    private Label minLabel;

    @FXML
    private Label sepColonMinSecLabel;

    @FXML
    private Label secLabel;

    @FXML
    private Button prevTimerButton;

    @FXML
    private Button nextTimerButton;

    @FXML
    private Button startTimerButton;

    @FXML
    private Button pauseTimerButton;

    @FXML
    private Button resetTimerButton;

    @FXML
    private Button exitTimerButton;

    public CountDownTimerController(String fxmlViewName, ViewFactory viewFactory, CountDownTimerManager manager) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hrsLabel.textProperty().bind(manager.getModel().hrsProperty());
        minLabel.textProperty().bind(manager.getModel().minProperty());
        secLabel.textProperty().bind(manager.getModel().secProperty());
        workTitleLabel.textProperty().bind(manager.getModel().titleProperty());

        countDownTimerRootAnchorPane.setStyle("-fx-background-color: " + manager.getModel().getColor());
        manager.getModel().colorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                countDownTimerRootAnchorPane.setStyle("-fx-background-color: " + manager.getModel().getColor());
            }
        });
    }

    @FXML
    protected void onTimerStartButtonClicked(){
        manager.startTimer();
    }

    @FXML
    protected void onTimerPauseButtonClicked(){
        manager.pauseTimer();
    }

    @FXML
    protected void onTimerResetButtonClicked(){
        manager.resetTimer();
    }

    @FXML
    protected void onTimerExitButtonClicked(){
        manager.exitTimer(countDownTimerRootAnchorPane);
    }

    @FXML
    protected void onTimerPrevButtonClicked(){
        manager.switchToPreviousWorkCard();
    }

    @FXML
    protected void onTimerNextButtonClicked(){
        manager.switchToNextWorkCard();
    }

}
