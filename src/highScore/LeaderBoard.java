package highScore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LeaderBoard {
	int[] score = new int[10];
	String[] name = new String[10];

    int entryCount;

    public static LeaderBoard TM_LB = new LeaderBoard();
    public static LeaderBoard THM_LB = new LeaderBoard();
    public static LeaderBoard TTM_LB = new LeaderBoard();
    public static LeaderBoard LS_LB = new LeaderBoard();
    public static LeaderBoard LSH_LB = new LeaderBoard();

    public static void setLeaderBoard(LeaderBoard leaderBoard, String pathname) {
        File file = new File(pathname);

        leaderBoard.entryCount = 0;

        try {
            FileReader in = new FileReader(file);
            BufferedReader inReader = new BufferedReader(in);
            String inStr = new String(), name;
            int score, i, j;

            try {
                for(i = 0; i < 10; i++) {
                    inStr = inReader.readLine();

                    if(inStr == null) {
                        System.out.println("Insufficient number of records");
                        throw new IOException();
                    }

                    name = inStr.substring(0, 3);
                    score = Integer.parseInt(inStr.substring(4));

                    for(j = i; j > 0 && leaderBoard.score[j-1] < score; j--) {
                        leaderBoard.name[j] = leaderBoard.name[j-1];
                        leaderBoard.score[j] = leaderBoard.score[j-1];
                    }
                    leaderBoard.name[j] = name;
                    leaderBoard.score[j] = score;
                    leaderBoard.entryCount++;
                }

            }
            catch(NumberFormatException nfe) {
                System.out.println("File corrupted");
                for(i = leaderBoard.entryCount; i < 10; i++) {
            		leaderBoard.score[i] = -1;
            		leaderBoard.name[i] = "---";
                }
            }
            catch (IOException ioe) {
                for(i = leaderBoard.entryCount; i < 10; i++) {
            		leaderBoard.score[i] = -1;
            		leaderBoard.name[i] = "---";
                }
            }

            try {
                inReader.close();
            }
            catch(IOException ioe) {
                System.out.println("Failed to close BufferedReader");
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println(pathname + ": File not found");
            try {
                for(int i = 0; i < 10; i++) {
            		leaderBoard.score[i] = -1;
            		leaderBoard.name[i] = "---";
            	}

            	file.createNewFile();
            }
            catch(IOException ioe) {
            	System.out.println("Failed to create file: " + pathname);
            }
        }
    }

    public static void insertScore(String name, int score, LeaderBoard leaderBoard) {
        int i;
        for(i = 9; i > 0 && leaderBoard.score[i-1] < score; i--) {
            leaderBoard.name[i] = leaderBoard.name[i-1];
            leaderBoard.score[i] = leaderBoard.score[i-1];
        }
        leaderBoard.name[i] = name;
        leaderBoard.score[i] = score;
    }

    public static void setFile(LeaderBoard leaderBoard, String pathname) {
        File file = new File(pathname);

        try {
            PrintWriter outWriter = new PrintWriter(file);
            for(int  i = 0; i < 10; i++) {
                outWriter.println(leaderBoard.name[i] + " " + leaderBoard.score[i]);
                System.out.println(leaderBoard.name[i] + " " + leaderBoard.score[i]);
            }

            outWriter.close();
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("Failed to open file to write");
        }
        catch(SecurityException se) {
            System.out.println("Failed to gain access to file to write");
        }
    }
}
