package com.projectgain.controller;

import com.projectgain.FxmlViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
* TODO:
*   Remove Gray background set in Scene Builder directly for card group.
*   Instead put that style separately in CSS file, later.
* */
public class WorkCardGroupController {

    @FXML
    private AnchorPane workCardGroupRootAnchorPane;
    @FXML
    private TextField workCardSetNumTextField;

    @FXML
    private VBox workCardDisplayVBox;

    @FXML
    private ScrollPane workCardDisplayScrollPane;
    @FXML
    private Button addWorkCardButton;

    @FXML
    private Button workGroupDeleteButton;

    @FXML
    protected void onAddNewWorkCardButtonClicked(){
        Pane newWorkCardPane = FxmlViewLoader.getPage("WorkCard");
        workCardDisplayVBox.getChildren().add(newWorkCardPane);
        workCardDisplayVBox.heightProperty().addListener(
                observable -> workCardDisplayScrollPane.setVvalue(1D)
        );
    }

    @FXML
    protected void onWorkGroupDeleteButtonClicked(){
        workCardGroupRootAnchorPane.getChildren().clear();
        workCardGroupRootAnchorPane = null;
    }
}

