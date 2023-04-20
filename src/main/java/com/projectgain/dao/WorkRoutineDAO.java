package com.projectgain.dao;


import com.projectgain.configuration.DatabaseProps;
import com.projectgain.models.WorkRoutine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRoutineDAO implements DatabaseProps {
    private WorkGroupDAO workGroupDAO;

    public WorkRoutineDAO() {
        this.workGroupDAO = new WorkGroupDAO();
    }

    private WorkRoutine createWorkRoutine(ResultSet rs, boolean lazyLoad){
        WorkRoutine workRoutine = new WorkRoutine();
        try {
            workRoutine.setTitle(rs.getString("title"));
            if(!lazyLoad){
                workRoutine.setWorkGroupList(workGroupDAO.getWorkGroupsByRoutineId(rs.getInt("id")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workRoutine;
    }


    public WorkRoutine getWorkRoutineById(int routineId, boolean lazyLoad){
        String sqlQuery = "SELECT * FROM workout_routines WHERE id = " + routineId + ";";
        WorkRoutine workRoutine = null;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if(rs.next()){
                workRoutine = createWorkRoutine(rs, lazyLoad);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workRoutine;
    }

    public List<WorkRoutine> getWorkRoutines(boolean lazyLoad){
        String sqlQuery = "SELECT * FROM workout_routines;";
        List<WorkRoutine> routines = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                WorkRoutine routine = createWorkRoutine(rs, lazyLoad);
                routines.add(routine);
            }
            stmt.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return routines;
    }

    public boolean deleteWorkRoutineById(int id){
        String sqlQuery = "DELETE FROM workout_routines WHERE id = " + id + ";";
        String sqlQueryForWorkGroupId = "SELECT work_groups.id FROM work_groups INNER JOIN workout_routines ON " +
                "work_groups.workout_routine_id = workout_routines.id WHERE workout_routine_id = " + id +";";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQueryForWorkGroupId);
            int workGroupId = 0;
            boolean deletionResult = true;
            while (rs.next()){
                workGroupId = rs.getInt("id");
                deletionResult &= workGroupDAO.deleteWorkGroupById(workGroupId);
            }
            if(deletionResult){
                int routineDeleted = stmt.executeUpdate(sqlQuery);
                if(routineDeleted == 0){
                    return false;
                }
            }
            stmt.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
}
