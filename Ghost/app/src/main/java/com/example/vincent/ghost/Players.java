package com.example.vincent.ghost;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Players {

    // Properties of the class...
    private ArrayList<String> playerNames;
    private final String keyPlayerNamesFile = "players.txt";

    public Players(Context context) {
        playerNames = new ArrayList<>();
        retrievePlayerNames(context);
    }

    // Methods of the class...

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

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void addPlayerName(Context context, String name) {
        if(playerNames.contains(name)) {
            Toast.makeText(context, R.string.players_text_duplicate_player, Toast.LENGTH_SHORT).show();
        }
        else if(name.equals("")) {
            Toast.makeText(context, R.string.players_text_empty_name, Toast.LENGTH_SHORT).show();
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
        }
    }
}
