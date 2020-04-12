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
    }
}
