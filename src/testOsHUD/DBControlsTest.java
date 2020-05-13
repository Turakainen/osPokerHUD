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
    void testConnectDatabase() {
        fail("Not yet implemented");
    }


    @Test
    void testCreateTable() {
        fail("Not yet implemented");
    }


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
        fail("Not yet implemented");
    }


    @Test
    void testFindPlayer() {
        fail("Not yet implemented");
    }


    @Test
    void testPrintTable() {
        fail("Not yet implemented");
    }
    
    @AfterEach
    void onTearDown() {
        File db = new File(testDB+".db");
        db.delete();
    }

}
