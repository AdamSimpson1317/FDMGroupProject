// This class was created by Adam Sanderson.
// It is used to create a Main Menu GUI which the user can navigate to choose a stream to research, choose which
// game to play, and choose the option to view the leaderboards.

package csc2033.team29.fdm;

import com.mysql.jdbc.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Calendar;

public class MainMenu extends JFrame{
    // create variables to be used throughout running of the program
    private static String chosenStream;
    private static String chosenGame;
    private static String chosenName;

    public static void displayMenu(){
        // set values to null when they are ran for first time
        chosenStream="";
        chosenGame="";
        chosenName="";

        // create frame and set values needed
        JFrame menuFrame = new JFrame("Main Menu");
        menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuFrame.setSize(825, 600);
        menuFrame.setResizable(false);

        // create panels that will be used for the main menu, and set colours
        JPanel panel = new JPanel();
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.white);
        JPanel welcomeCard = new JPanel();
        welcomeCard.setBackground(Color.white);
        JPanel streamCard = new JPanel();
        streamCard.setBackground(Color.darkGray);
        JPanel gameCard = new JPanel();
        gameCard.setBackground(Color.darkGray);
        JPanel nameCard = new JPanel();
        nameCard.setBackground(Color.white);
        JPanel leaderboardCard = new JPanel();
        leaderboardCard.setBackground(Color.white);
        JPanel dateSelectCard = new JPanel();
        leaderboardCard.setBackground(Color.white);
        JPanel commandPanel1 = new JPanel();
        commandPanel1.setBackground(Color.white);
        JPanel commandPanel2 = new JPanel();
        commandPanel2.setBackground(Color.darkGray);

        // create buttons and elements for most panels
        JLabel titleLabel = new JLabel("More information can be found at: https://www.fdmgroup.com");
        JButton stream1 = new JButton("Software Testing");
        stream1.setPreferredSize(new Dimension(250, 300));
        JButton stream2 = new JButton("Business Intelligence");
        stream2.setPreferredSize(new Dimension(250, 300));
        JButton stream3 = new JButton("Technical Operations");
        stream3.setPreferredSize(new Dimension(250, 300));
        JButton game1 = new JButton("Fill In The Blanks");
        game1.setPreferredSize(new Dimension(250, 300));
        JButton game2 = new JButton("Match The Key Words");
        game2.setPreferredSize(new Dimension(250, 300));
        JButton game3 = new JButton("Test Your Knowledge Quiz");
        game3.setPreferredSize(new Dimension(250, 300));
        JButton playGame = new JButton("Play The Game!");
        JButton backButton = new JButton("Choose a different stream");
        backButton.setPreferredSize(new Dimension(500, 100));
        JButton beginButton = new JButton("Begin!");
        beginButton.setPreferredSize(new Dimension(200, 100));
        JButton leaderboardButton = new JButton("View the leader boards");
        leaderboardButton.setPreferredSize(new Dimension(200, 100));
        JButton resetButton = new JButton("Return to Title Screen");
        resetButton.setPreferredSize(new Dimension(200, 100));

        // elements needed for leader board panel
        String[] streams = {"Software Testing", "Business Intelligence", "Technical Operations"};
        String[] games = {"Fill In The Blanks", "Match The Key Words", "Test Your Knowledge Quiz"};
        String[] filters = {"Top 5", "Top 10", "Top 20", "By Date"};
        final JComboBox<String> streamsBox = new JComboBox<>(streams);
        final JComboBox<String> gamesBox = new JComboBox<>(games);
        final JComboBox<String> filterBox = new JComboBox<>(filters);
        JButton showBoardButton = new JButton("Show leaderboards");

        // elements needed for the name panel
        JLabel nameLabel = new JLabel("Please enter your initials:");
        JTextField nameText = new JTextField(20);

        // create card layout that will be used to display panels
        CardLayout cardLayout = new CardLayout();
        panel.setLayout(cardLayout);
        bottomPanel.setLayout(cardLayout);

        // add elements to each card/panel
        titlePanel.add(titleLabel);

        // create the brand logo image and add to be displayed on the title screen
        ImageIcon image = new ImageIcon("src/main/java/csc2033/team29/fdm/fdm-logo-black.jpg");
        JLabel welcomeLabel = new JLabel(new ImageIcon(image.getImage()));
        welcomeCard.add(welcomeLabel);

        // add elements to card panels
        streamCard.add(stream1);
        streamCard.add(stream2);
        streamCard.add(stream3);

        gameCard.add(game1);
        gameCard.add(game2);
        gameCard.add(game3);
        gameCard.add(backButton);

        nameCard.add(nameLabel);
        nameCard.add(nameText);
        nameCard.add(playGame);

        leaderboardCard.add(streamsBox);
        leaderboardCard.add(gamesBox);
        leaderboardCard.add(filterBox);
        leaderboardCard.add(showBoardButton);

