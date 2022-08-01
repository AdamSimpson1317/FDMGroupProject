package csc2033.team29.fdm;

import csc2033.team29.fdm.DBConnections.DBManager;
import csc2033.team29.fdm.DBConnections.Data.Game3;
import csc2033.team29.fdm.DBConnections.Data.LeaderEntry;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

//Created by Adam Simpson.
class Quiz extends JFrame implements ActionListener {
    //Sets all variables
    JLabel l;
    JRadioButton jb[]=new JRadioButton[5];
    JButton b1,b2,b3;
    ButtonGroup bg;
    int count=0;
    int current=0;
    int x=1;
    int y=1;
    int now=0;
    int m[]=new int[10];
    String streams;
    String initials;

    Quiz(String s, String stream, String initial){
        super(s);
        streams = stream;
        initials = initial;
        l=new JLabel();
        add(l);
        bg=new ButtonGroup();
        for(int i=0;i<5;i++){
            jb[i]=new JRadioButton();
            add(jb[i]);
            bg.add(jb[i]);
        }
        //Sets up the 2 buttons that will be displayed
        b1=new JButton("Next");
        b2=new JButton("Bookmark");
        b3=new JButton("Back to Menu");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        add(b1);
        add(b2);
        add(b3);
        set();
        //Sets the dimensions of the question and answers box
        l.setBounds(30,40,450,50);
        jb[0].setBounds(50,130,650,50);
        jb[1].setBounds(50,200,650,50);
        jb[2].setBounds(50,270,650,50);
        jb[3].setBounds(50,340,650,50);
        //Sets the colour of the background around the questions
        jb[0].setBackground(Color.LIGHT_GRAY);
        jb[1].setBackground(Color.LIGHT_GRAY);
        jb[2].setBackground(Color.LIGHT_GRAY);
        jb[3].setBackground(Color.LIGHT_GRAY);
        //Sets the button dimensions.
        b1.setBounds(100,480,150,60);
        b2.setBounds(270,480,150,60);
        b3.setBounds(440,480,150,60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(250,100);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        setSize(825,600);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(check()){
                count=count+1;
            }
            current++;
            set();
            if(current==9) {
                b1.setEnabled(false);
                b2.setText("Result");
            }
        }
        if (e.getActionCommand().equals("Back to Menu")) {
            MainMenu.displayMenu();
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            dispose();
        }
        //Allows the user to bookmark and come back to a question.
        if(e.getActionCommand().equals("Bookmark")){
            JButton bk = new JButton("Bookmark"+x);
            bk.setBounds(700,20+50*x,100,30);
            add(bk);
            bk.addActionListener(this);
            m[x]=current;
            x++;
            current++;
            set();
            if(current==9){
                b2.setText("Result");
            }
            setVisible(false);
            setVisible(true);
        }
        for(int i=0,y=1;i<x;i++,y++){
            if(e.getActionCommand().equals("Bookmark"+y)){
                if(check()){
                    count=count+1;
                }
                now=current;
                current=m[y];
                set();
                ((JButton)e.getSource()).setEnabled(false);
                current=now;
            }
        }

        if(e.getActionCommand().equals("Result")){
            if(check()){
                count=count+1;
            }
            current++;
            JOptionPane.showMessageDialog(this,"Correct Answers="+count);
            //Connects to database to input a leader board entry.
            LeaderEntry entry = new LeaderEntry();
            entry.setInitials(initials);
            entry.setScore(count);
            entry.setGame("Game 3");
            entry.setStream(streams);
            //Closes the game and loads menu back up.
            MainMenu.displayMenu();
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            dispose();
        }
    }

