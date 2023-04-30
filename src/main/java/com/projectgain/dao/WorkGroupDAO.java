package com.projectgain.dao;

import com.projectgain.models.WorkGroup;
import com.projectgain.models.WorkRoutine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class WorkGroupDAO extends DatabaseConfigurationBaseDAO {

    private WorkCardDAO workCardDAO;

    public WorkGroupDAO() {
        workCardDAO = new WorkCardDAO();
    }

    private WorkGroup createWorkGroup(ResultSet rs){
        WorkGroup workGroup = new WorkGroup();
        try{
            workGroup.setId(rs.getInt("id"));
            workGroup.setSets(rs.getInt("sets"));
            workGroup.setIndexInCurrentRoutine(rs.getInt("order"));
            workGroup.setWorkCardList(workCardDAO.getWorkCardsByWorkGroupId(rs.getInt("id")));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroup;
    }

    public WorkGroup getWorkGroupById(int workGroupId){
        String sqlQuery = "SELECT * FROM work_groups WHERE id = ?;";
        WorkGroup workGroup = null;
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(sqlQuery);
            stmt.setInt(1, workGroupId);
            ResultSet rs = stmt.executeQuery();
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
                "workout_routines.id = work_groups.workout_routine_id WHERE workout_routine_id = ?;";
        List<WorkGroup> workGroups = new ArrayList<>();
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(sqlQuery);
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();
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
        String sqlQuery = "DELETE FROM work_groups WHERE id = ?;";
        boolean deleted = false;
        if(workCardDAO.deleteWorkCardByWorkGroupId(id)){
            try {
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sqlQuery);
                stmt.setInt(1, id);
                int noOfRowsUpdated = stmt.executeUpdate();
                if(noOfRowsUpdated > 0){
                    deleted = true;
                }
                stmt.close();
                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return deleted;
    }

    public int saveOrUpdateWorkGroup(WorkGroup workGroup, int parentWorkRoutineId){
        String query;
        boolean workGroupAlreadyExists = checkIfWorkGroupExistsById(workGroup.getId());
        int workGroupIdAutoGenerated = -1;
        if(workGroupAlreadyExists){
            workGroupIdAutoGenerated = workGroup.getId();
            query = "UPDATE work_groups SET `sets` = ?, `order` = ? , `workout_routine_id` = ? WHERE `id` = ?";
        }else {
            query = "INSERT INTO work_groups(`sets`, `order`,`workout_routine_id`)" +
                    "VALUES(?,?,?);";
        }
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, workGroup.getSets());
            stmt.setInt(2, workGroup.getIndexInCurrentRoutine());
            stmt.setInt(3, parentWorkRoutineId);
            if(workGroupAlreadyExists) stmt.setInt(4, workGroup.getId());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    workGroupIdAutoGenerated = rs.getInt(1);
                }
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workGroupIdAutoGenerated;
    }

    private boolean checkIfWorkGroupExistsById(int id){
        //new work group is being created. No need to check.
        if (id == -1) {
            return false;
        }
        String sqlQuery = "SELECT COUNT(*) FROM work_groups WHERE id = ?";
        return checkIfRowExistsById(sqlQuery, id);
    }

    public void loadWorkRoutineEntities(WorkRoutine workRoutine) {
        workRoutine.setWorkGroupList(getWorkGroupsByRoutineId(workRoutine.getId()));
    }
}
