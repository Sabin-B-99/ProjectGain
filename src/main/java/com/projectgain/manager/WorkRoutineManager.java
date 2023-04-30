package com.projectgain.manager;

import com.projectgain.dao.WorkCardDAO;
import com.projectgain.dao.WorkGroupDAO;
import com.projectgain.dao.WorkRoutineDAO;
import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;
import com.projectgain.models.WorkType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkRoutineManager {


    private  Color lastWorkCardColor = Color.RED;
    private static final Color[] WORK_CARD_INITIAL_AVAILABLE_COLORS = {
            Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN
    };
    private final ObservableList<Pane> workGroupsPaneList;
    private final ObservableMap<Integer, ObservableList<Pane>> workCardsList;
    private WorkRoutine workRoutineModel;

    private int indexOfLastWorkGroupOnWhichAddBtnPressed;
    private int totalWorkCardGroup;


    private List<Integer> workCardIdsRemovedByEditing;
    private List<Integer> workGroupIdsRemovedByEditing;

    public WorkRoutineManager() {
        this.workGroupsPaneList = FXCollections.observableArrayList();
        this.workCardsList = FXCollections.observableHashMap();
        this.totalWorkCardGroup = 0;

        this.workCardIdsRemovedByEditing = new ArrayList<>();
        this.workGroupIdsRemovedByEditing = new ArrayList<>();
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
        this.workRoutineModel.getWorkGroupList().remove(indexOfPaneBeingRemoved);
        setTotalWorkCardGroup(getTotalWorkCardGroup() - 1);
    }

    public int getWorkGroupIndex(Pane workGroupPane){
        return workGroupsPaneList.indexOf(workGroupPane);
    }

    public int getTotalWorkCardGroup() {
        return workRoutineModel.getWorkGroupList().size();
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

    public ObservableList<Pane> findWorkCardGroup(Pane workCardPane){
        for (Integer key: workCardsList.keySet()) {
            if(workCardsList.get(key).contains(workCardPane)){
                return workCardsList.get(key);
            }
        }
        return null;
    }

    public int findWorkCardParentWorkGroupIndex(Pane workCardPane){
        for (Integer key: workCardsList.keySet()) {
            if(workCardsList.get(key).contains(workCardPane)){
                return key;
            }
        }
        return -1;
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

    public void deleteRoutinePane(Pane workRoutineFormRootPane, AppManager mainAppManager) {
        this.totalWorkCardGroup = 0;
        this.workCardsList.clear();
        this.workGroupsPaneList.clear();
        mainAppManager.getLandingWindowActivePanes().remove(workRoutineFormRootPane);
    }
    public void setWorkRoutineModel(WorkRoutine workRoutineModel) {
        this.workRoutineModel = workRoutineModel;
    }

    public WorkRoutine getWorkRoutineModel() {
        return workRoutineModel;
    }

    public int getIndexOfLastWorkGroupOnWhichAddBtnPressed() {
        return indexOfLastWorkGroupOnWhichAddBtnPressed;
    }

    public void setIndexOfLastWorkGroupOnWhichAddBtnPressed(int indexOfLastWorkGroupOnWhichAddBtnPressed) {
        this.indexOfLastWorkGroupOnWhichAddBtnPressed = indexOfLastWorkGroupOnWhichAddBtnPressed;
    }

    public List<WorkGroup> getWorkRoutineWorkGroups(){
        return this.workRoutineModel.getWorkGroupList();
    }

    public List<WorkCard> getWorkCardsOfWorkGroupAtIndex(int workGroupIndex){
        return this.workRoutineModel.getWorkGroupList().get(workGroupIndex).getWorkCardList();
    }

    public void performRoutineSaveOperations(AppManager manager) {
        manager.saveRoutine(workRoutineModel);
    }

    public void setLastWorkCardColor(Color lastWorkCardColor) {
        this.lastWorkCardColor = lastWorkCardColor;
    }

    public List<Integer> getWorkCardIdsRemovedByEditing() {
        return workCardIdsRemovedByEditing;
    }

    public void setWorkCardIdsRemovedByEditing(List<Integer> workCardIdsRemovedByEditing) {
        this.workCardIdsRemovedByEditing = workCardIdsRemovedByEditing;
    }

    public List<Integer> getWorkGroupIdsRemovedByEditing() {
        return workGroupIdsRemovedByEditing;
    }

    public void setWorkGroupIdsRemovedByEditing(List<Integer> workGroupIdsRemovedByEditing) {
        this.workGroupIdsRemovedByEditing = workGroupIdsRemovedByEditing;
    }

    public void checkDeletedEntitiesAndUpdateDB(AppManager manager) {
        manager.checkDeletedEntitiesAndUpdateDB(workGroupIdsRemovedByEditing, workCardIdsRemovedByEditing);
        workCardIdsRemovedByEditing.clear();
        workGroupIdsRemovedByEditing.clear();
    }
}