        // create and add elements for user to filter the leader board by date
        JLabel dateLabel = new JLabel("Please select a day, month and year:");
        Integer[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
        Integer[] years = {2020, 2021};
        final JComboBox<Integer> daysBox = new JComboBox<>(days);
        final JComboBox<Integer> monthsBox = new JComboBox<>(months);
        final JComboBox<Integer> yearsBox = new JComboBox<>(years);
        JButton showDatesButton = new JButton("Show the leader board");
        dateSelectCard.add(dateLabel);
        dateSelectCard.add(daysBox);
        dateSelectCard.add(monthsBox);
        dateSelectCard.add(yearsBox);
        dateSelectCard.add(showDatesButton);

        // add the rotating challenge to the title screen
        JLabel challengeLabel = new JLabel(getChallenge());
        commandPanel1.add(challengeLabel);
        commandPanel1.add(beginButton);
        commandPanel2.add(leaderboardButton);
        commandPanel2.add(resetButton);

        // add card panels to panels using card layout
        panel.add(welcomeCard, "welcome");
        panel.add(streamCard,"streams");
        panel.add(gameCard, "games");
        panel.add(nameCard, "name");
        panel.add(leaderboardCard, "leader board");
        panel.add(dateSelectCard, "select date");
        bottomPanel.add(commandPanel1, "first commands");
        bottomPanel.add(commandPanel2, "second commands");

        // code for each button's action listeners
        beginButton.addActionListener(e -> {
            cardLayout.show(panel, "streams");
            cardLayout.show(bottomPanel, "second commands");
        });
        resetButton.addActionListener(e -> {
            // reset values stored in variables
            chosenStream="";
            chosenGame="";
            chosenName="";
            // return to title screen panels
            cardLayout.show(panel, "welcome");
            cardLayout.show(bottomPanel, "first commands");
        });
        leaderboardButton.addActionListener(e -> {
            cardLayout.show(panel, "leader board");
        });


        stream1.addActionListener(e -> {
            // set value of the chosen stream and move on to game select panel
            chosenStream = "Software Testing";
            cardLayout.show(panel, "games");
        });
        stream2.addActionListener(e -> {
            chosenStream = "Business Intelligence";
            cardLayout.show(panel, "games");
        });
        stream3.addActionListener(e -> {
            chosenStream = "Technical Operations";
            cardLayout.show(panel, "games");
        });

        game1.addActionListener(e -> {
            // store game selected and move on to panel where user can enter their name
            chosenGame = "game1";
            cardLayout.show(panel, "name");
        });
        game2.addActionListener(e -> {
            chosenGame = "game2";
            cardLayout.show(panel, "name");
        });
        game3.addActionListener(e -> {
            chosenGame = "game3";
            cardLayout.show(panel, "name");
        });
        backButton.addActionListener(e -> {
            chosenStream = "";
            chosenGame = "";
            cardLayout.show(panel, "streams");
        });
        playGame.addActionListener(e -> {
            chosenName = nameText.getText();
            // if name is blank, then user is registered as anonymous in the database
            if (chosenName.equals("")){
                chosenName = "Anonymous";
            }
            // code to check the user's entry does not exceed 100 characters
            if (chosenName.length()>99){
                // if name is too long, only take first 99 characters
                chosenName = chosenName.substring(0,99);
            }
            // load games depending on what was chosen in the menus
            switch (chosenGame) {
                case "game1":
                    // opens the game in a new frame
                    new FillInBlanks(chosenStream, chosenName);
                    // closes the menu frame
                    menuFrame.dispose();
                    break;
                case "game2":
                    new Match(chosenStream, chosenName);
                    menuFrame.dispose();
                    break;
                case "game3":
                    new Quiz("FDM Quiz", chosenStream, chosenName);
                    menuFrame.dispose();
                    break;
            }
        });



        // buttons actions to show leaderboard
        showBoardButton.addActionListener(e -> {
            // retrieve data from the combo boxes
            chosenStream = streamsBox.getItemAt(streamsBox.getSelectedIndex());
            chosenGame = gamesBox.getItemAt(gamesBox.getSelectedIndex());
            String chosenFilter = filterBox.getItemAt(filterBox.getSelectedIndex());
            // change variable to match database field name
            switch (chosenGame) {
                case "Fill In The Blanks":
                    chosenGame = "game1";
                    break;
                case "Match The Key Words":
                    chosenGame = "game2";
                    break;
                case "Test Your Knowledge Quiz":
                    chosenGame = "game3";
                    break;
            }
            switch (chosenFilter) {
                // run different code, depending on the filter that the user chooses for the database
                case "Top 5":
                    Leaderboard.createLeaderboardMax(chosenStream, chosenGame, 5);
                    break;
                case "Top 10":
                    Leaderboard.createLeaderboardMax(chosenStream, chosenGame, 10);
                    break;
                case "Top 20":
                    Leaderboard.createLeaderboardMax(chosenStream, chosenGame, 20);
                    break;
                case "By Date":
                    cardLayout.show(panel, "select date");
                    break;
            }
        });

        showDatesButton.addActionListener(e -> {
            int chosenDay = daysBox.getItemAt(daysBox.getSelectedIndex());
            int chosenMonth = monthsBox.getItemAt(monthsBox.getSelectedIndex());
            int chosenYear = yearsBox.getItemAt(yearsBox.getSelectedIndex());
            // create a new timestamp for the date that will be used to filter results
            Timestamp chosenDate = new Timestamp(chosenYear-1900, chosenMonth-1, chosenDay, 0, 0,0,0);
            // call leader board function that uses date as a filter
            Leaderboard.createLeaderboardDate(chosenStream, chosenGame, chosenDate);

        });

        // add panels and display the menu frame
        menuFrame.add(titlePanel, BorderLayout.NORTH);
        menuFrame.add(panel, BorderLayout.CENTER);
        menuFrame.add(bottomPanel, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }

    private static String getChallenge(){
        // store the day of the month, depending on the calendar
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // return different strings depending on the day of the month
        if (dayOfMonth<5){
            return "Challenge: Play a game of Fill In The Blanks!";
        } else if (dayOfMonth<10){
            return "Challenge: Play a game of Match The Keywords!";
        } else if (dayOfMonth<15) {
            return "Challenge: Test your Knowledge in the quiz!";
        } else if (dayOfMonth<20) {
            return "Challenge: Play a game for all 3 streams!";
        } else if (dayOfMonth<25) {
            return "Challenge: Play all 3 games for one stream!";
        } else {
            return "Challenge: Score full marks in the quiz!";
        }
    }

    public static void main(String[] args) {
    }
}
