package com.projectgain.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.time.LocalTime;

public class CountDownTimer {
    private StringProperty hrs;
    private StringProperty min;
    private StringProperty sec;

    private StringProperty hrsMinSeparator;
    private StringProperty minSecSeparator;

    private LocalTime initialTime;

    //Animators
    private Timeline timerTimeline;
    private KeyFrame timerKeyFrame;

    private BooleanProperty animationCompleteToggle;

    public CountDownTimer() {
        this.hrs = new SimpleStringProperty();
        this.min = new SimpleStringProperty();
        this.sec = new SimpleStringProperty();
        this.hrsMinSeparator = new SimpleStringProperty();
        this.minSecSeparator = new SimpleStringProperty();
        this.timerTimeline = new Timeline();
        this.animationCompleteToggle = new SimpleBooleanProperty();
    }

    public CountDownTimer(LocalTime initialTime){
        this.hrs = new SimpleStringProperty(Integer.toString(initialTime.getHour()));
        this.min = new SimpleStringProperty(Integer.toString(initialTime.getMinute()));
        this.sec = new SimpleStringProperty(Integer.toString(initialTime.getSecond()));
        this.hrsMinSeparator = new SimpleStringProperty();
        this.minSecSeparator = new SimpleStringProperty();
        this.animationCompleteToggle = new SimpleBooleanProperty();

        this.timerTimeline = new Timeline();
        this.initialTime = initialTime;
    }

    public void startTimer(){
        this.timerKeyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calculateTimeKeyValues();
            }
        });
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.getKeyFrames().clear();
        timerTimeline.getKeyFrames().add(timerKeyFrame);
        timerTimeline.playFromStart();
    }

    public void startTimer(LocalTime time, int reps){
        this.hrs.set("");
        this.min.set("");
        this.sec.set(Integer.toString(reps));
        java.time.Duration durationInSecs = java.time.Duration.ofSeconds(time.getSecond())
                .plusMinutes(time.getMinute())
                .plusHours(time.getHour());
        Duration durationAnimation = Duration.seconds(durationInSecs.getSeconds());
        this.timerKeyFrame = new KeyFrame(durationAnimation, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calculateTimerValuesForRepeatedTypes();
            }
        });
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.getKeyFrames().clear();
        timerTimeline.getKeyFrames().add(timerKeyFrame);
        timerTimeline.playFromStart();
    }

    public void resetTimer(){
        hrs.set(Integer.toString(initialTime.getHour()));
        min.set(Integer.toString(initialTime.getMinute()));
        sec.set(Integer.toString(initialTime.getSecond()));
        timerTimeline.stop();
    }

    public void restartTimer(){
        hrs.set(Integer.toString(initialTime.getHour()));
        min.set(Integer.toString(initialTime.getMinute()));
        sec.set(Integer.toString(initialTime.getSecond()));
        startTimer();
    }

    public void pauseTimer(){
        timerTimeline.pause();
    }

    public String getHrs() {
        return hrs.get();
    }

    public StringProperty hrsProperty() {
        return hrs;
    }

    public void setHrs(String hrs) {
        this.hrs.set(hrs);
    }

    public String getMin() {
        return min.get();
    }

    public StringProperty minProperty() {
        return min;
    }

    public void setMin(String min) {
        this.min.set(min);
    }

    public String getSec() {
        return sec.get();
    }

    public StringProperty secProperty() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec.set(sec);
    }

    public LocalTime getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(LocalTime initialTime) {
        this.initialTime = initialTime;
    }

    private void calculateTimeKeyValues(){
        sec.set(Integer.toString(Integer.parseInt(sec.get()) - 1));

        if(Integer.parseInt(sec.get()) <= 0){
            sec.set(Integer.toString(59));
            min.set(Integer.toString(Integer.parseInt(min.get()) - 1));
        }

        if(Integer.parseInt(min.get()) < 0){
            min.set(Integer.toString(59));
            hrs.set(Integer.toString(Integer.parseInt(hrs.get())-1));
        }

        if(Integer.parseInt(hrs.get()) < 0){
            hrs.set(Integer.toString(0));
            min.set(Integer.toString(0));
            sec.set(Integer.toString(0));
            timerTimeline.stop();
            animationCompleteToggle.set(!animationCompleteToggle.get());
        }
    }

    private void calculateTimerValuesForRepeatedTypes(){
        sec.set(Integer.toString(Integer.parseInt(sec.get()) - 1));
        if(Integer.parseInt(sec.get()) <= 0){
            sec.set(Integer.toString(0));
            timerTimeline.stop();
            animationCompleteToggle.set(!animationCompleteToggle.get());
        }
    }

    public boolean getAnimationCompleteToggle() {
        return animationCompleteToggle.get();
    }

    public BooleanProperty animationCompleteToggleProperty() {
        return animationCompleteToggle;
    }

    public void setAnimationCompleteToggle(boolean animationCompleteToggle) {
        this.animationCompleteToggle.set(animationCompleteToggle);
    }

    public String getHrsMinSeparator() {
        return hrsMinSeparator.get();
    }

    public StringProperty hrsMinSeparatorProperty() {
        return hrsMinSeparator;
    }

    public void setHrsMinSeparator(String hrsMinSeparator) {
        this.hrsMinSeparator.set(hrsMinSeparator);
    }

    public String getMinSecSeparator() {
        return minSecSeparator.get();
    }

    public StringProperty minSecSeparatorProperty() {
        return minSecSeparator;
    }

    public void setMinSecSeparator(String minSecSeparator) {
        this.minSecSeparator.set(minSecSeparator);
    }
}
