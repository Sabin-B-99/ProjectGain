package com.projectgain.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkCardCountDownTimer extends CountDownTimer {

    private WorkCard workCard;
    private StringProperty title;
    private StringProperty color;
    private StringProperty remainingSets;


    public WorkCardCountDownTimer(WorkCard workCard) {
        super(workCard.getTime());
        this.workCard = workCard;
        this.title = new SimpleStringProperty(workCard.getTitle());
        this.color = new SimpleStringProperty(workCard.getColorHexCode());
        this.remainingSets = new SimpleStringProperty();
        if(workCard.getWorkType() == WorkType.TIMED){
            this.hrsMinSeparatorProperty().set(":");
            this.minSecSeparatorProperty().set(":");
        }else {
            this.hrsMinSeparatorProperty().set("");
            this.minSecSeparatorProperty().set("");
        }
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getColor() {
        return color.get();
    }

    @Override
    public void startTimer() {
        if (workCard.getWorkType() == WorkType.TIMED) {
            this.hrsMinSeparatorProperty().set(":");
            this.minSecSeparatorProperty().set(":");
            super.startTimer();
        } else {
            this.hrsMinSeparatorProperty().set("");
            this.minSecSeparatorProperty().set("");
            super.startTimer(workCard.getTime(), workCard.getReps());
        }
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public void changeWorkCard(WorkCard workCard){
        setWorkCard(workCard);
        setInitialTime(workCard.getTime());
        resetTimer();

        this.title.set(workCard.getTitle());
        this.color.set(workCard.getColorHexCode());
    }

    public String getRemainingSets() {
        return remainingSets.get();
    }

    public StringProperty remainingSetsProperty() {
        return remainingSets;
    }

    public void setRemainingSets(String remainingSets) {
        this.remainingSets.set(remainingSets);
    }

    public WorkCard getWorkCard() {
        return workCard;
    }

    public void setWorkCard(WorkCard workCard) {
        this.workCard = workCard;
    }
}