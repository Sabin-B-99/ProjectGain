package com.projectgain.manager;

import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkCardCountDownTimer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CountDownTimerManager extends BaseManager{


    private WorkCardCountDownTimer model;

    private int currentWorkCardIndex;
    private WorkCard currentWorkCard;
    private WorkCard nextWorkCard;
    private WorkCard previousWorkCard;

    private ArrayList<WorkCard> workCards;

    public CountDownTimerManager(ArrayList<WorkCard> workCards) {
        this.workCards = workCards;
        this.currentWorkCardIndex = 0;
        this.currentWorkCard = workCards.get(currentWorkCardIndex);
        this.model = new WorkCardCountDownTimer(currentWorkCard);
    }

    public void startTimer(){
        model.startTimer();
    }

    public void pauseTimer(){
        model.pauseTimer();
    }


    public void resetTimer(){
        model.resetTimer();
    }

    public void exitTimer(Pane timerPane){
        timerPane.getChildren().clear();
        getLandingWindowActivePanes().remove(timerPane);
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
            this.previousWorkCard = workCards.get(currentWorkCardIndex);
            this.model.changeWorkCard(previousWorkCard);
        }
    }

    public void switchToNextWorkCard(){
        if(currentWorkCardIndex < (workCards.size()-1)) {
            this.currentWorkCardIndex += 1;
            this.nextWorkCard = workCards.get(currentWorkCardIndex);
            this.model.changeWorkCard(nextWorkCard);
        }
    }

}
