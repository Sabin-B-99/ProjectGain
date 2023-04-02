package com.projectgain.controllers;

import com.projectgain.customcontrol.TimeSpinner;
import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.models.WorkCard;
import com.projectgain.views.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * TODO: Add card copy functionality
 *
 * */
public class WorkCardController extends BaseController implements Initializable {

    private WorkRoutineManager manager;
    private WorkCard cardModel;
    @FXML
    private AnchorPane workCardRootAnchorPane;

    @FXML
    private TextField cardTitleTextField;

    @FXML
    private ToggleButton cardRepToggleButton;

    private Spinner<Integer> repSpinner;
    @FXML
    private ToggleButton cardTimeToggleButton;
    private TimeSpinner timeSpinner;

    @FXML
    private HBox timeRepControlHBox;

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

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager, WorkCard cardModel) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
        this.cardModel = cardModel;
        this.timeSpinner = new TimeSpinner();
        this.repSpinner = new Spinner<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        performCardInitializationTasks();
        addWorkCardModelListeners();
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
        //debug code //TODO: Delete these codes later
        String color = viewFactory.getColorHex(manager.generateWorkCardColor());
        cardModel.setTitle(color);
    }

    private void addWorkCardModelListeners(){
        cardModel.titleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                cardTitleTextField.setText(cardModel.getTitle());
                System.out.println("loop: model");
            }
        });

        cardModel.colorHexCodeProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                cardBGColorSelectColorPicker.setValue(Color.web(cardModel.getColorHexCode()));
                workCardRootAnchorPane.setStyle("-fx-background-color: " + cardModel.getColorHexCode());
            }
        });
    }

    private void performCardInitializationTasks(){
        Color cardColor = manager.generateWorkCardColor();
        cardBGColorSelectColorPicker.setValue(cardColor);
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(cardColor));

        SpinnerValueFactory<Integer> repSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        repSpinnerValueFactory.setValue(1);
        repSpinner.setValueFactory(repSpinnerValueFactory);

        cardTimeToggleButton.setSelected(true);
        timeRepControlHBox.getChildren().add(timeSpinner);

        cardRepToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                repSpinner.getValueFactory().setValue(1);
                if(newVal){
                    timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,3));
                    timeRepControlHBox.getChildren().clear();
                    timeRepControlHBox.getChildren().add(repSpinner);
                    timeRepControlHBox.setSpacing(3);
                    timeRepControlHBox.getChildren().add(timeSpinner);
                }else {
                    timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,0));
                    timeRepControlHBox.getChildren().clear();
                    timeRepControlHBox.getChildren().add(timeSpinner);
                }
            }
        });
    }
}
