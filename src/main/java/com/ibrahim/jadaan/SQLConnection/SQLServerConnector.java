package com.ibrahim.jadaan.SQLConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class SQLServerConnector {

    private String serverName = "localhost/";
    private String databaseName = "parallel_programming_db";
    private String userName = "root";
    private String password = "0123460";

    //********
    private Statement queryStatement;
    ResultSet resultSet;


    //************** * * * * * **  * * ** * * **

    public SQLServerConnector() {

    } // No Parameter Constructor

    Connection connection = getConnection();

    public Connection getConnection() {
        String connectionUrl = "jdbc:mysql://" + serverName + databaseName;

        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;

    }

    public int getLastId() {


        String selectionQuery = "SELECT MAX(user_id) FROM user_info";

        int lastInsertedId = 5;
        try {
            queryStatement = connection.createStatement();
            resultSet = queryStatement.executeQuery(selectionQuery);
            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt(1);
            }

            // Close the resources
            resultSet.close();
            queryStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastInsertedId;
    }

}   //************* SQLServerConnector Class end














