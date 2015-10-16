/*
 * HighscoresData.java
 *
 * A model class that represents the highscores. This model class implements all the functionality
 * regarding the highscores (i.e., retrieving the highscores, adding a highscore, and saving the
 * highscores).
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class HighscoresData {

    /*
     * Properties of the class.
     */
    private Hashtable<String, Integer> namesAndScores;

    private final String keyHighscoresDataFile = "highscores.txt";

    /*
     * Constructor of the class.
     */
    public HighscoresData(Context context) {
        namesAndScores = new Hashtable<>();
        retrieveNamesAndScores(context);
    }

    /*
     * Retrieves the names and scores (i.e., name-score pairs) from the text file 'highscores.txt'
     * (which is saved on the device's internal storage), and stores them into the Hashtable
     * 'namesAndScores' as key-value pairs. Every line in the text file has the following format:
     * 'name,score\n'
     */
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

    /*
     * Adds a name and score to the Hashtable 'namesAndScores' (as key-value pair), and saves the
     * name and score to the text file 'highscores.txt' (which is saved on the device's internal
     * storage). If the name is already present in the Hashtable, the score is simply updated, but
     * the content of the text file is overwritten with the content of the Hashtable (this prevents
     * lookup of the name). If the name is not present in the Hashtable, is is simple added as a new
     * key with the score as value, and the name-score pair is appended to the end of the text file.
     */
    public void addNameAndScore(Context context, String name, int score) {
        if(namesAndScores.containsKey(name)) {
            namesAndScores.put(name, score);
            rewriteNamesAndScoresFile(context);
        }
        else {
            namesAndScores.put(name, score);
            writeNewNameAndScoreToFile(context, name, score);
        }
    }

    /*
     * Overwrites the content of the text file 'highscores.txt' (which is saved on the device's
     * internal storage) with the content of the Hashtable 'namesAndScores'. Every name-score pair
     * is written on a new line as 'name,score'.
     */
    private void rewriteNamesAndScoresFile(Context context) {
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

    /*
     * Appends a name and score to the end of the text file 'highscores.txt' (which is saved on the
     * device's internal storage). The name and score are written on a new line as 'name,score\n'.
     */
    private void writeNewNameAndScoreToFile(Context context, String name, int score) {
        try {
            PrintStream out = new PrintStream(context.openFileOutput(keyHighscoresDataFile, Context.MODE_APPEND));
            out.println(name + "," + Integer.toString(score));
            out.close();
        } catch(IOException e) {
            Toast.makeText(context, R.string.highscores_data_text_save_error, Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Returns the property 'namesAndScores' (i.e., the Hashtable with name-score pairs).
     */
    public Hashtable<String, Integer> getNamesAndScores() {
        return namesAndScores;
    }
}