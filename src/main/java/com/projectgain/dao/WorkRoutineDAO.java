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
}
