package com.projectgain.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class WorkRoutine {

    int id;
    private StringProperty title;
    private List<WorkGroup> workGroupList;

    public WorkRoutine() {
        this.id = -1;
        this.title = new SimpleStringProperty();
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
}
