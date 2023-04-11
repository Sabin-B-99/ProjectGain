package com.projectgain.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class WorkGroup {
    private int indexInCurrentRoutine;
    private IntegerProperty sets;
    List<WorkCard> workCardList;

    public WorkGroup() {
        this.indexInCurrentRoutine = -1;
        this.sets = new SimpleIntegerProperty();
        this.workCardList = new ArrayList<>();
    }

    public int getIndexInCurrentRoutine() {
        return indexInCurrentRoutine;
    }

    public void setIndexInCurrentRoutine(int indexInCurrentRoutine) {
        this.indexInCurrentRoutine = indexInCurrentRoutine;
    }

    public int getSets() {
        return sets.get();
    }

    public IntegerProperty setsProperty() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets.set(sets);
    }

    public List<WorkCard> getWorkCardList() {
        return workCardList;
    }

    public void setWorkCardList(List<WorkCard> workCardList) {
        this.workCardList = workCardList;
    }
}
