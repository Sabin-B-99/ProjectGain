package com.projectgain.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class WorkRoutineCountDownTimer {
    private WorkRoutine currentRoutine;
    private WorkCardCountDownTimer cardCountDownTimer;
    private WorkGroup currentWorkGroup;
    private WorkCard currentWorkCard;


    private int currentWorkCardIndex;
    private int currentWorkGroupIndex;

    private int remainingSets;
    private BooleanProperty changeWorkCard;

    private BooleanProperty workOutComplete;

    public WorkRoutineCountDownTimer(WorkRoutine currentRoutine) {
        this.currentWorkGroupIndex = 0;
        this.currentWorkCardIndex = 0;
        this.currentRoutine = currentRoutine;
        this.currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkGroupIndex);
        this.currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
        this.cardCountDownTimer = new WorkCardCountDownTimer(currentWorkCard);
        this.remainingSets = currentWorkGroup.getSets();
        this.cardCountDownTimer.setRemainingSets(Integer.toString(remainingSets));
        this.changeWorkCard = new SimpleBooleanProperty();
        this.workOutComplete = new SimpleBooleanProperty();

        this.changeWorkCard.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                changeToNextCardAfterCompletion();
            }
        });
        this.changeWorkCard.bind(cardCountDownTimer.animationCompleteToggleProperty());
    }

    public void startTimer(){
        cardCountDownTimer.startTimer(currentWorkCard.getTitle());
    }

    public void pauseTimer() {
        cardCountDownTimer.pauseTimer();
    }

    public void resetTimer(){
        cardCountDownTimer.resetTimer();
    }

    private void changeToNextCardAfterCompletion(){
        if(switchToNextWorkCard()){
            cardCountDownTimer.startTimer(currentWorkCard.getTitle());
        }else{
            this.workOutComplete.set(true);
        }
    }

    public boolean switchToNextWorkCard(){
        boolean switchComplete = false;
        if(currentWorkGroupHasAnotherCard()){
            currentWorkCardIndex += 1;
            currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
            cardCountDownTimer.changeWorkCard(currentWorkCard);
            switchComplete = true;
        }else{
            if(remainingSets > 0){
                remainingSets -= 1;
            }
            if(remainingSets < 1){
                if(currentRoutineHasAnotherWorkGroup()){
                    currentWorkGroupIndex += 1;
                    currentWorkCardIndex = 0;
                    currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkGroupIndex);
                    remainingSets = currentWorkGroup.getSets();
                    currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
                    cardCountDownTimer.changeWorkCard(currentWorkCard);
                    cardCountDownTimer.setRemainingSets(Integer.toString(remainingSets));
                    switchComplete = true;
                }
            }else{
                currentWorkCardIndex = 0;
                currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
                cardCountDownTimer.changeWorkCard(currentWorkCard);
                cardCountDownTimer.setRemainingSets(Integer.toString(remainingSets));
                switchComplete = true;
            }
        }
        return switchComplete;
    }

    public boolean switchToPreviousWorkCard(){
        boolean switchComplete = false;
        if(currentWorkCardIndex > 0){
            currentWorkCardIndex -= 1;
            currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
            cardCountDownTimer.changeWorkCard(currentWorkCard);
            switchComplete = true;
        }else{
            if(currentWorkGroupIndex > 0){
                currentWorkGroupIndex -= 1;
                currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkGroupIndex);
            }
            if(remainingSets < currentWorkGroup.getSets()){
                currentWorkCardIndex = currentWorkGroup.getWorkCardList().size() - 1;
                currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
                remainingSets += 1;
                cardCountDownTimer.changeWorkCard(currentWorkCard);
                switchComplete = true;
            }
            cardCountDownTimer.setRemainingSets(Integer.toString(remainingSets));
        }
        return switchComplete;
    }

    private boolean currentWorkGroupHasAnotherCard(){
        return currentWorkCardIndex < currentWorkGroup.getWorkCardList().size() - 1;
    }

    private boolean currentRoutineHasAnotherWorkGroup(){
        return currentWorkGroupIndex < currentRoutine.getWorkGroupList().size() - 1;
    }

    public WorkCardCountDownTimer getCardCountDownTimer() {
        return cardCountDownTimer;
    }

    public boolean isWorkOutComplete() {
        return workOutComplete.get();
    }

    public BooleanProperty workOutCompleteProperty() {
        return workOutComplete;
    }

    public void setWorkOutComplete(boolean workOutComplete) {
        this.workOutComplete.set(workOutComplete);
    }
}
