package com.projectgain.controllers;

import com.projectgain.manager.AppManager;
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

    private AppManager appManager;

    private WorkRoutine workRoutineModel;

    private boolean editing;

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

    public WorkRoutineController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager,
                                 WorkRoutine workRoutineModel, boolean editing) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
        this.workRoutineModel = workRoutineModel;
        this.editing = editing;
    }

    @FXML
    protected void onAddNewWorkGroupButtonClicked(){

        appManager.getWorkRoutineManager().getWorkCardsList().put(appManager.getWorkRoutineManager().getTotalWorkCardGroup(), FXCollections.observableArrayList());
        appManager.getWorkRoutineManager().setTotalWorkCardGroup(appManager.getWorkRoutineManager().getTotalWorkCardGroup() + 1);

        Pane workCardGroup = viewFactory.getWorkCardGroup();
        appManager.getWorkRoutineManager().getWorkGroupsPaneList().add(workCardGroup);

        workGroupDisplayVBox.heightProperty().addListener(
                observable -> workGroupDisplayScrollPane.setVvalue(1D)
        );
    }
    @FXML
    protected void onRoutineSaveClicked(){
        appManager.getWorkRoutineManager().performRoutineSaveOperations(appManager);
        appManager.getWorkRoutineManager().deleteRoutinePane(workRoutineRootVBox, appManager);
        if(editing){
            appManager.getWorkRoutineManager().checkDeletedEntitiesAfterEditAndUpdateDB(appManager);
            appManager.getWorkRoutineManager().updateAvailableWorkoutTableViewAfterEdit(workRoutineModel, appManager);
        }else{
            appManager.getWorkRoutineManager().updateAvailableWorkoutTableViewAfterSave(workRoutineModel,appManager);
        }
    }

    @FXML
    protected void onQuitWorkRoutineButtonClicked(){
        appManager.getWorkRoutineManager().deleteRoutinePane(workRoutineRootVBox, appManager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        appManager.getWorkRoutineManager().setWorkRoutineModel(workRoutineModel);
        appManager.getWorkRoutineManager().getWorkGroupsPaneList().addListener(new ListChangeListener<Pane>() {
            @Override
            public void onChanged(Change<? extends Pane> change) {
                workGroupDisplayVBox.getChildren().clear();
                workGroupDisplayVBox.getChildren().addAll(appManager.getWorkRoutineManager().getWorkGroupsPaneList());
            }
        });

        routineTitleTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                workRoutineModel.setTitle(routineTitleTextField.getText());
            }
        });
        if(!editing){
            onAddNewWorkGroupButtonClicked();
        }else{
            routineTitleTextField.setText(workRoutineModel.getTitle());
        }
    }
}
