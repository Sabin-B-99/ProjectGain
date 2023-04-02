package com.projectgain.models;

import javafx.beans.property.*;

import java.time.LocalTime;

public class WorkCard {
    private StringProperty title;
    private StringProperty colorHexCode;

    private WorkType workType;
    private LocalTime time;
    private IntegerProperty reps;
    public WorkCard() {
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
}
