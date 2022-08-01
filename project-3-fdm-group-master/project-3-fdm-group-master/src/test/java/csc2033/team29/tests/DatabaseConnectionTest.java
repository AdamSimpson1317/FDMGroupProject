package csc2033.team29.tests;

import csc2033.team29.fdm.DBConnections.DBManager;
import csc2033.team29.fdm.DBConnections.Data.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseConnectionTest {

    DBManager manager;


    @BeforeAll
    public void databaseConnectionWorks() {
        manager = new DBManager();
        assertTrue(manager.connect(), "Check the ssh tunnel is set up");
        assertTrue(manager.isConnected());
    }

    @Test
    public void getLeaderBoardBy() {
        Timestamp before = Timestamp.valueOf("2020-12-01 09:01:15");
        Timestamp after = Timestamp.valueOf("2020-12-11 09:01:15");

        List<LeaderEntry> leaderBoard = manager.getLeaderBoard("TESTGAME", "TESTSTREAM", before);
        assertEquals(1, leaderBoard.size());
        leaderBoard = manager.getLeaderBoard("TESTGAME", "TESTSTREAM", after);
        assertEquals(0, leaderBoard.size());
    }

    @Test
    public void getLeaderBoardMax() {
        List<LeaderEntry> leaderBoard = manager.getLeaderBoard("TESTGAME", "TESTSTREAM", 3);
        assertEquals(2, leaderBoard.size());
        leaderBoard = manager.getLeaderBoard("TESTGAME", "TESTSTREAM", 2);
        assertEquals(2, leaderBoard.size());
        leaderBoard = manager.getLeaderBoard("TESTGAME", "TESTSTREAM", 1);
        assertEquals(1, leaderBoard.size());
    }

    @Test
    public void getGame1Qs() {
        List<Game1> game1Qs = manager.getGame1Q("TESTSTREAM");
        System.out.println(game1Qs);
        assertEquals(1, game1Qs.size());

    }

    @Test
    public void getGame2Qs() {
        List<Game2> game2Qs = manager.getGame2Q("TESTSTREAM");
        System.out.println(game2Qs);
        assertEquals(1, game2Qs.size());

    }

    @Test
    public void getGame3Qs() {
        List<Game3> game3Qs = manager.getGame3Q("TESTSTREAM");
        System.out.println(game3Qs);
        assertEquals(1, game3Qs.size());

    }

    @Test
    public void AddLeaderBoardEntry() {

        Timestamp now = Timestamp.from(Instant.now());
        LeaderEntry entry = new LeaderEntry("TEST", 123, now, "TESTADDGAME", "TESTADDSTREAM");

        assertTrue(manager.addLeaderBoardEntry(entry) );

        List<LeaderEntry> leaderBoard = manager.getLeaderBoard("TESTADDGAME", "TESTADDSTREAM", 3);
        assertEquals(1, leaderBoard.size());

        //remove added entry
        assertTrue(manager.removeLeaderBoard(now));

        leaderBoard = manager.getLeaderBoard("TESTADDGAME", "TESTADDSTREAM", 3);
        assertEquals(0, leaderBoard.size());

    }

    @Test
    public void AddGame1Entry() {

        Game1 entry = new Game1("hello", new String[]{"hi, hia, biy"}, "hello bob how are you", "TESTADDSTREAM");

        assertTrue(manager.addGame1Entry(entry));

        List<Game1> questions = manager.getGame1Q("TESTADDSTREAM");
        assertEquals(1, questions.size());

        //remove added entry
        assertTrue(manager.removeGame1(entry));

        questions = manager.getGame1Q("TESTADDGAME");
        assertEquals(0, questions.size());

    }

    @Test
    public void AddGame2Entry() {

        Game2 entry = new Game2("hello", "  A way of saying hi", "TESTADDSTREAM");

        assertTrue(manager.addGame2Entry(entry));

        List<Game2> questions = manager.getGame2Q("TESTADDSTREAM");
        assertEquals(1, questions.size());

        //remove added entry
        assertTrue(manager.removeGame2(entry));

        questions = manager.getGame2Q("TESTADDGAME");
        assertEquals(0, questions.size());

    }
    @Test
    public void AddGame3Entry() {

        Game3 entry = new Game3("How are you?", "Good", new String[]{"Bad", "Okay", "Fine"}, "TESTADDSTREAM");

        assertTrue(manager.addGame3Entry(entry));

        List<Game3> questions = manager.getGame3Q("TESTADDSTREAM");
        assertEquals(1, questions.size());

        //remove added entry
        assertTrue(manager.removeGame3(entry));

        questions = manager.getGame3Q("TESTADDGAME");
        assertEquals(0, questions.size());

    }

    @AfterAll
    public void Disconnect() {
        assertTrue(manager.disconnect());
    }
}
