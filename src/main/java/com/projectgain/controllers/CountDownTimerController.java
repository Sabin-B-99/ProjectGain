package com.projectgain.controllers;

import com.projectgain.manager.AppManager;
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

    private AppManager appManager;

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

    public CountDownTimerController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hrsLabel.textProperty().bind(appManager.getCountDownTimerManager().getModel().hrsProperty());
        minLabel.textProperty().bind(appManager.getCountDownTimerManager().getModel().minProperty());
        secLabel.textProperty().bind(appManager.getCountDownTimerManager().getModel().secProperty());
        workTitleLabel.textProperty().bind(appManager.getCountDownTimerManager().getModel().titleProperty());
        numSetRemainingLabel.textProperty().bind(appManager.getCountDownTimerManager().getModel().remainingSetsProperty());

        countDownTimerRootAnchorPane.setStyle("-fx-background-color: " + appManager.getCountDownTimerManager().getModel().getColor());
        appManager.getCountDownTimerManager().getModel().colorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                countDownTimerRootAnchorPane.setStyle("-fx-background-color: " + appManager.getCountDownTimerManager().getModel().getColor());
            }
        });
    }

    @FXML
    protected void onTimerStartButtonClicked(){
        appManager.getCountDownTimerManager().startTimer();
    }

    @FXML
    protected void onTimerPauseButtonClicked(){
        appManager.getCountDownTimerManager().pauseTimer();
    }

    @FXML
    protected void onTimerResetButtonClicked(){
        appManager.getCountDownTimerManager().resetTimer();
    }

    @FXML
    protected void onTimerExitButtonClicked(){
        appManager.getCountDownTimerManager().exitTimer(countDownTimerRootAnchorPane, appManager);
    }

    @FXML
    protected void onTimerPrevButtonClicked(){
        appManager.getCountDownTimerManager().switchToPreviousWorkCard();
    }

    @FXML
    protected void onTimerNextButtonClicked(){
        appManager.getCountDownTimerManager().switchToNextWorkCard();
    }

}
