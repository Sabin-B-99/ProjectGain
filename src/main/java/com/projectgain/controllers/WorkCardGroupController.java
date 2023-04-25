package com.projectgain.controllers;

import com.projectgain.manager.AppManager;
import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.models.WorkGroup;
import com.projectgain.views.ViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class WorkCardGroupController extends BaseController implements Initializable{

    private AppManager appManager;
    private WorkGroup workGroupModel;
    private int indexOfCurrentWorkCardGroup;
    @FXML
    private AnchorPane workCardGroupRootAnchorPane;
    @FXML
    private TextField workCardSetNumTextField;

    @FXML
    protected VBox workCardDisplayVBox;

    @FXML
    private ScrollPane workCardDisplayScrollPane;
    @FXML
    private Button addWorkCardButton;

    @FXML
    private Button workGroupDeleteButton;

    public WorkCardGroupController(String fxmlViewName, ViewFactory viewFactory) {
        super(fxmlViewName, viewFactory);
    }

    public WorkCardGroupController(String fxmlViewName, ViewFactory viewFactory, AppManager appManager, WorkGroup workGroupModel) {
        super(fxmlViewName, viewFactory);
        this.appManager = appManager;
        this.workGroupModel = workGroupModel;
    }

    @FXML
    protected void onAddNewWorkCardButtonClicked(){
        appManager.getWorkRoutineManager().setIndexOfLastWorkGroupOnWhichAddBtnPressed(indexOfCurrentWorkCardGroup);
        Pane newWorkCardPane = viewFactory.getWorkCard();
        appManager.getWorkRoutineManager().getWorkCardsList().get(indexOfCurrentWorkCardGroup).add(newWorkCardPane);
        workCardDisplayVBox.heightProperty().addListener(
                observable -> workCardDisplayScrollPane.setVvalue(1D)
        );
    }
    @FXML
    protected void onWorkGroupDeleteButtonClicked(){
        appManager.getWorkRoutineManager().deleteWorkGroupPane(workCardGroupRootAnchorPane);
        System.out.println("Number of workgroup models: " + appManager.getWorkRoutineManager().getWorkRoutineModel().getWorkGroupList().size());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appManager.getWorkRoutineManager().getWorkRoutineWorkGroups().add(workGroupModel);
        indexOfCurrentWorkCardGroup = appManager.getWorkRoutineManager().getTotalWorkCardGroup() - 1;
        workGroupModel.setIndexInCurrentRoutine(indexOfCurrentWorkCardGroup);
        appManager.getWorkRoutineManager().getWorkCardsList().get(indexOfCurrentWorkCardGroup).addListener(new ListChangeListener<Pane>() {
            @Override
           public void onChanged(Change<? extends Pane> change) {
               workCardDisplayVBox.getChildren().clear();
               workCardDisplayVBox.getChildren().addAll(appManager.getWorkRoutineManager().getWorkCardsList().get(indexOfCurrentWorkCardGroup));
            }
        });

        workGroupModel.setIndexInCurrentRoutine(indexOfCurrentWorkCardGroup);
        workCardSetNumTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
               workGroupModel.setSets(Integer.parseInt(workCardSetNumTextField.getText()));
            }
        });
        onAddNewWorkCardButtonClicked();
    }
}

