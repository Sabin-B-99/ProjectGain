package com.projectgain.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public final class AvailableWorkouts {

    private static AvailableWorkouts INSTANCE;
    private ObservableList<WorkRoutine> routines;

    private AvailableWorkouts(){
        this.routines = FXCollections.observableArrayList();
    }

    public static AvailableWorkouts getInstance() {
        if(INSTANCE == null){
            INSTANCE = new AvailableWorkouts();
        }
        return INSTANCE;
    }

    public ObservableList<WorkRoutine> getRoutines() {
        return routines;
    }

    public void setRoutines(ObservableList<WorkRoutine> routines) {
        this.routines = routines;
    }
}
