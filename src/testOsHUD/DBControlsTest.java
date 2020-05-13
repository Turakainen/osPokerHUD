package testOsHUD;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import osHUD.DBControls;
import osHUD.Player;

class DBControlsTest {

    public final String testDB = "testDB";
    public final String testTable = "testTable";
    
    @Test
    void testInsertPlayer() {
        DBControls.createTable(testDB, testTable);
        Player player1 = new Player("Tester 1");
        Player player2 = new Player("Tester 2");
        Player player3 = new Player("Tester 3");
        
        DBControls.insertPlayer(testDB, testTable, player1);
        assertNotNull(DBControls.findPlayer(testDB, testTable, player1.getName()));
        
        DBControls.insertPlayer(testDB, testTable, player2);
        DBControls.insertPlayer(testDB, testTable, player3);
        assertNotNull(DBControls.findPlayer(testDB, testTable, player2.getName()));
        assertNotNull(DBControls.findPlayer(testDB, testTable, player3.getName()));
    }


    @Test
    void testUpdatePlayer() {
        DBControls.createTable(testDB, testTable);
        Player player1 = new Player("Tester 1");
        DBControls.insertPlayer(testDB, testTable, player1);
        
        player1.setHandsPlayed(100);
        DBControls.updatePlayer(testDB, testTable, player1);
        player1 = DBControls.findPlayer(testDB, testTable, "Tester 1");
        assertTrue(player1.getHandsPlayed() == 100);
        
        player1.setHandsPlayed(125);
        DBControls.updatePlayer(testDB, testTable, player1);
        player1 = DBControls.findPlayer(testDB, testTable, "Tester 1");
        assertTrue(player1.getHandsPlayed() == 125);
    }


    @Test
    void testFindPlayer() {
        DBControls.createTable(testDB, testTable);
        Player player1 = new Player("Tester 1");
        player1.setHandsPlayed(100);
        player1.setPreflopCalls(40);
        DBControls.insertPlayer(testDB, testTable, player1);
        
        player1 = new Player("");
        player1 = DBControls.findPlayer(testDB, testTable, "Tester 1");
        
        assertTrue(player1.getHandsPlayed() == 100);
        assertTrue(player1.getPreflopCalls() == 40);
    }

    @AfterEach
    void onTearDown() {
        File db = new File(testDB+".db");
        db.delete();
    }

}
