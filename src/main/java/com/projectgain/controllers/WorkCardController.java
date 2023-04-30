package com.projectgain.controllers;

import com.projectgain.customcontrol.TimeSpinner;
import com.projectgain.manager.AppManager;
import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkType;
import com.projectgain.views.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * TODO: Add card copy functionality. Remove Debug Codes: sysouts.
 *
 * */
public class WorkCardController extends BaseController implements Initializable {

    private AppManager appManager;
    private WorkCard cardModel;

    private boolean editing;
    @FXML
    private AnchorPane workCardRootAnchorPane;

    @FXML
    private TextField cardTitleTextField;

    @FXML
    private ToggleButton cardRepToggleButton;

    private Spinner<Integer> repSpinner;
    private VBox repControlVBox;
    @FXML
    private ToggleButton cardTimeToggleButton;
    private TimeSpinner timeSpinner;
    private VBox timeControlVBox;

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

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager, WorkCard cardModel,
                              boolean editing) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
        this.cardModel = cardModel;
        this.timeSpinner = new TimeSpinner();
        this.repSpinner = new Spinner<>();
        this.repControlVBox = new VBox();
        this.timeControlVBox = new VBox();
        this.editing = editing;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        performCardInitializationTasks();
        if(!editing){
            int indexOfParentWorkGroup = appManager.getWorkRoutineManager().getIndexOfLastWorkGroupOnWhichAddBtnPressed();
            appManager.getWorkRoutineManager().getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).add(cardModel);
        }else{
            cardTitleTextField.textProperty().set(cardModel.getTitle());
        }
    }
    @FXML
    protected void onCardDeleteButtonPressed(){
        int indexOfParentWorkGroup = appManager.getWorkRoutineManager().findWorkCardParentWorkGroupIndex(workCardRootAnchorPane);
        if(indexOfParentWorkGroup != -1){
            appManager.getWorkRoutineManager().getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).remove(cardModel);
        }else{
            throw new RuntimeException("failed to delete work card model");
        }
        if(editing){
            appManager.getWorkRoutineManager().getWorkCardIdsRemovedByEditing().add(cardModel.getId());
        }
       appManager.getWorkRoutineManager().deleteWorkCardPane(workCardRootAnchorPane);
    }

    @FXML
    protected void changeCardBackgroundColor(){
        Color updatedColor = cardBGColorSelectColorPicker.getValue();
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(updatedColor));
    }

    @FXML
    protected void onCardCopyButtonClicked(){
        //TODO: Add copy card feature
        appManager.getWorkRoutineManager().copyWorkCard(workCardRootAnchorPane);
    }

    private void performCardInitializationTasks(){
        setupInitialWorkCardUIBasedOnWorkModel();
        setupWorkCardListeners();
    }

    private void setupRepCardUI(){
        timeControlVBox.getChildren().clear();
        timeControlVBox.getChildren().add(new Label("Time for each rep"));
        timeControlVBox.getChildren().add(timeSpinner);
        timeControlVBox.setSpacing(3);
        repControlVBox.getChildren().clear();
        repControlVBox.getChildren().add(new Label("Rep"));
        repControlVBox.getChildren().add(repSpinner);
        repControlVBox.setSpacing(3);

        timeRepControlHBox.getChildren().clear();
        timeRepControlHBox.getChildren().add(repControlVBox);
        timeRepControlHBox.setSpacing(3);
        timeRepControlHBox.getChildren().add(timeControlVBox);
        cardModel.setWorkType(WorkType.REP);
    }
    private void setupTimedCardUI(){
        timeControlVBox.getChildren().clear();
        timeControlVBox.getChildren().add(new Label("Time"));
        timeControlVBox.setSpacing(3);
        timeControlVBox.getChildren().add(timeSpinner);

        timeRepControlHBox.getChildren().clear();
        timeRepControlHBox.getChildren().add(timeControlVBox);
        cardModel.setWorkType(WorkType.TIMED);
    }

    //TODO: Refactor with lambda expressions
    private void setupWorkCardListeners(){
        cardRepToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                repSpinner.getValueFactory().setValue(cardModel.getReps());
                if(newVal){
                    if(!editing){
                        timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,3));
                    }else {
                        timeSpinner.getValueFactory().setValue(cardModel.getTime());
                    }
                    setupRepCardUI();
                }else {
                    if(!editing){
                        timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,0));
                    }else {
                        timeSpinner.getValueFactory().setValue(cardModel.getTime());
                    }
                    setupTimedCardUI();
                }
            }
        });

        cardTitleTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                cardModel.setTitle(cardTitleTextField.getText());
            }
        });

        repSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                cardModel.setReps(repSpinner.getValue());
            }
        });

        timeSpinner.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observableValue, LocalTime localTime, LocalTime t1) {
                cardModel.setTime(timeSpinner.getValue());
            }
        });

        cardBGColorSelectColorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                cardModel.setColorHexCode( viewFactory.getColorHex(cardBGColorSelectColorPicker.getValue()));
            }
        });
    }

    private void setupInitialWorkCardUIBasedOnWorkModel(){
        SpinnerValueFactory<Integer> repSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        repSpinnerValueFactory.setValue(cardModel.getReps());
        repSpinner.setValueFactory(repSpinnerValueFactory);

        if(!editing){
            Color cardColor = appManager.getWorkRoutineManager().generateWorkCardColor();
            cardBGColorSelectColorPicker.setValue(cardColor);
            workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(cardColor));
            cardModel.setColorHexCode( viewFactory.getColorHex(cardColor));
            setupTimedCardUI();
        }else{
            workCardRootAnchorPane.setStyle("-fx-background-color: " + cardModel.getColorHexCode());
            cardBGColorSelectColorPicker.setValue(Color.web(cardModel.getColorHexCode()));
            if(cardModel.getWorkType() == WorkType.TIMED){
                cardTimeToggleButton.setSelected(true);
                repSpinner.getValueFactory().setValue(cardModel.getReps());
                setupTimedCardUI();
            }else{
                cardRepToggleButton.setSelected(true);
                setupRepCardUI();
            }
            timeSpinner.getValueFactory().setValue(cardModel.getTime());
        }
    }
}
