package com.projectgain.dao;

import com.projectgain.configuration.DatabaseProps;
import com.projectgain.models.WorkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkGroupDAO implements DatabaseProps {

    private WorkCardDAO workCardDAO;

    public WorkGroupDAO() {
        workCardDAO = new WorkCardDAO();
    }

    private WorkGroup createWorkGroup(ResultSet rs){
        WorkGroup workGroup = new WorkGroup();
        try{
            workGroup.setSets(rs.getInt("sets"));
            workGroup.setWorkCardList(workCardDAO.getWorkCardsByWorkGroupId(rs.getInt("id")));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroup;
    }

    public WorkGroup getWorkGroupById(int workGroupId){
        String sqlQuery = "SELECT * FROM work_groups WHERE id = " + workGroupId + ";";
        WorkGroup workGroup = null;
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if (rs.next()){
                workGroup = createWorkGroup(rs);
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroup;
    }

    public List<WorkGroup> getWorkGroups(){
        String sqlQuery = "SELECT * FROM work_groups";
        List<WorkGroup> workGroups = new ArrayList<>();
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                WorkGroup workGroup = createWorkGroup(rs);
                workGroups.add(workGroup);
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroups;
    }

    public List<WorkGroup> getWorkGroupsByRoutineId(int routineId){
        String sqlQuery = "SELECT * FROM work_groups INNER JOIN workout_routines ON " +
                "workout_routines.id = work_groups.workout_routine_id WHERE workout_routine_id = " + routineId + ";";
        List<WorkGroup> workGroups = new ArrayList<>();
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                WorkGroup workGroup = createWorkGroup(rs);
                workGroups.add(workGroup);
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroups;
    }

    public boolean deleteWorkGroupById(int id){
        String sqlQuery = "DELETE FROM work_groups WHERE id = " + id +";";

        if(workCardDAO.deleteWorkCardByWorkGroupId(id)){
            try {
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                Statement stmt = connection.createStatement();
                int deleted = stmt.executeUpdate(sqlQuery);
                if(deleted == 0){
                    return false;
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return true;
    }


}
