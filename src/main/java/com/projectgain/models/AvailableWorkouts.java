package com.projectgain.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AvailableWorkouts {

    private ObservableList<WorkRoutine> routines;

    public AvailableWorkouts() {
        this.routines = FXCollections.observableArrayList();
    }

    public ObservableList<WorkRoutine> getRoutines() {
        return routines;
    }

    public void setRoutines(ObservableList<WorkRoutine> routines) {
        this.routines = routines;
    }
}
