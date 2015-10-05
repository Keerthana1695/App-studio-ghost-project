package com.example.vincent.ghost;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

public class HighscoresData {

    private Hashtable<String, Integer> namesAndScores;
    private final String keyHighscoresDataFile = "highscores.txt";

    public HighscoresData(Context context) {
        namesAndScores = new Hashtable<String, Integer>();
        retrieveNamesAndScores(context);
    }

    private void retrieveNamesAndScores(Context context) {
        try {
            Scanner scan = new Scanner(context.openFileInput(keyHighscoresDataFile));
            String line;
            String[] parts;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                parts = line.split(",");
                namesAndScores.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error retrieving highscores.", Toast.LENGTH_SHORT).show();
        }
    }

    public Hashtable<String, Integer> getNamesAndScores() {
        return namesAndScores;
    }

    public void addNameAndScore(Context context, String name, int score) {
        if(namesAndScores.containsKey(name)) {
            // Update Hashtable and write to file...
        }
        else {
            namesAndScores.put(name, score);
            try {
                PrintStream out = new PrintStream(context.openFileOutput(keyHighscoresDataFile, Context.MODE_APPEND));
                out.println(name);
                out.close();
                // Toast here...
            } catch (IOException e) {
                // Toast here...
            }
        }
    }

}
