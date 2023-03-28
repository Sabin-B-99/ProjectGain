package com.projectgain.views;

import com.projectgain.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class ViewFactory{


    public void showLandingStage(){
        BaseController controller = new MainController("Main.fxml", this);
        initializeStage(controller);
    }

    public Pane getWorkCard(){
        BaseController controller = new WorkCardController("WorkCard.fxml", this);
        return getLayoutPane(controller);
    }

    public Pane getWorkCardGroup(){
        BaseController controller  = new WorkCardGroupController("WorkCardGroup.fxml", this);
        return getLayoutPane(controller);
    }

    public Pane getWorkRoutineForm(){
        BaseController controller = new WorkRoutineController("WorkRoutine.fxml", this);
        return getLayoutPane(controller);
    }
    private void initializeStage(BaseController controller){
        Parent parent = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + controller.getFxmlViewName()));
            fxmlLoader.setController(controller);
            parent = fxmlLoader.load();
        }catch (Exception e){
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public Pane getLayoutPane(BaseController controller) {
        Pane view = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + controller.getFxmlViewName()));
            fxmlLoader.setController(controller);
            view = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
