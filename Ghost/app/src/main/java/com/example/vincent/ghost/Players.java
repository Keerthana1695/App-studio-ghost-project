/*
 * Players.java
 *
 * A model class that represents the names of all the player that have played the game before.
 *
 * Author: Vincent Erich
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
import java.util.ArrayList;
import java.util.Scanner;

public class Players {

    /*
     * Properties of the class.
     */
    private ArrayList<String> playerNames;

    private final String keyPlayerNamesFile = "players.txt";

    /*
     * Constructor of the class.
     */
    public Players(Context context) {
        playerNames = new ArrayList<>();
        retrievePlayerNames(context);
    }

    /*
     * Retrieves the names of the players that have played the game before from the text file
     * 'players.txt' (which is saved on the device's internal storage), and stores them into the
     * ArrayList 'playerNames'. Every line in the text file has the following format: 'name\n'.
     */
    private void retrievePlayerNames(Context context) {
        try {
            Scanner scan = new Scanner(context.openFileInput(keyPlayerNamesFile));
            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                playerNames.add(line);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, R.string.players_text_retrieval_error, Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Adds a player name to the ArrayList 'playerNames' and saves the player name to the text file
     * 'players.txt' (which is saved on the device's internal storage). Furthermore, if the player
     * name is valid (see below), a HighscoresData instance (see HighscoresData.java) is created
     * and the player name is added to the highscores with score 0.
     * If the player name is already in the ArrayList, a toast is shown to the user that says that
     * the player name already exists. Furthermore, if the player name contains only whitespace, a
     * toast is shown to the user that asks the user to enter a player name. Also, the player name
     * cannot contain more than 15 characters.
     *
     */
    public void addPlayerName(Context context, String name) {
        if(playerNames.contains(name)) {
            Toast.makeText(context, R.string.players_text_duplicate_player, Toast.LENGTH_SHORT).show();
        }
        else if(name.trim().length() == 0) {
            Toast.makeText(context, R.string.players_text_empty_name, Toast.LENGTH_SHORT).show();
        }
        else if(name.length() > 15) {
            Toast.makeText(context, R.string.players_text_long_name, Toast.LENGTH_SHORT).show();
        }
        else {
            playerNames.add(name);
            try {
                PrintStream out = new PrintStream(context.openFileOutput(keyPlayerNamesFile, Context.MODE_APPEND));
                out.println(name);
                out.close();
                Toast.makeText(context, R.string.players_text_player_added, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, R.string.players_text_save_error, Toast.LENGTH_SHORT).show();
            }
            HighscoresData highscoresData = new HighscoresData(context);
            highscoresData.addNameAndScore(context, name, 0);
        }
    }

    /*
     * Returns the property 'playerNames' (i.e., the ArrayList with all the player names).
     */
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}