package com.ecp.sudoku.model;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.sql.*;
import java.util.Random;

//With help from from http://www.javatpoint.com/singleton-design-pattern-in-java
//This should probably be in Spring using beans and stuff

/**
 * Created by ericpass on 12/11/15.
 */
public class DatabaseManagerSingleton {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sudokudbv1";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    private static DatabaseManagerSingleton manager;
    private static Random random = new Random();

    private DatabaseManagerSingleton(){}

    public static DatabaseManagerSingleton getInstance(){
        if(manager == null){
            manager = new DatabaseManagerSingleton();
            System.out.println("Creating first dbManager");
        }
        return manager;
    }

    private static Connection getConnection() throws ClassNotFoundException,SQLException{
        Connection con = null;
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        return con;
    }

    public static PuzzleEntity getRandomPuzzel(SudokuDifficulty difficulty) throws SQLException{
        if(difficulty == null){
            difficulty = SudokuDifficulty.SIMPLE;
        }
        Connection con = null;
        PreparedStatement puzzleStatement = null;
        PreparedStatement sizeStatement = null;
        ResultSet rs = null;
        try {

            con=DatabaseManagerSingleton.getConnection();

            sizeStatement = con.prepareStatement("select count(*) as size from PUZZLES where rating=?");
            sizeStatement.setString(1, difficulty.getDisplayString());
            ResultSet sizeSet = sizeStatement.executeQuery();
            int size = 0;
            if(sizeSet.next()){
                size = sizeSet.getInt("size");
                System.out.println("We got size of: "+size);
            }
            if(size == 0){
                System.out.println("Result set is empty");
            }
            puzzleStatement=con.prepareStatement("select * from PUZZLES where rating=? order by puzzle_id ASC");
            puzzleStatement.setString(1, difficulty.getDisplayString());

            rs=puzzleStatement.executeQuery();
            if(rs.next()){
                int randomRowNum = random.nextInt(size);
                rs.absolute(randomRowNum);
                return new PuzzleEntity(rs);
            } else {
                System.err.println("Problems with result set for puzzle");
            }



        } catch (Exception e) { System.out.println(e);}
        finally{
            if(rs!=null){
                rs.close();
            }if (puzzleStatement!=null){
                puzzleStatement.close();
            }if(con!=null){
                con.close();
            }
        }
        return null;
    }


    public static PuzzleEntity[] getBulk(SudokuDifficulty difficulty, int number) throws SQLException{
        Connection con = null;
        PreparedStatement puzzleStatement = null;
        ResultSet rs = null;
        PuzzleEntity[] res = new PuzzleEntity[number];
        try {

            con=DatabaseManagerSingleton.getConnection();

            puzzleStatement=con.prepareStatement("select * from PUZZLES where rating=?");
            puzzleStatement.setString(1, difficulty.getDisplayString());


            rs=puzzleStatement.executeQuery();

            if(rs.next()){
                for (int i = 0; i <number; i++) {
                    res[i]= new PuzzleEntity(rs);
                }
            } else {
                System.err.println("Problems with result set for puzzle");
            }



        } catch (Exception e) { System.out.println(e);}
        finally{
            if(rs!=null){
                rs.close();
            }if (puzzleStatement!=null){
                puzzleStatement.close();
            }if(con!=null){
                con.close();
            }
        }
        return res;
    }





}
