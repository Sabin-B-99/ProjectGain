package com.projectgain.dao;


import com.projectgain.models.WorkRoutine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRoutineDAO extends DatabaseConfigurationBaseDAO {
    private WorkGroupDAO workGroupDAO;

    public WorkRoutineDAO() {
        this.workGroupDAO = new WorkGroupDAO();
    }

    private WorkRoutine createWorkRoutine(ResultSet rs, boolean lazyLoad){
        WorkRoutine workRoutine = new WorkRoutine();
        try {
            workRoutine.setId(rs.getInt("id"));
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
        String sqlQuery = "SELECT * FROM workout_routines WHERE id = ?;";
        WorkRoutine workRoutine = null;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                workRoutine = createWorkRoutine(rs, lazyLoad);
            }
            stmt.close();
            connection.close();
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
        String sqlQuery = "DELETE FROM workout_routines WHERE id = ?;";
        String sqlQueryForWorkGroupId = "SELECT work_groups.id FROM work_groups INNER JOIN workout_routines ON " +
                "work_groups.workout_routine_id = workout_routines.id WHERE workout_routine_id = ?;";
        boolean routineDeleted = false;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sqlQueryForWorkGroupId);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            int workGroupId = 0;
            boolean deletionResult = true;
            while (rs.next()){
                workGroupId = rs.getInt("id");
                deletionResult &= workGroupDAO.deleteWorkGroupById(workGroupId);
            }
            if(deletionResult){
                stmt.close();
                stmt = connection.prepareStatement(sqlQuery);
                stmt.setInt(1, id);
                int noOfRowsUpdated = stmt.executeUpdate();
                if(noOfRowsUpdated > 0){
                    routineDeleted = true;
                }
            }
            stmt.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return routineDeleted;
    }

    public int saveOrUpdateWorkRoutine(WorkRoutine workRoutine){
        String query;
        boolean alreadyExists = checkIfWorkRoutineExistsById(workRoutine.getId());
        if(alreadyExists){
            query = "UPDATE workout_routines SET title = ? WHERE id = ?";
        }else{
            query = "INSERT INTO workout_routines(`title`)" +
                    "VALUES(?);";
        }
        int savedRoutineIdAutoGen = -1;
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, workRoutine.getTitle());
            if (alreadyExists) stmt.setInt(2, workRoutine.getId());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                   savedRoutineIdAutoGen = rs.getInt(1);
                }
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  savedRoutineIdAutoGen;
    }

    private boolean checkIfWorkRoutineExistsById(int id){
        //new work routine is being created. No need to check.
        if (id == -1) {
            return false;
        }
        String sqlQuery = "SELECT COUNT(*) FROM workout_routines WHERE id = ?";
        return checkIfRowExistsById(sqlQuery,id);
    }
}
