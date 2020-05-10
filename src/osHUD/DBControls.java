package osHUD;

import java.sql.*;

import com.sun.jna.platform.win32.DBT;

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
           c = DriverManager.getConnection("jdbc:sqlite:osPokerHUD.db");
           System.out.println("Opened database successfully");
           
           DatabaseMetaData dbm = c.getMetaData();
           ResultSet tables = dbm.getTables(null, null, "pokerstars", null);
           
           if (!tables.next()) {
            stmt = c.createStatement();
            String sql = "CREATE TABLE pokerstars "
                    + "( name          TEXT   PRIMARY KEY   NOT NULL,"
                    + " handsplayed    INT, " 
                    + " preflopcalls   INT, "
                    + " preflopbets    INT," 
                    + " preflopraises  INT)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("Table created successfully");
        } else {
            System.out.println("ERROR: Table already exists!");
        }
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
    }
    
    /**
     * Adds a new player to table
     * @param player player to be added
     */
    public static void insertPlayer(Player player) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:osPokerHUD.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            
            PreparedStatement pstmt;
            String sql = "INSERT INTO pokerstars "
                   + "(name, handsplayed, preflopcalls, preflopbets, preflopraises) "
                   + "VALUES (?,?,?,?,?)";
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getHandsPlayed());
            pstmt.setInt(3, player.getPreflopCalls());
            pstmt.setInt(4, player.getPreflopBets());
            pstmt.setInt(5, player.getPreflopRaises());
            pstmt.executeUpdate();
            pstmt.close();
            
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Records created successfully");
    }
    
    /**
     * Looks for player with given name and adds current hands to data
     * @param playerName name of the player
     */
    public static void appendPlayer(String playerName) {
        
    }
    
    /**
     * Prints table contents to console
     * @param table to print
     */
    public static void printTable(String table) {
        Connection c = null;
        
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:osPokerHUD.db");
           Statement st = c.createStatement();
           ResultSet rs = st.executeQuery("SELECT * FROM " + table);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           System.out.println("============================");
           System.out.println("Table " + table + "contents:");
           while(rs.next()) {
               for(int i = 1; i <= columnCount; i++) {
                   System.out.println(rs.getString(i) + " ");
               }
               System.out.println();
           }
           System.out.println("============================");
           c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
    }
    
    /**
     * Test program
     * @param args not in use
     */
    public static void main(String[] args) {
        createDatabase();
        createTable();
        Player p1 = new Player("Foo");
        Player p2 = new Player("Bar");
        Player p3 = new Player("Foobar");
        insertPlayer(p1);
        insertPlayer(p2);
        insertPlayer(p3);
        printTable("pokerstars");
    }
}
