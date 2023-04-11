package com.projectgain.controllers;

import com.projectgain.customcontrol.TimeSpinner;
import com.projectgain.manager.WorkRoutineManager;
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

    private WorkRoutineManager manager;
    private WorkCard cardModel;
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

    public WorkCardController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager, WorkCard cardModel) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
        this.cardModel = cardModel;
        this.timeSpinner = new TimeSpinner();
        this.repSpinner = new Spinner<>();
        this.repControlVBox = new VBox();
        this.timeControlVBox = new VBox();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        performCardInitializationTasks();
        addWorkCardModelListeners();

        int indexOfParentWorkGroup = manager.getIndexOfLastWorkGroupOnWhichAddBtnPressed();
        manager.getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).add(cardModel);
        System.out.println("Debug: WorkCard Added at work group index of " + indexOfParentWorkGroup);
        System.out.println("Size: " + manager.getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).size());
    }
    @FXML
    protected void onCardDeleteButtonPressed(){
        int indexOfParentWorkGroup = manager.findWorkCardParentWorkGroupIndex(workCardRootAnchorPane);
        if(indexOfParentWorkGroup != -1){
            manager.getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).remove(cardModel);
            System.out.println("Debug: WorkCard Added at work group index of " + indexOfParentWorkGroup);
            System.out.println("Size: " + manager.getWorkCardsOfWorkGroupAtIndex(indexOfParentWorkGroup).size());
        }else{
            throw new RuntimeException("failed to delete work card model");
        }
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

    //TODO: Check if this is even necessary
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

    //TODO: Refactor this code
    private void performCardInitializationTasks(){
        Color cardColor = manager.generateWorkCardColor();
        cardBGColorSelectColorPicker.setValue(cardColor);
        workCardRootAnchorPane.setStyle("-fx-background-color: " + viewFactory.getColorHex(cardColor));
        cardModel.setColorHexCode( viewFactory.getColorHex(cardColor));

        SpinnerValueFactory<Integer> repSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        repSpinnerValueFactory.setValue(1);
        repSpinner.setValueFactory(repSpinnerValueFactory);


        timeControlVBox.getChildren().add(new Label("Time"));
        timeControlVBox.getChildren().add(timeSpinner);
        timeControlVBox.setSpacing(3);

        cardTimeToggleButton.setSelected(true);
        timeRepControlHBox.getChildren().add(timeControlVBox);

        cardModel.setWorkType(WorkType.TIMED);

        cardRepToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                repSpinner.getValueFactory().setValue(1);
                if(newVal){
                    timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,3));

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

                }else {
                    timeSpinner.getValueFactory().setValue(LocalTime.of(0,0,0));

                    timeControlVBox.getChildren().clear();
                    timeControlVBox.getChildren().add(new Label("Time"));
                    timeControlVBox.setSpacing(3);
                    timeControlVBox.getChildren().add(timeSpinner);

                    timeRepControlHBox.getChildren().clear();
                    timeRepControlHBox.getChildren().add(timeControlVBox);
                    cardModel.setWorkType(WorkType.TIMED);
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

                System.out.println("DEBUG CODE: repspinner change detected. value: " + repSpinner.getValue());
                cardModel.setReps(repSpinner.getValue());
            }
        });

        timeSpinner.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observableValue, LocalTime localTime, LocalTime t1) {
                System.out.println("DEBUG CODE: timespinner change detected. Value" + timeSpinner.getValue().toString());
                cardModel.setTime(timeSpinner.getValue());
            }
        });

        cardBGColorSelectColorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                cardModel.setColorHexCode( viewFactory.getColorHex(cardBGColorSelectColorPicker.getValue()));
                System.out.println("Debug Code: background color change: . Value " + cardModel.getColorHexCode());
            }
        });
    }
}
