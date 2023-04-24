package com.projectgain.manager;

import com.projectgain.dao.WorkCardDAO;
import com.projectgain.dao.WorkGroupDAO;
import com.projectgain.dao.WorkRoutineDAO;
import com.projectgain.models.WorkRoutine;

import java.util.List;

public class DatabaseManager extends BaseManager{
    private WorkRoutineDAO workRoutineDAO;
    private WorkGroupDAO workGroupDAO;
    private WorkCardDAO workCardDAO;

    public DatabaseManager() {
        this.workRoutineDAO = new WorkRoutineDAO();
        this.workGroupDAO = new WorkGroupDAO();
        this.workCardDAO = new WorkCardDAO();
    }

    public List<WorkRoutine> loadWorkRoutinesFromDatabaseLazily(){
        return workRoutineDAO.getWorkRoutines(true);
    }

    public List<WorkRoutine> loadWorkRoutinesFromDatabase(){
        return workRoutineDAO.getWorkRoutines(false);
    }

    public void loadAllRelatedEntities(WorkRoutine workRoutine){
        this.workGroupDAO.loadWorkRoutineEntities(workRoutine);
    }
}
