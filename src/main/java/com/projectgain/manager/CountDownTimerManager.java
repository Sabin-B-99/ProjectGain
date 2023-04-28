package com.projectgain.manager;

import com.projectgain.models.*;
import javafx.scene.layout.Pane;

public class CountDownTimerManager {


    private WorkRoutineCountDownTimer routineTimerModel;



    private long durationOfWorkRoutine;
    public CountDownTimerManager(WorkRoutine workRoutine) {
        routineTimerModel = new WorkRoutineCountDownTimer(workRoutine);
    }

    public void startTimer(){
        routineTimerModel.startTimer();
    }

    public void pauseTimer(){
        routineTimerModel.pauseTimer();
    }


    public void resetTimer(){
        routineTimerModel.resetTimer();
    }

    public void switchToNextWorkCard(){
        routineTimerModel.switchToNextWorkCard();
    }

    public void switchToPreviousWorkCard(){
        routineTimerModel.switchToPreviousWorkCard();
    }

    public WorkRoutineCountDownTimer getRoutineTimerModel() {
        return routineTimerModel;
    }

    public void setRoutineTimerModel(WorkRoutineCountDownTimer routineTimerModel) {
        this.routineTimerModel = routineTimerModel;
    }
}
