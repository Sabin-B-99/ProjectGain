package com.projectgain.controllers;

import com.projectgain.manager.WorkRoutineManager;
import com.projectgain.views.ViewFactory;
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

    private WorkRoutineManager manager;
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

    public WorkCardGroupController(String fxmlViewName, ViewFactory viewFactory, WorkRoutineManager manager) {
        super(fxmlViewName, viewFactory);
        this.manager = manager;
    }

    @FXML
    protected void onAddNewWorkCardButtonClicked(){
        Pane newWorkCardPane = viewFactory.getWorkCard();
        manager.getWorkCardsList().get(indexOfCurrentWorkCardGroup).add(newWorkCardPane);

        workCardDisplayVBox.heightProperty().addListener(
                observable -> workCardDisplayScrollPane.setVvalue(1D)
        );
    }
    @FXML
    protected void onWorkGroupDeleteButtonClicked(){
        manager.deleteWorkGroupPane(workCardGroupRootAnchorPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        indexOfCurrentWorkCardGroup = manager.getTotalWorkCardGroup() - 1;
        manager.getWorkCardsList().get(indexOfCurrentWorkCardGroup).addListener(new ListChangeListener<Pane>() {
            @Override
           public void onChanged(Change<? extends Pane> change) {
               workCardDisplayVBox.getChildren().clear();
               workCardDisplayVBox.getChildren().addAll(manager.getWorkCardsList().get(indexOfCurrentWorkCardGroup));
            }
        });
    }
}

