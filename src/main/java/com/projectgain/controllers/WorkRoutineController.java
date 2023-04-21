package com.projectgain.controllers;

import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.models.WorkRoutine;
import com.projectgain.views.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class WorkRoutineController extends BaseController implements Initializable{

    private WorkRoutineManager manager;

    private WorkRoutine workRoutineModel;

    @FXML
    private VBox workRoutineRootVBox;

    @FXML
    private TextField routineTitleTextField;
    @FXML
    private VBox workGroupDisplayVBox;
    @FXML
    private ScrollPane workGroupDisplayScrollPane;

    @FXML
    private Button addNewWorkGroupButton;

    public WorkRoutineController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    public WorkRoutineController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager, WorkRoutine workRoutineModel) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
        this.workRoutineModel = workRoutineModel;
    }

    @FXML
    protected void onAddNewWorkGroupButtonClicked(){

        manager.getWorkCardsList().put(manager.getTotalWorkCardGroup(), FXCollections.observableArrayList());
        manager.setTotalWorkCardGroup(manager.getTotalWorkCardGroup() + 1);

        Pane workCardGroup = viewFactory.getWorkCardGroup();
        manager.getWorkGroupsPaneList().add(workCardGroup);

        workGroupDisplayVBox.heightProperty().addListener(
                observable -> workGroupDisplayScrollPane.setVvalue(1D)
        );
    }
    @FXML
    protected void onRoutineSaveClicked(){
        manager.performRoutineSaveOperations();
        manager.deleteRoutinePane(workRoutineRootVBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        manager.setWorkRoutineModel(workRoutineModel);
        manager.getWorkGroupsPaneList().addListener(new ListChangeListener<Pane>() {
            @Override
            public void onChanged(Change<? extends Pane> change) {
                workGroupDisplayVBox.getChildren().clear();
                workGroupDisplayVBox.getChildren().addAll(manager.getWorkGroupsPaneList());
            }
        });

        routineTitleTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                workRoutineModel.setTitle(routineTitleTextField.getText());
            }
        });
    }
}
