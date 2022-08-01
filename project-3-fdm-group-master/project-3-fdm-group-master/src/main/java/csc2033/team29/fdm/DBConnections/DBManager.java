package csc2033.team29.fdm.DBConnections;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import csc2033.team29.fdm.DBConnections.Data.*;

import javax.swing.*;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

/* This class was created by Harry Brettell.
   it is used as an intimidate step between the user and the database
*/

public class DBManager {
    private static Connection conn;
    private static PreparedStatement stmt;

    private static Session sshSession;

    /**
     * This will connect the manager to the database.
     * @return whether it has succeed
     */
    public boolean connect() {

        try {
            if (sshSession != null && sshSession.isConnected()) {

                if(conn == null || conn.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    //need to create an ssh tunnel to 'linux.cs.ncl.ac.uk' port forwarding 'cs-db.ncl.ac.uk:3306' to 'localhost:3307'
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/t2033t29", "t2033t29", "VoteDye(8Jay");
                    conn.setAutoCommit(false);
                }
            } else {
                JFrame frame = new JFrame("Login");
                LoginDialog ld = new LoginDialog(frame , this);
                ld.setAlwaysOnTop(true);
                ld.setVisible(true);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Check SSH tunnel settings is set up to forward localhost:3307 to the database");
        }

        return conn != null;
    }

    /**
     * this will create an sshtunnel for the database to connect to
     * @param user username for the remote
     * @param pwd password for the remote
     * @return whether it has succeed
     */
    public boolean connectSSH(String user, String pwd) {
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession(user, "linux.cs.ncl.ac.uk", 22);
            sshSession.setPassword(pwd);
            localUserInfo lui = new localUserInfo();
            sshSession.setUserInfo(lui);
            sshSession.connect();
            sshSession.setPortForwardingL(3307, "cs-db.ncl.ac.uk", 3306);

            return sshSession.isConnected();
        } catch (JSchException e) {
            return false;
        }
    }
    /**
     * small object used to hold info about sshconnection
     */
    class localUserInfo implements UserInfo {
        String passwd;
        public String getPassword(){ return passwd; }
        public boolean promptYesNo(String str){return true;}
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){return true; }
        public boolean promptPassword(String message){return true;}
        public void showMessage(String message){}
    }


    /**
     * this will disconnect the manager from the database.
     * @return will return true if successful
     */
    public boolean disconnect() {
        boolean attempt = true;
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {
            attempt = false;
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            attempt = false;
        }
        if(sshSession != null) {
            sshSession.disconnect();
        }
        return attempt;
    }

