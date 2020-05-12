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
     * Creates a database connection
     * @param db database name
     * @return database connection
     */
    public static Connection connectDatabase(String db) {
        Connection c = null;
        
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:" + db + ".db");
           System.out.println("Opened database successfully");
           return c;
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        return c;
    }
    
    /**
     * Creates a new table for poker room
     * @param db database name
     * @param table name
     */
    public static void createTable(String db, String table) {
        Connection c = null;
        Statement stmt = null;
        
        try {
           c = connectDatabase(db);
           
           DatabaseMetaData dbm = c.getMetaData();
           ResultSet tables = dbm.getTables(null, null, "pokerstars", null);
           
           if (!tables.next()) {
            stmt = c.createStatement();
            String sql = "CREATE TABLE " + table + " "
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
     * @param db database name
     * @param table name
     * @param player player to be added
     */
    public static void insertPlayer(String db, String table, Player player) {
        Connection c = null;
        Statement stmt = null;

        try {
            c = connectDatabase(db);
            c.setAutoCommit(false);
            
            PreparedStatement pstmt;
            String sql = "INSERT INTO " + table + " "
                   + "(name, handsplayed, preflopcalls, preflopbets, preflopraises) "
                   + "VALUES (?,?,?,?,?);";
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
     * Looks for player with given name and adds current stats to database table
     * @param db database name
     * @param table name
     * @param player to update
     */
    public static void updatePlayer(String db, String table, Player player) {
        Connection c = null;
        
        try {
            c = connectDatabase(db);
            c.setAutoCommit(false);
            
            PreparedStatement pstmt;
            String sql = "UPDATE " + table
                    + " SET handsplayed=?"
                    + ", preflopcalls=?"
                    + ", preflopbets=?"
                    + ", preflopraises=?"
                    + " WHERE name=?;";
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, player.getHandsPlayed());
            pstmt.setInt(2, player.getPreflopCalls());
            pstmt.setInt(3, player.getPreflopBets());
            pstmt.setInt(4, player.getPreflopRaises());
            pstmt.setString(5, player.getName());
            pstmt.executeUpdate();
            pstmt.close();
            
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Finds player with given name from database table
     * @param db name of the database
     * @param table name
     * @param name player to find
     * @return player object
     */
    public static Player findPlayer(String db, String table, String name) {
        Connection c = null;
        Player player = null;
        
        try {
            c = connectDatabase(db);
            
            PreparedStatement pstmt;
            String sql = "SELECT *"
                    + " FROM " + table 
                    + " WHERE name = ?;";
            
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                player = new Player(rs.getString("name"));
                player.setHandsPlayed(rs.getInt("handsplayed"));
                player.setPreflopCalls(rs.getInt("preflopcalls"));
                player.setPreflopBets(rs.getInt("preflopbets"));
                player.setPreflopRaises(rs.getInt("preflopraises"));
            }
            
            pstmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return player;
    }
    
    /**
     * Prints table contents to console
     * @param db database name
     * @param table to print
     */
    public static void printTable(String db, String table) {
        Connection c = null;
        
        try {
           c = connectDatabase(db);
           Statement st = c.createStatement();
           ResultSet rs = st.executeQuery("SELECT * FROM " + table);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           System.out.println("============================");
           System.out.println("Table " + table + " contents:");
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
        String db = "osPokerHUD";
        String table = "pokerstars";
        Player p1 = new Player("Foo");
        Player p2 = new Player("Bar");
        Player p3 = new Player("Foobar");
        
        createTable(db, table);
        
        insertPlayer(db, table, p1);
        insertPlayer(db, table, p2);
        insertPlayer(db, table, p3);
        
        p1.setHandsPlayed(150);
        p1.setPreflopBets(40);
        p1.setPreflopRaises(15);
        p2.setHandsPlayed(300);
        p2.setPreflopBets(100);
        
        updatePlayer(db, table, p1);
        updatePlayer(db, table, p2);
        
        printTable(db, table);
        
        p3 = findPlayer(db, table, "Foo");
        System.out.println("P3: " + p3.getName());
        System.out.println(p3.getName());
        System.out.println(p3.getHandsPlayed());
        System.out.println(p3.getPreflopCalls());
        System.out.println(p3.getPreflopBets());
        System.out.println(p3.getPreflopRaises());
    }
}
