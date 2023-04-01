package com.projectgain.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Random;

public class WorkRoutineManager {


    private  Color lastWorkCardColor = Color.RED;
    private static final Color[] WORK_CARD_INITIAL_AVAILABLE_COLORS = {
            Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN
    };

    private final ObservableList<Pane> landingWindowActivePanes;
    private final ObservableList<Pane> workGroupsPaneList;
    private final ObservableMap<Integer, ObservableList<Pane>> workCardsList;

    private int totalWorkCardGroup;

    public WorkRoutineManager() {
        landingWindowActivePanes = FXCollections.observableArrayList();
        workGroupsPaneList = FXCollections.observableArrayList();
        workCardsList = FXCollections.observableHashMap();
        this.totalWorkCardGroup = 0;
    }


    public ObservableList<Pane> getLandingWindowActivePanes() {
        return landingWindowActivePanes;
    }

    public ObservableList<Pane> getWorkGroupsPaneList() {
        return workGroupsPaneList;
    }

    public ObservableMap<Integer, ObservableList<Pane>> getWorkCardsList() {
        return workCardsList;
    }

    public Color getLastWorkCardColor() {
        return lastWorkCardColor;
    }
    public void deleteWorkGroupPane(Pane workCardGroupRootAnchorPane) {
        int indexOfPaneBeingRemoved = getWorkGroupIndex(workCardGroupRootAnchorPane);
        this.workGroupsPaneList.remove(workCardGroupRootAnchorPane);
        this.getWorkCardsList().remove(indexOfPaneBeingRemoved);
        setTotalWorkCardGroup(getTotalWorkCardGroup() - 1);
    }

    public int getWorkGroupIndex(Pane workGroupPane){
        return workGroupsPaneList.indexOf(workGroupPane);
    }

    public int getTotalWorkCardGroup() {
        return totalWorkCardGroup;
    }

    public void setTotalWorkCardGroup(int totalWorkCardGroup) {
        this.totalWorkCardGroup = totalWorkCardGroup;
    }

    public void deleteWorkCardPane(Pane workCardPane) {
        ObservableList<Pane> workCardGroup = findWorkCardGroup(workCardPane);
        if(workCardPane != null){
            workCardGroup.remove(workCardPane);
        }
    }

    private ObservableList<Pane> findWorkCardGroup(Pane workCardPane){
        for (Integer key: workCardsList.keySet()) {
            if(workCardsList.get(key).contains(workCardPane)){
                return workCardsList.get(key);
            }
        }
        return null;
    }

    public void copyWorkCard(Pane workCardPane) {
        System.out.println("TODO: work will be done later. Need to code own logic to copy node");
    }


    public Color generateWorkCardColor(){
        Random rnd = new Random(System.currentTimeMillis());
        int rndVal = rnd.nextInt(WORK_CARD_INITIAL_AVAILABLE_COLORS.length);
        while (lastWorkCardColor.equals(WORK_CARD_INITIAL_AVAILABLE_COLORS[rndVal])){
            rndVal = rnd.nextInt(WORK_CARD_INITIAL_AVAILABLE_COLORS.length);
        }
        lastWorkCardColor = WORK_CARD_INITIAL_AVAILABLE_COLORS[rndVal];
        return WORK_CARD_INITIAL_AVAILABLE_COLORS[rndVal];
    }

    public void deleteRoutinePane(Pane workRoutineFormRootPane) {
        this.totalWorkCardGroup = 0;
        this.workCardsList.clear();
        this.workGroupsPaneList.clear();
        this.landingWindowActivePanes.remove(workRoutineFormRootPane);
    }
}
