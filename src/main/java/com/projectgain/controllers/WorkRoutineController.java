package com.projectgain.controllers;

import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.views.ViewFactory;
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

    private WorkRoutineManager manger;

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

    public WorkRoutineController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manger) {
        super(fxmlViewName, viewFactory);
        this.manger = manger;
    }

    @FXML
    protected void onAddNewWorkGroupButtonClicked(){

        manger.getWorkCardsList().put(manger.getTotalWorkCardGroup(), FXCollections.observableArrayList());
        manger.setTotalWorkCardGroup(manger.getTotalWorkCardGroup() + 1);

        Pane workCardGroup = viewFactory.getWorkCardGroup();
        manger.getWorkGroupsPaneList().add(workCardGroup);

        workGroupDisplayVBox.heightProperty().addListener(
                observable -> workGroupDisplayScrollPane.setVvalue(1D)
        );
    }
    @FXML
    protected void onRoutineSaveClicked(){
        System.out.println("TODO: Saving routine. Perform save operations");
        manger.deleteRoutinePane(workRoutineRootVBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manger.getWorkGroupsPaneList().addListener(new ListChangeListener<Pane>() {
            @Override
            public void onChanged(Change<? extends Pane> change) {
                workGroupDisplayVBox.getChildren().clear();
                workGroupDisplayVBox.getChildren().addAll(manger.getWorkGroupsPaneList());
            }
        });
    }
}
