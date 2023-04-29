package com.projectgain.manager;

import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;
import com.projectgain.views.ViewFactory;
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

    public void editRoutine(WorkRoutine routine, ViewFactory viewFactory){
        Pane workRoutinePane = viewFactory.getWorkRoutineForm(routine);
        List<WorkGroup> workGroups = routine.getWorkGroupList();
        List<WorkCard> workCards = null;
        landingWindowActivePanes.add(workRoutinePane);
        for (WorkGroup wg:
             workGroups) {
            workRoutineManager.getWorkCardsList().put(wg.getIndexInCurrentRoutine(), FXCollections.observableArrayList());
            Pane workGroupPane = viewFactory.getWorkCardGroup(wg);
            workRoutineManager.getWorkGroupsPaneList().add(workGroupPane);
            workCards = wg.getWorkCardList();
            for (WorkCard wc:
                 workCards) {
                Pane workCardPane = viewFactory.getWorkCard(wc);
                workRoutineManager.getWorkCardsList().get(wg.getIndexInCurrentRoutine()).add(workCardPane);
            }
        }
    }
}
