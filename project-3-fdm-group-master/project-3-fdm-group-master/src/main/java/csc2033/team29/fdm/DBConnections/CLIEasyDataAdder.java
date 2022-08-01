package csc2033.team29.fdm.DBConnections;

import csc2033.team29.fdm.DBConnections.Data.*;

import java.util.Scanner;

/* This class was created by Harry Brettell.
   the class is a small command line interface used to add more questions to the database by helping format all of the
   data correctly.
 */
public class CLIEasyDataAdder {
    public static void main(String[] args) {
        mainLoop();
    }

    private static void mainLoop() {
        System.out.println("           Welcome to DataAdder");
        System.out.println("==================================================");
        System.out.print("Creating connection to the database\r");

        Scanner scan = new Scanner(System.in);
        DBManager db = new DBManager();
        if(!db.connect()) {
            throw new RuntimeException("Failed to connect");
        }
        System.out.println("Database connected");
        while (true) {
            System.out.println("Which game would you like to add data to? 1, 2 or 3 type -q to quit");
            String input = scan.nextLine();

            if (input.equals("-q")) {
                break;
            } else if (input.equals("1")) {
                addGame1(scan, db);
            } else if (input.equals("2")) {
                addGame2(scan, db);
            } else if (input.equals("3")) {
                addGame3(scan, db);
            } else {
                System.out.println("Sorry input not recognised. please try again");
            }
        }

        System.out.print("Closing database connection \r");
        if(db.disconnect()) {
            System.out.println("Database closed");
        } else {
            System.out.println("Error in closing the database");
        }
        System.out.println("Goodbye");
    }

    private static void addGame1(Scanner scan, DBManager database) {
        while (true) {
            System.out.println("Creating a new entry for game 1. Type -c in any input to cancel the creation");
            Game1 newEntry = new Game1();
            String input;

            System.out.println("Please enter the missing word");
            input = scan.nextLine();
            if (input.equals("-c")) {return;}
            newEntry.setCorrectWord(input);


            System.out.println("Please enter how many wrong words there will be");
            input = scan.nextLine();
            if (input.equals("-c")) {return;}
            int count = Integer.parseInt(input);


            String[] words = new String[count];
            for (int i = 0; i < count; i++) {
                System.out.printf("Please enter the %d word\n", i + 1);
                input = scan.nextLine();
                if (input.equals("-c")) {return;}
                words[i] = input;
            }
            newEntry.setWrongWords(words);

            System.out.println("Please enter the block of text.");
            input = scan.nextLine();
            if (input.equals("-c")) {return;}
            newEntry.setTextBlock(input);

            System.out.println("Please enter the stream this corresponds to.");
            input = scan.nextLine();
            if (input.equals("-c")) {return;}
            newEntry.setStream(input);

            System.out.println(newEntry);
            System.out.println("Do you want to add this entry? y/n");

            input = scan.nextLine();
            if (input.equals("-c")) {return;}
            if (input.equals("y")) {
                System.out.println("Adding entry\r");
                    database.addGame1Entry(newEntry);
            } else {
                System.out.println("removing new entry data");
            }
        }
    }

    private static void addGame2(Scanner scan, DBManager database) {
        while (true) {
            System.out.println("Creating a new entry for game 2. Type -c in any input to cancel the creation");
            Game2 newEntry = new Game2();
            String input;

            System.out.println("Please enter the word");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setWord(input);

            System.out.println("Please enter the description.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setDescription(input);

            System.out.println("Please enter the stream this corresponds to.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setStream(input);

            System.out.println(newEntry);
            System.out.println("Do you want to add this entry? y/n");

            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            if (input.equals("y")) {
                System.out.println("Adding entry");
                database.addGame2Entry(newEntry);
            } else {
                System.out.println("removing new entry data");
            }
        }
    }

    private static void addGame3(Scanner scan, DBManager database) {
        while (true) {
            System.out.println("Creating a new entry for game 3. Type -c in any input to cancel the creation");
            Game3 newEntry = new Game3();
            String input;

            System.out.println("Please enter the question");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setQuestion(input);

            System.out.println("Please enter the correct answer.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setAnswer(input);

            String[] wrongAnswers = new String[3];
            System.out.println("Please enter the 1st wrong answer.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            wrongAnswers[0] = input;

            System.out.println("Please enter the 2nd wrong answer.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            wrongAnswers[1] = input;

            System.out.println("Please enter the 3rd wrong answer.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            wrongAnswers[2] = input;
            newEntry.setWrongAnswers(wrongAnswers);

            System.out.println("Please enter the stream this corresponds to.");
            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            newEntry.setStream(input);

            System.out.println(newEntry);
            System.out.println("Do you want to add this entry? y/n");

            input = scan.nextLine();
            if (input.equals("-c")) {
                return;
            }
            if (input.equals("y")) {
                System.out.println("Adding entry");
                database.addGame3Entry(newEntry);
            } else {
                System.out.println("removing new entry data");
            }
        }
    }
}
