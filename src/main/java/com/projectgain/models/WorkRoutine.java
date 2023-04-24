package com.projectgain.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WorkRoutine {

    int id;
    private StringProperty title;

    private long workoutDuration;
    private StringProperty workoutDurationInString;

    private List<WorkGroup> workGroupList;

    public WorkRoutine() {
        this.id = -1;
        this.workoutDuration = 0;
        this.title = new SimpleStringProperty();
        this.workoutDurationInString = new SimpleStringProperty();
        this.workGroupList = new ArrayList<>();
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

    public List<WorkGroup> getWorkGroupList() {
        return workGroupList;
    }

    public void setWorkGroupList(List<WorkGroup> workGroupList) {
        this.workGroupList = workGroupList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(long workoutDuration) {
        this.workoutDuration = workoutDuration;
        System.out.println(Duration.ofSeconds(this.workoutDuration).toString());
        this.workoutDurationInString.set(Duration.ofSeconds(workoutDuration).toString()
                .substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase());
    }

    public String getWorkoutDurationInString() {
        return workoutDurationInString.get();
    }

    public StringProperty workoutDurationInStringProperty() {
        return workoutDurationInString;
    }

    public void setWorkoutDurationInString(String workoutDurationInString) {
        this.workoutDurationInString.set(workoutDurationInString);
    }
}
