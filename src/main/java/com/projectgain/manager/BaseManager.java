package com.projectgain.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class BaseManager {
    private static final ObservableList<Pane> landingWindowActivePanes = FXCollections.observableArrayList();
    public ObservableList<Pane> getLandingWindowActivePanes() {
        return landingWindowActivePanes;
    }
}
