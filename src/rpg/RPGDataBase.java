/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class RPGDataBase {

    private String url = "jdbc:derby://localhost:1527/RPGDB;create=true";
    private String user = "jaw123";
    private String pass = "jaw123";
    private Connection conn;
    private String pk;

    public void RPGDataBase() {
        pk = java.util.UUID.randomUUID().toString();
    }

    public void connectDB() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println(conn.toString());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            if (doesTableExist("HIGHSCORES") == true) {
                return;
            }
            Statement statement = conn.createStatement();
            String highScores = "HIGHSCORES";
            //Create player table
            statement.executeUpdate("CREATE TABLE " + highScores
                    + " (ID VARCHAR(50) NOT NULL, NAME VARCHAR(20), SCORE INT, PRIMARY KEY(ID))");
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPlayerData(String name, int score) {
        try {
            Statement statement = conn.createStatement();
            pk = java.util.UUID.randomUUID().toString();
            statement.executeUpdate("INSERT INTO HIGHSCORES " + "VALUES('" + pk + "','" + name + "'," + score + ")");
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> loadData() {
        try {
            //get list of all the saved game
            Statement statement = conn.createStatement();
            ArrayList<String[]> result = new ArrayList<String[]>();
            ResultSet rs = statement.executeQuery("SELECT name, score FROM highscores WHERE score > 0 ORDER BY score DESC");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                result.add(row);
            }
            statement.close(); 
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doesTableExist(String newTableName) {
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    return true;
                }
            }
            rsDBMeta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
