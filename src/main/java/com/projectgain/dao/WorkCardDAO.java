package com.projectgain.dao;

import com.projectgain.configuration.DatabaseProps;
import com.projectgain.models.WorkCard;
import com.projectgain.models.WorkType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkCardDAO implements DatabaseProps {


    public WorkCardDAO() {
    }

    private WorkCard createWorkCard(ResultSet rs){
        WorkCard workCard = new WorkCard();
        try {
            workCard.setTitle(rs.getString("workout_name"));
            workCard.setColorHexCode(rs.getString("card_hex_color"));

            if (rs.getString("workout_type").equals(WorkType.REP.name())){
                workCard.setWorkType(WorkType.REP);
                workCard.setTime(rs.getTime("workout_time").toLocalTime());
                workCard.setReps(rs.getInt("workout_reps"));
            }else{
                workCard.setWorkType(WorkType.TIMED);
                workCard.setTime(rs.getTime("workout_time").toLocalTime());
            }
        }catch (SQLException e){

        }
        return workCard;
    }


    public WorkCard getWorkCardById(int id){
        String sqlQuery = "SELECT * FROM work_cards WHERE id = " + id + ";";
        WorkCard workCard = null;
        try{
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
           if(rs.next()){
               workCard = createWorkCard(rs);
           }
           rs.close();
           jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workCard;
    }

    public List<WorkCard> getWorkCards(){
        String sqlQuery = "SELECT * FROM work_cards";
        List<WorkCard> workCards = new ArrayList<>();
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                WorkCard card = createWorkCard(rs);
                workCards.add(card);
            }
            rs.close();
            jdbcConnection.close();
        }catch (SQLException e){

        }
        return workCards;
    }

    public List<WorkCard> getWorkCardsByWorkGroupId(int workGroupID){

        String sqlQuery = "SELECT * FROM work_cards INNER JOIN work_groups ON work_cards.work_group_id = work_groups.id" +
                " WHERE work_group_id = " + workGroupID + ";";

        List<WorkCard> workCards = new ArrayList<>();
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                WorkCard workCard = createWorkCard(rs);
                workCards.add(workCard);
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return workCards;
    }

    public boolean deleteWorkCardById(int id){
        String sqlQuery  = "DELETE FROM work_cards WHERE id = " + id + ";";
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            int deleted =  stmt.executeUpdate(sqlQuery);
            if(deleted == 0){
                return false;
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean deleteWorkCardByWorkGroupId(int id){
        String sqlQuery = "DELETE FROM work_cards WHERE work_group_id = " + id + ";";
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = jdbcConnection.createStatement();
            int deleted = stmt.executeUpdate(sqlQuery);
            if(deleted == 0){
                return false;
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    public void saveWorkCard(WorkCard workCard, int parentWorkGroupId){
        String query = createQueryForInsertBasedOnWorkoutType(workCard);
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(query);
            stmt.setString(1, workCard.getTitle());
            stmt.setString(2, workCard.getWorkType().name());
            stmt.setString(3, workCard.getColorHexCode());
            stmt.setString(4, workCard.getTime().toString());
            if(workCard.getWorkType() == WorkType.REP){
                stmt.setInt(5, workCard.getReps());
                stmt.setInt(6, parentWorkGroupId);
            }else {
                stmt.setInt(5, parentWorkGroupId);
            }
            stmt.executeUpdate();
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            e.getMessage();
        }
    }

    private String createQueryForInsertBasedOnWorkoutType(WorkCard workCard){
        if(workCard.getWorkType() == WorkType.TIMED){
            return  "INSERT INTO work_cards (`workout_name`, `workout_type`, `card_hex_color`, `workout_time`, `work_group_id`)" +
                    "VALUES(?,?,?,?,?);";
        }else{
            return  "INSERT INTO work_cards (`workout_name`, `workout_type`, `card_hex_color`, `workout_time`,`workout_reps`, `work_group_id`)" +
                    "VALUES(?,?,?,?,?,?);";
        }
    }
}
