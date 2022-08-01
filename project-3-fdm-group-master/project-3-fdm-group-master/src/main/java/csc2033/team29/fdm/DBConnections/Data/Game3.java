package csc2033.team29.fdm.DBConnections.Data;

import java.util.Arrays;

/* this class was created by Harry Brettell
   it acts as a storage for the data involved in a question for game 3
 */
public class Game3 {
    private String question;
    private String answer;
    private String[] wrongAnswers;
    private String stream;

    public Game3(String question, String answer, String[] wrongAnswers, String stream) {
        this.question = question;
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
        this.stream = stream;
    }

    public Game3() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Game3{\n" +
                "   question    ='" + question + "'\n" +
                "   answer      ='" + answer + "'\n" +
                "   wrongAnswers=" + Arrays.toString(wrongAnswers) + '\n' +
                "   stream      ='" + stream + "'\n" +
                '}';
    }
}
