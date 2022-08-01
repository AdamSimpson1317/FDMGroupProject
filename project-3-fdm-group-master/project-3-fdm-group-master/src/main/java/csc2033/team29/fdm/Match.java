package csc2033.team29.fdm;

import csc2033.team29.fdm.DBConnections.DBManager;
import csc2033.team29.fdm.DBConnections.Data.Game2;
import csc2033.team29.fdm.DBConnections.Data.LeaderEntry;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Match extends JFrame implements MouseListener, ActionListener {

    // create variables that will be used throughout the code
    int resetCount = 0;
    int userScore = 50;
    String initials;
    String stream;

    List<Game2> wordsNDescriptions;

    JPanel pl = new JPanel();
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JButton b1;
    JLabel[] labels = new JLabel[2];

    String[] words = new String[4];
    String[] descriptions = new String[4];
    String[] correctAnswers = new String[4];
    String[] pickedWords = new String[4];
    String[] pickedDefinitions = new String[4];

    Border blackLine = BorderFactory.createLineBorder(Color.black);
    Border whiteLine = BorderFactory.createLineBorder(Color.white);

    // method that starts the program
    public Match(String stream, String initials) {
        runGame(stream, initials);
    }

    public static void main(String[] args) {
        String pickedStream = "Software Testing";
        String initials = "L.J";
        new Match(pickedStream, initials);
    }

    // works when main menu button is pressed 
    @Override
    public void actionPerformed(ActionEvent e) {
        MainMenu.displayMenu();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // creates a copy of the label that was pressed
        JLabel label = (JLabel) e.getSource();

        boolean found = false;

        /* simple checks to see if the clicked label was correctly clicked (works if it has not been clicked before and
        is in the correct order (word -> description)) */
        if(labels[0] == null){
            if(Arrays.stream(pickedWords).anyMatch(label.getText()::equals)){
                found = true;
            }
            if(!found){
                if(Arrays.stream(words).anyMatch(label.getText()::equals)){
                    label.setBorder(whiteLine);
                    labels[0] = label;
                }
            }
        } else if(labels[1] == null){
            if(Arrays.stream(pickedDefinitions).anyMatch(label.getText()::equals)){
                found = true;
            }
            if(!found){
                if(Arrays.stream(descriptions).anyMatch(label.getText()::equals)){
                    label.setBorder(whiteLine);
                    labels[1] = label;
                }
            }
        }

        // when both the word and description are clicked checks if it is a correct answer
        if(labels[0] != null && labels[1] != null){
            String userPick = labels[0].getText() + '-' + labels[1].getText();
            if(Arrays.stream(correctAnswers).anyMatch(userPick::equals)){
                for(int i = 0; i <= 3; i++){
                    if(pickedWords[i] == null){
                        pickedWords[i] = labels[0].getText();
                        pickedDefinitions[i] = labels[1].getText();

                        if (i == 3){
                            userScore += 50;
                            resetCount++;

                            prepareLabels();
                            labels[0] = null; labels[1] = null;
                            for(int j = 0; j <= 3; j++){
                                pickedWords[j] = null;
                                pickedDefinitions[j] = null;
                            }
                            found = true;
                        } else {
                            userScore += 50;

                            labels[0] = null; labels[1] = null;
                            found = true;
                        }
                        break;
                    }
                }
            }

            // if the answer isn't correct it unmarks the labels
            if(!found){
                userScore -= 50;

                labels[0].setBorder(blackLine); labels[1].setBorder(blackLine);
                labels[0] = null; labels[1] = null;
            }
        }
    }

    // whenever a mouse cursor enters a label it marks it
    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setBackground(Color.lightGray);
        label.setOpaque(true);
    }

    // when the mouse cursor exits a label it unmarks it
    @Override
    public void mouseExited(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        label.setBackground(Color.white);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    public void runGame (String userStream, String userInitials){

        // create a dbmanager object and connect to the database
        DBManager db = new DBManager();
        db.connect();

        /* if the passed information is null (meaning the program is ready to show user score) sends score to the leaderboard
        else, it initialises a panel and calls the function that initialises labels */
        if(userStream == null && userInitials == null){
            LeaderEntry entry = new LeaderEntry();
            entry.setInitials(initials);
            entry.setStream(stream);
            entry.setGame("Game2");
            entry.setScore(userScore);

            db.addLeaderBoardEntry(entry);
        } else {
            initials = userInitials;
            stream = userStream;

            getContentPane().add(pl);
            setSize(825, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            wordsNDescriptions = db.getGame2Q(stream);

            prepareLabels();

            pl.setLayout(null);
            pl.setVisible(true);
        }
        setVisible(true);
    }

    public void prepareLabels(){
        // if there are panels displayed already removes them
        if(l1 != null) {
            pl.remove(l1); pl.remove(l2); pl.remove(l3); pl.remove(l4); pl.remove(l5); pl.remove(l6); pl.remove(l7); pl.remove(l8);
        }

        // checks if there are enough words left in the database
        if (wordsNDescriptions.size() < 4) {

            // if theres not, creates a label that displays user score as well as a button to get back to main menu
            l9 = new JLabel("Score: " + userScore, SwingConstants.CENTER);
            l9.setBounds(50, 20, 100, 35);
            l9.addMouseListener(this);
            l9.setBackground(Color.white);
            l9.setBorder(blackLine);

            b1 = new JButton("Back to main menu");
            b1.setBounds(175, 20, 200, 35);
            b1.addActionListener(this);
            b1.setBackground(Color.white);
            b1.setBorder(blackLine);

            pl.add(l9);
            pl.repaint();
            pl.add(b1);

            // calls rungame to finish up the game and send user score to database
            runGame(null, null);

            // if there are still available words and descriptions keeps running
        } else {

            // creates a new random object
            Random rand = new Random();

            // randomly picks from the database and deletes the words after picked
            for (int i = 0; i <= 3; i++) {
                int randomIndex = rand.nextInt(wordsNDescriptions.size());
                Game2 randomElement = wordsNDescriptions.get(randomIndex);
                words[i] = randomElement.getWord();
                descriptions[i] = randomElement.getDescription();
                correctAnswers[i] = words[i] + "-" + descriptions[i];
                wordsNDescriptions.remove(randomIndex);

            }

            // after the words are picked randomises their positions
            for (int i = 0; i <= 3; i++) {
                int randomIndex = rand.nextInt(words.length);
                String temp = words[randomIndex];
                words[randomIndex] = words[i];
                words[i] = temp;

                randomIndex = rand.nextInt(descriptions.length);
                temp = descriptions[randomIndex];
                descriptions[randomIndex] = descriptions[i];
                descriptions[i] = temp;
            }

            // initialises all labels with words and descriptions
            l1 = new JLabel(words[0], SwingConstants.CENTER);
            l1.setBounds(50, 50, 150, 75);
            l1.addMouseListener(this);
            l1.setBackground(Color.white);
            l1.setBorder(blackLine);

            l2 = new JLabel(descriptions[0], SwingConstants.CENTER);
            l2.setBounds(280, 50, 480, 75);
            l2.addMouseListener(this);
            l2.setBackground(Color.white);
            l2.setBorder(blackLine);

            l3 = new JLabel(words[1], SwingConstants.CENTER);
            l3.setBounds(50, 157, 150, 75);
            l3.addMouseListener(this);
            l3.setBackground(Color.white);
            l3.setBorder(blackLine);

            l4 = new JLabel(descriptions[1], SwingConstants.CENTER);
            l4.setBounds(280, 157, 480, 75);
            l4.addMouseListener(this);
            l4.setBackground(Color.white);
            l4.setBorder(blackLine);

            l5 = new JLabel(words[2], SwingConstants.CENTER);
            l5.setBounds(50, 265, 150, 75);
            l5.addMouseListener(this);
            l5.setBackground(Color.white);
            l5.setBorder(blackLine);

            l6 = new JLabel(descriptions[2], SwingConstants.CENTER);
            l6.setBounds(280, 265, 480, 75);
            l6.addMouseListener(this);
            l6.setBackground(Color.white);
            l6.setBorder(blackLine);

            l7 = new JLabel(words[3], SwingConstants.CENTER);
            l7.setBounds(50, 372, 150, 75);
            l7.addMouseListener(this);
            l7.setBackground(Color.white);
            l7.setBorder(blackLine);

            l8 = new JLabel(descriptions[3], SwingConstants.CENTER);
            l8.setBounds(280, 372, 480, 75);
            l8.addMouseListener(this);
            l8.setBackground(Color.white);
            l8.setBorder(blackLine);

            pl.add(l1);
            pl.add(l2);
            pl.add(l3);
            pl.add(l4);
            pl.add(l5);
            pl.add(l6);
            pl.add(l7);
            pl.add(l8);
            pl.repaint();
        }
    }
}

