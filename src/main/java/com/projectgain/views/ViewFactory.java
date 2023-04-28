package com.projectgain.views;

import com.projectgain.controllers.*;
import com.projectgain.manager.AppManager;
import com.projectgain.manager.CountDownTimerManager;
import com.projectgain.models.AvailableWorkouts;
import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ViewFactory{

    private AppManager appManager;

    public ViewFactory(AppManager appManager) {
        this.appManager = appManager;
    }

    public void showLandingStage(){
        BaseController controller = new LandingWindowController("LandingWindow.fxml", this,
                appManager);
        initializeStage(controller);
    }

    public Pane getWorkCard(){
        BaseController controller = new WorkCardController("WorkCard.fxml", this,
                appManager, new WorkCard());
        return getLayoutPane(controller);
    }

    public Pane getWorkCardGroup(){
        BaseController controller  = new WorkCardGroupController("WorkCardGroup.fxml", this,
                appManager, new WorkGroup());
        return getLayoutPane(controller);
    }

    public Pane getWorkRoutineForm(){
        BaseController controller = new WorkRoutineController("WorkRoutine.fxml", this,
                appManager, new WorkRoutine());
        return getLayoutPane(controller);
    }

    public Pane getCountDownTimerForRoutine(WorkRoutine workRoutine){
        BaseController controller = new CountDownTimerController("CountDownTimer.fxml", this,
                appManager);
        appManager.setCountDownTimerManager(new CountDownTimerManager(workRoutine));
        return getLayoutPane(controller);
    }

    public Pane getAvailableWorkoutRoutinesPane(){
        BaseController controller = new AvailableWorkoutsController("AvailableWorkouts.fxml", this
                ,appManager, new AvailableWorkouts());
        return getLayoutPane(controller);
    }

    public Pane getWorkoutCompleteScreenPane(){
        BaseController controller = new WorkoutRoutineCompleteController("WorkoutCompleteScreen.fxml", this
                ,appManager);
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

    //from stackoverflow
    public String getColorHex(Color color){
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