    /**
     * function to check the conectivity
     * @return true if database is connected, otherwise false
     */
    public boolean isConnected() {
        try {
            return !conn.isClosed();
        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will return a result set of all of the data in the leaderboard, ordered by datetime, that
     * correspond to the specific game and stream.
     * @param game the game that is targeted
     * @param stream the stream that is targeted
     * @return the result set of the query
     * @throws SQLException if sql execution fails
     */
    private ResultSet getLeaderBoardResults(String game, String stream) throws SQLException {
        String sql = "SELECT * FROM LeaderBoard WHERE Game=? AND Stream=? ORDER BY Score DESC ";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, game);
        stmt.setString(2, stream);

        if(!stmt.execute()) {
            throw new SQLException("didn't work");
        }
        return stmt.getResultSet();
    }

    /**
     * creates a leaderboard entry from the current line of the result set
     * @param rs the resultSet of data received from the database
     * @return a new leader entry
     * @throws SQLException when it fails at getting the information from the resultSet
     */
    private LeaderEntry createEntry(ResultSet rs) throws SQLException {
        LeaderEntry entry = new LeaderEntry();
        entry.setInitials(rs.getString("Initials"));
        entry.setScore(rs.getInt("Score"));
        entry.setDateTime(rs.getTimestamp("DateTime"));
        entry.setGame(rs.getString("Game"));
        entry.setStream(rs.getString("Stream"));

        return entry;
    }

    /**
     * create a list of leaderEntries from the leaderboard of everyone who entered a score after the given timestamp
     * @param game the targeted game
     * @param stream the targeted stream
     * @param upto the timestamp to compare everyone's entry against
     * @return a list of accepted leaderEntries
     */
    public List<LeaderEntry> getLeaderBoard(String game, String stream, Timestamp upto) {
        try {
            ResultSet rs = getLeaderBoardResults(game, stream);
            List<LeaderEntry> leaderBoard = new ArrayList<>();
            while (rs.next()) {

                if (!rs.getTimestamp("DateTime").before(upto)) {
                    leaderBoard.add(createEntry(rs));
                }
            }
            return leaderBoard;
        } catch (SQLException se) {
            return null;
        }

    }

    /**
     * create a list of leaderEntries from all the data from the leaderboard upto a given maxSize.
     * @param game the targeted game
     * @param stream the targeted stream
     * @param maxCount maximum number of entries to include in the final list
     * @return a list of accepted leaderEntries
     */
    public List<LeaderEntry> getLeaderBoard(String game, String stream, int maxCount) {
        try {
            ResultSet rs = getLeaderBoardResults(game, stream);
            List<LeaderEntry> leaderBoard = new ArrayList<>();

            while (rs.next() && leaderBoard.size() < maxCount) {
                leaderBoard.add(createEntry(rs));
            }
            return leaderBoard;
        } catch (SQLException se) {
            return null;
        }

    }

    /**
     * this will add the given leaderEntry to the database
     * @param entry the entry to add to the database
     * @return true if successful
     */
    public boolean addLeaderBoardEntry(LeaderEntry entry) {
        String sql = "INSERT INTO LeaderBoard (Initials, Score, DateTime, Stream, Game) VALUES (?, ?, ?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getInitials());
            stmt.setInt(2, entry.getScore());
            stmt.setTimestamp(3, entry.getDateTime());
            stmt.setString(4, entry.getStream());
            stmt.setString(5, entry.getGame());

            stmt.execute();
            conn.commit();


            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * will remove a leaderEntry from the database when it has a given timestamp.
     * @param dateTime the timestamp of the leaderEntry that should be removed
     * @return true if successful
     */
    public boolean removeLeaderBoard(Timestamp dateTime) {
        String sql = "DELETE FROM LeaderBoard WHERE DateTime=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, dateTime);

            stmt.execute();
            conn.commit();

            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will retrieve all of the questions in game 1 for a given stream and return them in a list
     * @param stream the targeted stream for the questions
     * @return a list of all of the questions
     */
    public List<Game1> getGame1Q(String stream) {
        String sql = "SELECT * FROM Game1 WHERE Stream=?";
        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, stream);

            if(!stmt.execute()) {
                throw new SQLException("didn't work");
            }
            ResultSet rs = stmt.getResultSet();
            List<Game1> questions = new ArrayList<>();
            while(rs.next()) {
                Game1 question = new Game1();
                question.setCorrectWord(rs.getString("Answer"));
                question.setWrongWords(rs.getString("Words").split("\t"));
                question.setTextBlock(rs.getString("TextBlock"));
                question.setStream(rs.getString("Stream"));

                questions.add(question);
            }

            return questions;

        } catch (SQLException se) {
            return null;
        }
    }

    /**
     * Will retrieve all of the questions in game 2 for a given stream and return them in a list
     * @param stream the targeted stream for the questions
     * @return a list of all of the questions
     */
    public List<Game2> getGame2Q(String stream) {
        String sql = "SELECT * FROM Game2 WHERE Stream=?";
        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, stream);

            if(!stmt.execute()) {
                throw new SQLException("didn't work");
            }
            ResultSet rs = stmt.getResultSet();
            List<Game2> questions = new ArrayList<>();
            while(rs.next()) {
                Game2 question = new Game2();
                question.setWord(rs.getString("Word"));
                question.setDescription(rs.getString("Description"));
                question.setStream(rs.getString("Stream"));

                questions.add(question);
            }

            return questions;

        } catch (SQLException se) {
            return null;
        }
    }

    /**
     * Will retrieve all of the questions in game 3 for a given stream and return them in a list
     * @param stream the targeted stream for the questions
     * @return a list of all of the questions
     */
    public List<Game3> getGame3Q(String stream) {
        String sql = "SELECT * FROM Game3 WHERE Stream = ?";

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, stream);

            if(!stmt.execute()) {
                throw new SQLException("didn't work");
            }
            ResultSet rs = stmt.getResultSet();
            List<Game3> questions = new ArrayList<>();
            while(rs.next()) {
                Game3 question = new Game3();
                question.setQuestion(rs.getString("Question"));
                question.setAnswer(rs.getString("CorrectAnswer"));
                question.setWrongAnswers(rs.getString("WrongAnswers").split("\t"));
                question.setStream(rs.getString("Stream"));


                questions.add(question);
            }
            return questions;

        } catch (SQLException se) {
            return null;
        }

    }

    /**
     * Will add a given question for game 1 and add it to the database
     * @param entry the question to add to the database
     * @return true if successful
     */
    public boolean addGame1Entry(Game1 entry) {
        String sql = "INSERT INTO Game1 (Answer, Words, TextBlock, Stream) VALUES (?, ?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getCorrectWord());
            stmt.setString(2, String.join("\t", entry.getWrongWords()));
            stmt.setString(3, entry.getTextBlock());
            stmt.setString(4, entry.getStream());


            stmt.execute();
            conn.commit();


            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will add a given question for game 2 and add it to the database
     * @param entry the question to add to the database
     * @return true if successful
     */
    public boolean addGame2Entry(Game2 entry) {
        String sql = "INSERT INTO Game2 (Word, Description, Stream) VALUES (?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getWord());
            stmt.setString(2, entry.getDescription());
            stmt.setString(3, entry.getStream());


            stmt.execute();
            conn.commit();


            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will add a given question for game 3 and add it to the database
     * @param entry the question to add to the database
     * @return true if successful
     */
    public boolean addGame3Entry(Game3 entry) {
        String sql = "INSERT INTO Game3 (Question, CorrectAnswer, WrongAnswers, Stream) VALUES (?, ?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getQuestion());
            stmt.setString(2, entry.getAnswer());
            stmt.setString(3, String.join("\t", entry.getWrongAnswers()));
            stmt.setString(4, entry.getStream());


            stmt.execute();
            conn.commit();


            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will remove a given question for game 1 and add it to the database
     * @param entry the question to remove from a database
     * @return true if successful
     */
    public boolean removeGame1(Game1 entry) {
        String sql = "DELETE FROM Game1 WHERE Stream=? AND TextBlock=? AND Answer=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getStream());
            stmt.setString(2, entry.getTextBlock());
            stmt.setString(3, entry.getCorrectWord());

            stmt.execute();
            conn.commit();

            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will remove a given question for game 2 and add it to the database
     * @param entry the question to remove from a database
     * @return true if successful
     */
    public boolean removeGame2(Game2 entry) {
        String sql = "DELETE FROM Game2 WHERE Stream=? AND Word=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getStream());
            stmt.setString(2, entry.getWord());

            stmt.execute();
            conn.commit();

            return true;

        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Will remove a given question for game 3 and add it to the database
     * @param entry the question to remove from a database
     * @return true if successful
     */
    public boolean removeGame3(Game3 entry) {
        String sql = "DELETE FROM Game3 WHERE Stream=? AND Question=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entry.getStream());
            stmt.setString(2, entry.getQuestion());

            stmt.execute();
            conn.commit();

            return true;

        } catch (SQLException se) {
            return false;
        }
    }
}
