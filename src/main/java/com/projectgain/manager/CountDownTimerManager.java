package com.projectgain.manager;

import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkCardCountDownTimer;
import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;
import javafx.scene.layout.Pane;

import java.util.List;

public class CountDownTimerManager {


    private WorkCardCountDownTimer model;

    private int currentWorkCardIndex;
    private int currentWorkGroupIndex;

    private WorkCard currentWorkCard;
    private WorkCard nextWorkCard;
    private WorkCard previousWorkCard;

    private List<WorkGroup> workGroups;

    private int totalTimeTimerShouldRun;
    public CountDownTimerManager(WorkRoutine workRoutine) {
        this.workGroups = workRoutine.getWorkGroupList();
        this.currentWorkCardIndex = 0;
        this.currentWorkGroupIndex = 0;
        this.currentWorkCard = workGroups.get(currentWorkGroupIndex).getWorkCardList().get(currentWorkCardIndex);
        this.model = new WorkCardCountDownTimer(currentWorkCard);
    }

    public void startTimer(){
        for (WorkGroup wg:
             workGroups) {
            totalTimeTimerShouldRun += (wg.getSets() * wg.getWorkCardList().size());
        }
       model.startTimer(totalTimeTimerShouldRun);
    }

    public void pauseTimer(){
        model.pauseTimer();
    }


    public void resetTimer(){
        model.resetTimer();
    }

    public void exitTimer(Pane timerPane, AppManager appManager){
        timerPane.getChildren().clear();
        appManager.getLandingWindowActivePanes().remove(timerPane);
    }

    public WorkCardCountDownTimer getModel() {
        return model;
    }

    public void setModel(WorkCardCountDownTimer model) {
        this.model = model;
    }

    public void switchToPreviousWorkCard(){
        if(currentWorkCardIndex > 0){
            this.currentWorkCardIndex -= 1;
            this.previousWorkCard = workGroups.get(currentWorkGroupIndex).getWorkCardList().get(currentWorkCardIndex);
            this.model.changeWorkCard(previousWorkCard);
        }
    }

    public void switchToNextWorkCard(){
        if(currentWorkCardIndex < (workGroups.get(currentWorkGroupIndex).getWorkCardList().size()-1)) {
            this.currentWorkCardIndex += 1;
            this.nextWorkCard = workGroups.get(currentWorkGroupIndex).getWorkCardList().get(currentWorkCardIndex);
            this.model.changeWorkCard(nextWorkCard);
        }
    }

}
