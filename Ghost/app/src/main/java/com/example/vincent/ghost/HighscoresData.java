package com.example.vincent.ghost;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class HighscoresData {

    private Hashtable<String, Integer> namesAndScores;
    private final String keyHighscoresDataFile = "highscores.txt";

    public HighscoresData(Context context) {
        namesAndScores = new Hashtable<>();
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
            Toast.makeText(context, R.string.highscores_data_text_retrieval_error, Toast.LENGTH_SHORT).show();
        }
    }

    public Hashtable<String, Integer> getNamesAndScores() {
        return namesAndScores;
    }

    public void addNameAndScore(Context context, String name, int score) {
        if(namesAndScores.containsKey(name)) {
            namesAndScores.put(name, score);
            rewriteHighscoresData(context);
        }
        else {
            namesAndScores.put(name, score);
            writeNewNameAndScoreToFile(context, name, score);
        }
    }

    private void rewriteHighscoresData(Context context) {
        try {
            PrintStream out = new PrintStream(context.openFileOutput(keyHighscoresDataFile, Context.MODE_PRIVATE));
            Enumeration<String> names = namesAndScores.keys();
            String name;
            int score;
            while(names.hasMoreElements()) {
                name = names.nextElement();
                score = namesAndScores.get(name);
                out.println(name + "," + Integer.toString(score));
            }
            out.close();
        }
        catch(IOException e) {
            Toast.makeText(context, R.string.highscores_data_text_save_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void writeNewNameAndScoreToFile(Context context, String name, int score) {
        try {
            PrintStream out = new PrintStream(context.openFileOutput(keyHighscoresDataFile, Context.MODE_APPEND));
            out.println(name + "," + Integer.toString(score));
            out.close();
        } catch(IOException e) {
            Toast.makeText(context, R.string.highscores_data_text_save_error, Toast.LENGTH_SHORT).show();
        }
    }
}
