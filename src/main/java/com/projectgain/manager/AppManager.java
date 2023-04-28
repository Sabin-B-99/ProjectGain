package com.projectgain.manager;

import com.projectgain.models.WorkRoutine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class AppManager {
    private CountDownTimerManager countDownTimerManager;
    private WorkRoutineManager workRoutineManager;
    private DatabaseManager databaseManager;

    private static final ObservableList<Pane> landingWindowActivePanes = FXCollections.observableArrayList();

    public ObservableList<Pane> getLandingWindowActivePanes() {
        return landingWindowActivePanes;
    }
    public AppManager() {
        this.workRoutineManager = new WorkRoutineManager();
        this.databaseManager = new DatabaseManager();
    }

    public CountDownTimerManager getCountDownTimerManager() {
        return countDownTimerManager;
    }

    public void setCountDownTimerManager(CountDownTimerManager countDownTimerManager) {
        this.countDownTimerManager = countDownTimerManager;
    }

    public WorkRoutineManager getWorkRoutineManager() {
        return workRoutineManager;
    }

    public void setWorkRoutineManager(WorkRoutineManager workRoutineManager) {
        this.workRoutineManager = workRoutineManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void saveRoutine(WorkRoutine workRoutine){
       databaseManager.saveRoutineFromUser(workRoutine);
    }

    public void showTimerCompletePane(Pane workOutCompletePane) {
        getLandingWindowActivePanes().add(workOutCompletePane);
    }

    public void exitTimer(Pane countDownTimerRootAnchorPane) {
        countDownTimerRootAnchorPane.getChildren().clear();
        getLandingWindowActivePanes().remove(countDownTimerRootAnchorPane);
    }

    public void removeWorkoutCompleteScreen(Pane workoutCompleteScreenRootAnchorPane) {
        getLandingWindowActivePanes().remove(workoutCompleteScreenRootAnchorPane);
    }
}
