package com.projectgain.dao;

import java.sql.*;

public abstract class DatabaseConfigurationBaseDAO {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/projgain_workout_routine";
    public static final String USER = "root";
    public static final String PASSWORD = "asdf";

    public DatabaseConfigurationBaseDAO() {
    }

    public boolean checkIfRowExistsById(String preparedSqlWithWhereIdClause, int id){
        boolean valueExists = false;
        try {
            Connection jdbcConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = jdbcConnection.prepareStatement(preparedSqlWithWhereIdClause);
            stmt.setInt(1, id);
            ResultSet rs =  stmt.executeQuery();
            if(rs.next()){
                int noOfValues = rs.getInt(1);
                if(noOfValues > 0){
                    valueExists = true;
                }
            }
            stmt.close();
            jdbcConnection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return valueExists;
    }
}
