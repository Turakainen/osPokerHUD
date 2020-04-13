package osHUD;

import java.sql.*;

/**
 * Creates and controls data of database
 * @author Olli
 * @version 10.4.2020
 *
 */
public class DBControls {

    /**
     * Initializes a new database
     */
    public static void createDatabase() {
        Connection c = null;
        
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:osPokerHUD.db");
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
    /**
     * Creates a new table for poker room
     */
    public static void createTable() {
        Connection c = null;
        Statement stmt = null;
        
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:test.db");
           System.out.println("Opened database successfully");

           stmt = c.createStatement();
           String sql = "CREATE TABLE pokerstars " +
                          "( name          INT   PRIMARY KEY   NOT NULL," +
                          " handsplayed    INT, " + 
                          " preflopcalls   INT, " + 
                          " preflopbets    INT,"  + 
                          " preflopraises  INT)"; 
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        System.out.println("Table created successfully");
    }
    
    /**
     * Adds a new player to table
     * @param player player to be added
     */
    public static void insertPlayer(Player player) {
        
    }
    
    /**
     * Looks for player with given name and adds current hands to data
     * @param playerName name of the player
     */
    public static void appendPlayer(String playerName) {
        
    }
    
    /**
     * Test program
     * @param args not in use
     */
    public static void main(String[] args) {
        createDatabase();
        createTable();
    }
}