    public List<String> getQuestionAndAnswers(int current){
        //Creates a new instance of DBManager
        DBManager dbmanager = new DBManager();
        dbmanager.connect();
        //Creates Lists
        List<Game3> questionList;
        List<String> questionAndAnswers = new ArrayList<>();
        //Gets a List of an object from the database but need to change this for menu usage.
        questionList = dbmanager.getGame3Q(streams);
        Game3 game3 = questionList.get(current);
        //Gets the question and adds it to a new list
        String question = game3.getQuestion();
        questionAndAnswers.add(question);
        //Gets the correct answer and adds it to a list
        String correctAnswer = game3.getAnswer();
        questionAndAnswers.add(correctAnswer);
        //Takes the array of wrong answers out of the list and adds them to the list
        String[] wrongAnswerArray = game3.getWrongAnswers();
        String wrongAnswer1 = wrongAnswerArray[0];
        questionAndAnswers.add(wrongAnswer1);
        String wrongAnswer2 = wrongAnswerArray[1];
        questionAndAnswers.add(wrongAnswer2);
        String wrongAnswer3 = wrongAnswerArray[2];
        questionAndAnswers.add(wrongAnswer3);
        //returns everything in this order (Question, Correct Answer, Wrong Answer 1, Wrong Answer 2, Wrong Answer 3)
        return questionAndAnswers;
    }

//This sets all questions and shows the question and answers.
    void set() {
        jb[4].setSelected(true);
        if (current==0){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(1));
            jb[1].setText(questionAndAnswers.get(2));
            jb[2].setText(questionAndAnswers.get(3));
            jb[3].setText(questionAndAnswers.get(4));
        }
        if (current==1){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(4));
            jb[1].setText(questionAndAnswers.get(3));
            jb[2].setText(questionAndAnswers.get(1));
            jb[3].setText(questionAndAnswers.get(2));
        }
        if (current==2){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(3));
            jb[1].setText(questionAndAnswers.get(1));
            jb[2].setText(questionAndAnswers.get(2));
            jb[3].setText(questionAndAnswers.get(4));
        }
        if (current==3){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(4));
            jb[1].setText(questionAndAnswers.get(2));
            jb[2].setText(questionAndAnswers.get(3));
            jb[3].setText(questionAndAnswers.get(1));
        }
        if (current==4){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(1));
            jb[1].setText(questionAndAnswers.get(4));
            jb[2].setText(questionAndAnswers.get(3));
            jb[3].setText(questionAndAnswers.get(2));
        }
        if (current==5){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(3));
            jb[1].setText(questionAndAnswers.get(1));
            jb[2].setText(questionAndAnswers.get(4));
            jb[3].setText(questionAndAnswers.get(2));
        }
        if (current==6){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(3));
            jb[1].setText(questionAndAnswers.get(4));
            jb[2].setText(questionAndAnswers.get(1));
            jb[3].setText(questionAndAnswers.get(2));
        }
        if (current==7){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(1));
            jb[1].setText(questionAndAnswers.get(3));
            jb[2].setText(questionAndAnswers.get(2));
            jb[3].setText(questionAndAnswers.get(4));
        }
        if (current==8){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(4));
            jb[1].setText(questionAndAnswers.get(2));
            jb[2].setText(questionAndAnswers.get(1));
            jb[3].setText(questionAndAnswers.get(3));
        }
        if (current==9){
            List<String> questionAndAnswers = getQuestionAndAnswers(current);
            l.setText(questionAndAnswers.get(0));
            jb[0].setText(questionAndAnswers.get(4));
            jb[1].setText(questionAndAnswers.get(3));
            jb[2].setText(questionAndAnswers.get(2));
            jb[3].setText(questionAndAnswers.get(1));
        }
    }
    //Checks inputted answer against the actual answer.
    boolean check(){
        if(current==0){
            return(jb[0].isSelected());
        }
        if(current==1){
            return(jb[2].isSelected());
        }
        if(current==2){
            return(jb[1].isSelected());
        }
        if(current==3){
            return(jb[3].isSelected());
        }
        if(current==4){
            return(jb[0].isSelected());
        }
        if(current==5){
            return(jb[1].isSelected());
        }
        if(current==6){
            return(jb[2].isSelected());
        }
        if(current==7){
            return(jb[0].isSelected());
        }
        if(current==8){
            return(jb[2].isSelected());
        }
        if(current==9){
            return(jb[3].isSelected());
        }
        return false;
    }
}
