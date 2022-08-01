// This class was created by Jake Matias
// This class is used to create the GUI for Game 1 (Fill in the blanks) and lets the user see the results of
// the game after answering all the questions.

package csc2033.team29.fdm;

import csc2033.team29.fdm.DBConnections.DBManager;
import csc2033.team29.fdm.DBConnections.Data.Game1;
import csc2033.team29.fdm.DBConnections.Data.LeaderEntry;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class FillInBlanks extends JFrame{

    // array that stores the answers the user has selected
    String[] currentAnswers = new String[5];
    // array to store the correct answers for each question
    String[] correctAnswers = new String[5];
    // integer to count the correct answers the user has submitted
    int answerCount = 0;

    // initials of the user to be added to the leaderboard
    String userInitials;

    // name of the stream selected
    String streamName;

    // list of questions and answers from the database
    List<Game1> questionsAndAnswers;

    // the card that is currently shown
    private int currentCard = 1;

    // card layouts
    private CardLayout card1;
    private CardLayout card2;

    // instance of the database manager
    DBManager dbManager = new DBManager();

    /**
     * Main method that gets called which displays the GUI
     * @param stream one of the three streams
     * @param initials initials of the user that gets put into the database
     */
    public FillInBlanks(String stream, String initials){

        // gets the questions and answers of a stream from the database
        getFromDatabase(stream);
        setTitle("Fill Blanks");

        userInitials = initials;
        streamName = stream;

        // sets frame size
        setSize(new Dimension(825, 600));
        setMinimumSize(new Dimension(825, 600));
        setVisible(true);

        // new card panel with card layout
        JPanel titlePanel = new JPanel();
        card1 = new CardLayout();
        titlePanel.setLayout(card1);

        // panels displayed at the top of the screen
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();

        JLabel label1 = new JLabel("Page 1");
        JLabel label2 = new JLabel("Page 2");
        JLabel label3 = new JLabel("Page 3");
        JLabel label4 = new JLabel("Page 4");
        JLabel label5 = new JLabel("Page 5");

        p1.add(label1);
        p2.add(label2);
        p3.add(label3);
        p4.add(label4);
        p5.add(label5);

        titlePanel.add(p1, "1");
        titlePanel.add(p2, "2");
        titlePanel.add(p3, "3");
        titlePanel.add(p4, "4");
        titlePanel.add(p5, "5");

        // Creates question panel and sets the layout to card layout
        JPanel questionPanel = new JPanel();
        card2 = new CardLayout();
        questionPanel.setLayout(card2);

        // question panels displayed at the center of the screen
        JPanel q1 = new JPanel();
        JPanel q2 = new JPanel();
        JPanel q3 = new JPanel();
        JPanel q4 = new JPanel();
        JPanel q5 = new JPanel();

        q1.setBackground(Color.lightGray);
        q2.setBackground(Color.lightGray);
        q3.setBackground(Color.lightGray);
        q4.setBackground(Color.lightGray);
        q5.setBackground(Color.lightGray);

        questionPanel.add(q1, "1");
        questionPanel.add(q2, "2");
        questionPanel.add(q3, "3");
        questionPanel.add(q4, "4");
        questionPanel.add(q5, "5");

        // button panel to show buttons at the bottom which lets the user navigate between questions
        JPanel buttonPanel = new JPanel();
        JButton first = new JButton("1");
        JButton second = new JButton("2");
        JButton third = new JButton("3");
        JButton fourth = new JButton("4");
        JButton fifth = new JButton("5");
        JButton back = new JButton("<");
        JButton next = new JButton(">");

        // submit button which only appears once the user has answered all questions
        JButton submit = new JButton("Submit");
        submit.setVisible(false);

        buttonPanel.add(back);
        buttonPanel.add(first);
        buttonPanel.add(second);
        buttonPanel.add(third);
        buttonPanel.add(fourth);
        buttonPanel.add(fifth);
        buttonPanel.add(next);
        buttonPanel.add(submit);

        // Action listeners for the buttons at the bottom of the screen

        // first button
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentCard = 1;
                card1.first(titlePanel);
                card2.first(questionPanel);
            }
        });

        // second button
        second.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentCard = 2;
                card1.show(titlePanel, "" + (currentCard));
                card2.show(questionPanel, "" + (currentCard));
            }
        });

        // third button
        third.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentCard = 3;
                card1.show(titlePanel, "" + (currentCard));
                card2.show(questionPanel, "" + (currentCard));
            }
        });

        // fourth button
        fourth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentCard = 4;
                card1.show(titlePanel, "" + (currentCard));
                card2.show(questionPanel, "" + (currentCard));
            }
        });

        // fifth button
        fifth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentCard = 5;
                card1.show(titlePanel, "" + (currentCard));
                card2.show(questionPanel, "" + (currentCard));
            }
        });

        // back button
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentCard > 1){
                    currentCard -= 1;
                    card1.show(titlePanel, "" + (currentCard));
                    card2.show(questionPanel, "" + (currentCard));
                }
            }
        });

        // next button
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currentCard < 5){
                    currentCard += 1;
                    card1.show(titlePanel, "" + (currentCard));
                    card2.show(questionPanel, "" + (currentCard));
                }
            }
        });

        // submit button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // test results panel
                showResults(getContentPane());
            }
        });

        // new Gridbag layout
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        q1.setLayout(grid);
        q2.setLayout(grid);
        q3.setLayout(grid);
        q4.setLayout(grid);
        q5.setLayout(grid);

        JLabel questionName1 = getQuestionName("First Question");
        JLabel questionName2 = getQuestionName("Second Question");
        JLabel questionName3 = getQuestionName("Third Question");
        JLabel questionName4 = getQuestionName("Fourth Question");
        JLabel questionName5 = getQuestionName("Fifth Question");

        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(5,5,5,5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        q1.add(questionName1, gbc);
        q2.add(questionName2, gbc);
        q3.add(questionName3, gbc);
        q4.add(questionName4, gbc);
        q5.add(questionName5, gbc);

        // gets all the answers of a question
        List<String> answers1 = getAllAnswers(1);
        List<String> answers2 = getAllAnswers(2);
        List<String> answers3 = getAllAnswers(3);
        List<String> answers4 = getAllAnswers(4);
        List<String> answers5 = getAllAnswers(5);

        // gets the right answers of a question
        String rightAnswer1 = getRightAnswer(1);
        String rightAnswer2 = getRightAnswer(2);
        String rightAnswer3 = getRightAnswer(3);
        String rightAnswer4 = getRightAnswer(4);
        String rightAnswer5 = getRightAnswer(5);

        // puts the correct answers into an array which will be later displayed in the results screen
        correctAnswers[0] = rightAnswer1;
        correctAnswers[1] = rightAnswer2;
        correctAnswers[2] = rightAnswer3;
        correctAnswers[3] = rightAnswer4;
        correctAnswers[4] = rightAnswer5;

        // shuffles the answers for each question
        Collections.shuffle(answers1);
        Collections.shuffle(answers2);
        Collections.shuffle(answers3);
        Collections.shuffle(answers4);
        Collections.shuffle(answers5);

        JTextArea questionText1 = createQuestionText(1, rightAnswer1);
        JTextArea questionText2 = createQuestionText(2, rightAnswer2);
        JTextArea questionText3 = createQuestionText(3, rightAnswer3);
        JTextArea questionText4 = createQuestionText(4, rightAnswer4);
        JTextArea questionText5 = createQuestionText(5, rightAnswer5);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        q1.add(questionText1, gbc);
        q2.add(questionText2, gbc);
        q3.add(questionText3, gbc);
        q4.add(questionText4, gbc);
        q5.add(questionText5, gbc);

        // creates the answer panel containing the buttons used to answer each question
        JPanel answerButtons1 = createAnswerPanel(1, questionText1, answers1, 1, submit);
        JPanel answerButtons2 = createAnswerPanel(2, questionText2, answers2, 2, submit);
        JPanel answerButtons3 = createAnswerPanel(3, questionText3, answers3, 3, submit);
        JPanel answerButtons4 = createAnswerPanel(4, questionText4, answers4, 4, submit);
        JPanel answerButtons5 = createAnswerPanel(5, questionText5, answers5, 5, submit);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 2;

        q1.add(answerButtons1, gbc);
        q2.add(answerButtons2, gbc);
        q3.add(answerButtons3, gbc);
        q4.add(answerButtons4, gbc);
        q5.add(answerButtons5, gbc);


        getContentPane().add(titlePanel, BorderLayout.NORTH);

        getContentPane().add(questionPanel, BorderLayout.CENTER);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * gets the question textblock from the database
     * @param dataID the ID of a record in the database
     * @return the text block as a string
     */
    private String getTextBlock(int dataID){
        String q = (questionsAndAnswers.get(dataID)).getTextBlock();
        return q;
    }

    /**
     * gets all of the answers from the database
     * @param dataID the ID of a record in the database
     * @return a list of strings containing all of the answers of a specific questions
     */
    private List<String> getAllAnswers(int dataID){
        dataID -= 1;
        String right = (questionsAndAnswers.get(dataID)).getCorrectWord();
        String[] array = (questionsAndAnswers.get(dataID)).getWrongWords();

        List<String> answers = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            answers.add(array[i]);
        }
        answers.add(right);

        return answers;
    }
    /**
     * gets the correct answer from the database
     * @param dataID the ID of a record in the database
     * @return the correct answer of a question as a string
     */
    private String getRightAnswer(int dataID){
        dataID -= 1;
        String r = (questionsAndAnswers.get(dataID)).getCorrectWord();
        return r;
    }

    /**
     * converts contents of a label into a larger font
     * @param question the name of the question (e.g "Question 1")
     * @return label in a larger font
     */
    private JLabel getQuestionName(String question){
        JLabel label = new JLabel(question);
        label.setFont (label.getFont ().deriveFont (30.0f));
        label.setBackground(Color.lightGray);
        label.setOpaque(true);
        return label;
    }

    /**
     * creates a JTextArea with the contents of the question
     * @param dataID the ID of a record in the database
     * @param right the right answer
     * @return a JTextArea
     */
    private JTextArea createQuestionText(int dataID, String right){
        dataID -= 1;

        // gets the question string and puts into a JTextArea
        String q = getTextBlock(dataID);
        String question = convertQuestion(q, right);
        JTextArea questionText = new JTextArea(question);

        // change font size of text area
        questionText.setFont(questionText.getFont().deriveFont(22.0f));
        questionText.setSize(800, 100);
        questionText.setBackground(Color.white);

        // adds line breaks to the text area, wraps the text area by words and makes it read only
        questionText.setEditable(false);
        questionText.setLineWrap(true);
        questionText.setWrapStyleWord(true);
        questionText.setOpaque(true);
        questionText.setBorder(BorderFactory.createEmptyBorder());

        return questionText;
    }

    /**
     * creates the answer panel containing the buttons. Also includes the action Listener for these buttons
     * @param dataID the ID of a record in the database
     * @param questionText JTextArea displaying the question
     * @param answers List of answers
     * @param questionNum question number
     * @param submitButton button which submits the answers and finishes the game
     * @return
     */
    private JPanel createAnswerPanel(int dataID, JTextArea questionText, List<String> answers, int questionNum, JButton submitButton){
        dataID -= 1;

        // index of array where the answer will be stored
        questionNum -= 1;

        // creates a new JPanel
        JPanel answerButtons = new JPanel();
        answerButtons.setBackground(Color.lightGray);
        answerButtons.setLayout(new FlowLayout());
        answerButtons.setPreferredSize(new Dimension(800,400));
        answerButtons.setSize(answerButtons.getPreferredSize());

        // goes through all items in answers array list and creates a button for each answer
        for(int i = 0; i < answers.size(); i++){
            JButton button = new JButton();
            button.setText(answers.get(i));
            answerButtons.add(button);
            button.setActionCommand(answers.get(i));

            int finalDataID = dataID;
            int finalQuestionNum = questionNum;
            // Creates an action listener for each answer button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String right = getRightAnswer(finalDataID+1);
                    String q = getTextBlock(finalDataID);
                    String input = e.getActionCommand();

                    // replaces text in text area with new text
                    q = q.replaceAll("(?i)"+right, input);

                    questionText.setText(q);

                    // put answer into array then display for testing
                    currentAnswers[finalQuestionNum] = input;

                    // checks if the array no longer has null values
                    boolean showSubmit = true;
                    for(int x = 0; x < currentAnswers.length; x++){
                        if (currentAnswers[x] == null){
                            showSubmit = false;
                        }
                    }
                    // displays submit button
                    if (showSubmit == true){
                        submitButton.setVisible(true);
                    }


                }
            });

        }
        return answerButtons;
    }

    /**
     * displays the user's score, initials and the results in a table format
     * @param currentFrame passes the current frame
     */
    public void showResults(Container currentFrame){
        // removes all previous components
        currentFrame.removeAll();
        currentFrame.repaint();

        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(Color.lightGray);
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        resultPanel.setLayout(grid);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5,5,5,5);
        gbc.weightx = 1;
        gbc.weighty = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // using same format that was in the question panels
        JLabel title = getQuestionName("Results");
        resultPanel.add(title, gbc);

        gbc.weighty = 5.0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5,200,5,200);
        gbc.fill = GridBagConstraints.BOTH;

        //display table with answers, 1st column = question, 2nd column = correct answer, 3rd column with background
        // colour red or green = user's answers

        String[][] records = new String[5][];
        String[] columnNames = {"Questions", "Correct Answer", "Your Answer"};

        // Data to be displayed in the JTable
        for (int i = 0; i < 5; i++) {
            String[] temp = {"Question "+i, correctAnswers[i], currentAnswers[i]};
            records[i] = temp;
            if (currentAnswers[i].equals(correctAnswers[i])){
                answerCount +=1;
            }
        }

        MyRenderer myRenderer = new MyRenderer();
        DefaultTableModel defModel = new DefaultTableModel(records, columnNames);

        // Initializing the JTable
        JTable resultsTable = new JTable(defModel);
        resultsTable.setFont(new Font(resultsTable.getFont().toString(), Font.BOLD, 15));
        resultsTable.setDefaultRenderer(Object.class, myRenderer);
        resultsTable.setSize(800, 100);

        // need to add the JTable to a scroll pane, otherwise, the header isn't displayed
        JScrollPane sp = new JScrollPane(resultsTable);
        sp.setSize(800,100);
        resultPanel.add(sp, gbc);

        gbc.insets = new Insets(5,5,5,5);
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;

        JLabel uInit = new JLabel("User Initials: " + userInitials);
        uInit.setFont (uInit.getFont ().deriveFont (20.0f));
        resultPanel.add(uInit, gbc);

        gbc.insets = new Insets(5,5,5,5);
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;

        JLabel totalScore = new JLabel("Total Score: " + answerCount);
        totalScore.setFont (totalScore.getFont ().deriveFont (20.0f));
        resultPanel.add(totalScore, gbc);

        gbc.insets = new Insets(5,5,5,5);
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;

        JButton backToMenu = new JButton("Back To Menu");
        resultPanel.add(backToMenu, gbc);

        // backToMenu button action Listener
        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainMenu.displayMenu();
                setDefaultCloseOperation(HIDE_ON_CLOSE);
                dispose();
            }
        });

        // adds the new panel and validates it to update the frame
        currentFrame.add(resultPanel);
        currentFrame.validate();

        // creates a new entry to be inserted into the database
        LeaderEntry entry = new LeaderEntry();
        entry.setGame("Game1");
        entry.setInitials(userInitials);
        entry.setScore(answerCount);
        entry.setStream(streamName);

        /** Add to leaderboard**/
        dbManager.addLeaderBoardEntry(entry);

    }

    /**
     * removes the correct answer from the question and replaces it with an underline
     * @param newQuestion the question string
     * @param answer the correct answer
     * @return converted string
     */
    public static String convertQuestion(String newQuestion, String answer) {
        String underline = "____";
        // (?i) makes it non case sensitive
        newQuestion = newQuestion.replaceAll("(?i)"+answer, underline);
        return newQuestion;
    }

    /**
     * connects to the database and gets all records relating to one of the three streams
     * @param stream one of the three streams
     */
    public void getFromDatabase(String stream){
        // connects to the database
        dbManager.connect();
        // creates a list
        List<Game1> questionList;
        // gets all questions and answers from a particular stream
        questionList = dbManager.getGame1Q(stream);
        // shuffles the questions list
        Collections.shuffle(questionList);
        // sets global variable equal to the questionList so that all methods can access the questions and answers
        questionsAndAnswers = questionList;

    }


    /** main method used for testing **/
    public static void main(String[] args) {
        FillInBlanks fb = new FillInBlanks("Technical Operations", "ABC");
        fb.setVisible(true);
    }

}

// MyRenderer class is used for setting the background colour of cells in the results table
class MyRenderer extends DefaultTableCellRenderer {
    /**
     * Configures the renderer appropriately before drawing
     * @param table the table asking the renderer to draw
     * @param value value of cell to be rendered
     * @param isSelected true if the cell is to be rendered with the selection highlighted
     * @param hasFocus if true, render cell appropriately
     * @param row row index of the cell being drawn
     * @param column column index of the cell being drawn
     * @return component used for drawing the cell
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        for (int i = 0; i < 5; i++) {
            Object correctAnswer = table.getValueAt(i, 1);
            Object userAnswer = table.getValueAt(i, 2);

            if (!table.isRowSelected(row)) {
                if (row == i && column == 2 && userAnswer.equals(correctAnswer))
                    c.setBackground(Color.green);
                else if (row ==i && column == 2 && !userAnswer.equals(correctAnswer))
                    c.setBackground(Color.red);
                else if (row == i && column == 1 )
                    c.setBackground(Color.white);
                else if (row == i && column == 0)
                    c.setBackground(Color.white);
            }

        }

        return c;
    }
}