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

    private BooleanProperty changeWorkCard;

    public WorkRoutineCountDownTimer(WorkRoutine currentRoutine) {
        this.currentWorkGroupIndex = 0;
        this.currentWorkCardIndex = 0;
        this.currentRoutine = currentRoutine;
        this.currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkGroupIndex);
        this.currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
        this.cardCountDownTimer = new WorkCardCountDownTimer(currentWorkCard);

        this.changeWorkCard = new SimpleBooleanProperty();
        this.changeWorkCard.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                changeToNextCardAfterCompletion();
            }
        });
        this.changeWorkCard.bind(cardCountDownTimer.animationCompleteToggleProperty());
    }

    public void startTimer(){
        cardCountDownTimer.startTimer();
    }

    public void pauseTimer() {
        cardCountDownTimer.pauseTimer();
    }

    public void resetTimer(){
        cardCountDownTimer.resetTimer();
    }

    private void changeToNextCardAfterCompletion(){
        if(switchToNextWorkCard()){
            cardCountDownTimer.startTimer();
        }else{
            //TODO: ADD CODE FOR WORK TO BE DONE AFTER COMPLETION
            System.out.println("Workout Complete!!!");
        }
    }
    public boolean switchToNextWorkCard(){
        boolean switchComplete = false;
        if(currentWorkGroupHasAnotherCard()){
            currentWorkCardIndex++;
            currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
            cardCountDownTimer.changeWorkCard(currentWorkCard);
            switchComplete  = true;
        }else{
            if(currentRoutineHasAnotherWorkGroup()){
                currentWorkGroupIndex++;
                currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkCardIndex);
                currentWorkCardIndex = 0;
                currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
                cardCountDownTimer.changeWorkCard(currentWorkCard);
                switchComplete = true;
            }
        }
        return switchComplete;
    }

    public boolean switchToPreviousWorkCard(){
        boolean switchComplete = false;
        if(currentWorkCardIndex > 0){
            currentWorkCardIndex--;
            currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
            cardCountDownTimer.changeWorkCard(currentWorkCard);
            switchComplete = true;
        }else{
            if(currentWorkGroupIndex > 0){
                currentWorkGroupIndex--;
                currentWorkGroup = currentRoutine.getWorkGroupList().get(currentWorkGroupIndex);
                currentWorkCardIndex = currentWorkGroup.getWorkCardList().size() - 1;
                currentWorkCard = currentWorkGroup.getWorkCardList().get(currentWorkCardIndex);
                cardCountDownTimer.changeWorkCard(currentWorkCard);
                switchComplete = true;
            }
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

}
