package com.projectgain.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkCardCountDownTimer extends CountDownTimer{

    private StringProperty title;
    private StringProperty color;
    private StringProperty remainingSets;
    public WorkCardCountDownTimer(WorkCard workCard) {
        super(workCard.getTime());
        this.title = new SimpleStringProperty(workCard.getTitle());
        this.color = new SimpleStringProperty(workCard.getColorHexCode());
        this.remainingSets = new SimpleStringProperty();
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

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public void changeWorkCard(WorkCard workCard){
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
}
