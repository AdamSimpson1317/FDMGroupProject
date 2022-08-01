package csc2033.team29.fdm.DBConnections.Data;

import java.sql.Timestamp;
import java.time.Instant;

/* this class was created by Harry Brettell
   it acts as a storage for the data involved in a leaderboard entry
 */
public class LeaderEntry {

    private String initials;
    private int score;
    private Timestamp dateTime;
    private String game;
    private String stream;

    public LeaderEntry(String initials, int score, Timestamp dateTime, String game, String stream) {
        this.initials = initials;
        this.score = score;
        this.dateTime = dateTime;
        this.game = game;
        this.stream = stream;
    }

    public LeaderEntry() {
        dateTime = Timestamp.from(Instant.now());
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }


    @Override
    public String toString() {
        return "LeaderEntry{\n" +
                "   initials='" + initials + "'\n" +
                "   score   =" + score + '\n' +
                "   dateTime=" + dateTime + '\n' +
                "   game    ='" + game + "'\n" +
                "   stream  ='" + stream + "'\n" +
                '}';
    }

    /*
        Example of how it would be used
        LeaderEntry entry = new LeaderEntry()    <- will automatically set datetime to this instant
        entry.setInitials(initials)
        entry.setScore(score)
        entry.setGame(game)
        entry.setStream(stream)

        dbmanager.addLeaderBoardEntry

     */
}
