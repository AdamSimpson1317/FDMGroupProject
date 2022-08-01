// This class was created by Adam Sanderson.
// The purpose of this class is to display a leader board for the stream, game and filter that the user has
// chosen. There are two main functions: one for filtering by top number of scores, and one for filtering by date.

package csc2033.team29.fdm;

import csc2033.team29.fdm.DBConnections.DBManager;
import csc2033.team29.fdm.DBConnections.Data.LeaderEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Leaderboard extends JFrame {

    // method used to show leader board, with filter based on top scores
    public static void createLeaderboardMax(String chosenStream, String chosenGame, int chosenCount) {

        // fetch data from the database as array list
        DBManager dbManager = new DBManager();
        dbManager.connect();
        ArrayList<LeaderEntry> allData;
        allData = (ArrayList<LeaderEntry>) dbManager.getLeaderBoard(chosenGame, chosenStream, chosenCount);

        // create frame to display table
        JFrame leaderboardFrame = new JFrame("Leader boards");
        leaderboardFrame.setSize(500,500);

        // create data model of table
        String[] columns = {"Initials", "Score", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable leaderboardTable = new JTable(tableModel);

        // loop to add rows to the table model
        for (LeaderEntry allDatum : allData) {
            String initial = allDatum.getInitials();
            int score = allDatum.getScore();
            Timestamp date = allDatum.getDateTime();

            // create object array for the row and add to data model
            Object[] tableData = {initial, score, date};
            tableModel.addRow(tableData);
        }

        // create scroll pane and display table
        JScrollPane leaderboardSP = new JScrollPane(leaderboardTable);
        leaderboardFrame.add(leaderboardSP);
        leaderboardFrame.setVisible(true);
    }

    // method used to show leader board, with filter based on date
    public static void createLeaderboardDate(String chosenStream, String chosenGame, Timestamp chosenDate) {

        // fetch data from the database as array list, depending on date passed
        DBManager dbManager = new DBManager();
        dbManager.connect();
        ArrayList<LeaderEntry> allData;
        allData = (ArrayList<LeaderEntry>) dbManager.getLeaderBoard(chosenGame, chosenStream, chosenDate);

        // create frame to display table
        JFrame leaderboardFrame = new JFrame("Leader boards");
        leaderboardFrame.setSize(500,500);

        // create data model of table
        String[] columns = {"Initials", "Score", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable leaderboardTable = new JTable(tableModel);

        // loop to add rows to the table model
        for (LeaderEntry allDatum : allData) {
            String initial = allDatum.getInitials();
            int score = allDatum.getScore();
            Timestamp date = allDatum.getDateTime();

            // create object array for the row and add to data model
            Object[] tableData = {initial, score, date};
            tableModel.addRow(tableData);
        }

        // create scroll pane and display table
        JScrollPane leaderboardSP = new JScrollPane(leaderboardTable);
        leaderboardFrame.add(leaderboardSP);
        leaderboardFrame.setVisible(true);
    }

    public static void main(String[] args) {
    }
}
