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
}
