package com.projectgain.models;

import javafx.beans.property.*;

import java.time.LocalTime;

public class WorkCard {
    private int id;
    private StringProperty title;
    private StringProperty colorHexCode;

    private WorkType workType;
    private LocalTime time;
    private IntegerProperty reps;
    public WorkCard() {
        this.id = -1;
        this.title = new SimpleStringProperty();
        this.colorHexCode = new SimpleStringProperty();
        this.reps = new SimpleIntegerProperty();
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

    public String getColorHexCode() {
        return colorHexCode.get();
    }

    public StringProperty colorHexCodeProperty() {
        return colorHexCode;
    }

    public void setColorHexCode(String colorHexCode) {
        this.colorHexCode.set(colorHexCode);
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getReps() {
        return reps.get();
    }

    public IntegerProperty repsProperty() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps.set(reps);
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
