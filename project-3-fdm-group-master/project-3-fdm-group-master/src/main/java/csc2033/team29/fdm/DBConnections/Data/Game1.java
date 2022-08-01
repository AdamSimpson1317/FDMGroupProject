package csc2033.team29.fdm.DBConnections.Data;

import java.util.Arrays;

/* this class was created by Harry Brettell
   it acts as a storage for the data involved in a question for game 1
 */
public class Game1 {
    private String correctWord;
    private String[] wrongWords;
    private String textBlock;
    private String Stream;

    public String getCorrectWord() {
        return correctWord;
    }

    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }

    public String[] getWrongWords() {
        return wrongWords;
    }

    public void setWrongWords(String[] words) {
        this.wrongWords = words;
    }

    public String getTextBlock() {
        return textBlock;
    }

    public void setTextBlock(String textBlock) {
        this.textBlock = textBlock;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public Game1(String correctWord, String[] wrongWords, String textBlock, String stream) {

        this.correctWord = correctWord;
        this.wrongWords = wrongWords;
        this.textBlock = textBlock;
        Stream = stream;
    }

    public Game1() {
    }

    @Override
    public String toString() {
        return "Game1{\n" +
                "   correct word='" + correctWord + "'\n" +
                "   wrong words =" + Arrays.toString(wrongWords) + '\n' +
                "   textBlock   ='" + textBlock + "'\n" +
                "   Stream      ='" + Stream + "'\n" +
                '}';
    }
}
