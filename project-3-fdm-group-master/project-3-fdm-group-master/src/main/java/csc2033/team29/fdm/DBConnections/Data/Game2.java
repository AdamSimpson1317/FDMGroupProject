package csc2033.team29.fdm.DBConnections.Data;

/* this class was created by Harry Brettell
   it acts as a storage for the data involved in a question for game 2
 */
public class Game2 {
    private String word;
    private String description;
    private String stream;

    public Game2() { }

    public Game2(String word, String description, String stream) {
        this.word = word;
        this.description = description;
        this.stream = stream;
    }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getStream() { return stream; }

    public void setStream(String stream) { this.stream = stream; }

    @Override
    public String toString() {
        return "Game2{\n" +
                "   word        ='" + word + "'\n" +
                "   description ='" + description + "'\n" +
                "   stream      ='" + stream + "'\n" +
                '}';
    }
}
