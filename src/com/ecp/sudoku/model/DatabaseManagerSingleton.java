package com.ecp.sudoku.model;
import java.sql.*;

//With help from from http://www.javatpoint.com/singleton-design-pattern-in-java
//This should probably be in Spring using beans and stuff

/**
 * Created by ericpass on 12/11/15.
 */
public class DatabaseManagerSingleton {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/sudokudbv1";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    private static DatabaseManagerSingleton manager;

    private DatabaseManagerSingleton(){}

    public static DatabaseManagerSingleton getInstance(){
        if(manager == null){
            manager = new DatabaseManagerSingleton();
        }
        return manager;
    }

    private static Connection getConnection() throws ClassNotFoundException,SQLException{
        Connection con = null;
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        return con;
    }






}
