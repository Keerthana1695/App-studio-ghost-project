/*
 * PlayerSelect.java
 *
 * The player select Activity. This Activity allows the user(s) (player(s)) to set a name for the
 * game. The user(s) can select a previously entered name, or enter a new name.
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class PlayerSelect extends Activity {

    /*
     * Properties of the class.
     */
    public static final String keyPlayer1Name = "player1Name";
    public static final String keyPlayer2Name = "player2Name";

    private Spinner player1Spinner, player2Spinner;
    private EditText player1EditText, player2EditText;
    private Players players;
    private ArrayList<String> playerNames;
    private ArrayAdapter<String> spinnerAdapter;

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player_select);
        players = new Players(getApplicationContext());
        playerNames = players.getPlayerNames();
        initializeViews();
        addItemsToSpinners();
    }

    /*
     * Initializes all the (required) views.
     */
    private void initializeViews() {
        player1Spinner = (Spinner) findViewById(R.id.player1_spinner);
        player2Spinner = (Spinner) findViewById(R.id.player2_spinner);
        player1EditText = (EditText) findViewById(R.id.player1_editText);
        player2EditText = (EditText) findViewById(R.id.player2_editText);
    }

    /*
     * Creates a SpinnerAdapter for the two Spinners and adds it to the Spinners.
     */
    private void addItemsToSpinners() {
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, playerNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player1Spinner.setAdapter(spinnerAdapter);
        player2Spinner.setAdapter(spinnerAdapter);
    }

    /*
     * Initializes the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_select, menu);
        return true;
    }

    /*
     * Handles clicks on the menu options.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
         * Handles on 'Back' click.
         */
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Handles a click on the 'Add' button for player 1. The method 'addPlayer(...)' is called to
     * add the player name to the ArrayList with all the player names (defined in Players.java) and
     * to save the player name for future use. Furthermore, if the player name is valid, the
     * corresponding Spinner is set to show the entered player name.
     */
    public void onNameButton1Click(View view) {
        String player1Name = String.valueOf(player1EditText.getText());
        player1EditText.setText("");
        addPlayer(player1Name);
        int spinnerPosition = spinnerAdapter.getPosition(player1Name);
        if(spinnerPosition != -1) {
            player1Spinner.setSelection(spinnerPosition);
        }
    }

    /*
     * Handles a click on the 'Add' button for player 2. Similar to the method
     * 'onNameButton1Click(...)', but now for player 2.
     */
    public void onNameButton2Click(View view) {
        String player2Name = String.valueOf(player2EditText.getText());
        player2EditText.setText("");
        addPlayer(player2Name);
        int spinnerPosition = spinnerAdapter.getPosition(player2Name);
        if(spinnerPosition != -1) {
            player2Spinner.setSelection(spinnerPosition);
        }
    }

    /*
     * Uses the method 'addPlayerName(...)' (defined in Players.java) to add the player name to the
     * ArrayList with all the player names (also defined in Players.java), to save the player
     * name for future use, and to add the player name to the highscores. After adding the
     * player name, the ArrayAdapter is notified that the data set has changed.
     */
    private void addPlayer(String name) {
        players.addPlayerName(getApplicationContext(), name);
        playerNames = players.getPlayerNames();
        spinnerAdapter.notifyDataSetChanged();
    }

    /*
     * Handles a click on the 'Play' TextView. Obtains the objects at the current spinner positions
     * and first checks whether these objects are not null (this is possible when no player names
     * have been added before, causing the Spinners to be empty). If the objects are not null, the
     * player names are obtained, and if the player names are not the same, the user is directed to
     * the Ghost game Activity (see GhostGame.java).
     */
    public void onPlayClick(View view) {
        Object objectNamePlayer1 = player1Spinner.getSelectedItem();
        Object objectNamePlayer2 = player2Spinner.getSelectedItem();
        if(objectNamePlayer1 == null || objectNamePlayer2 == null) {
            Toast.makeText(getApplicationContext(), R.string.player_select_text_no_names,
                           Toast.LENGTH_SHORT).show();
        }
        else {
            String namePlayer1 = objectNamePlayer1.toString();
            String namePlayer2 = objectNamePlayer2.toString();
            if (namePlayer1.equals(namePlayer2)) {
                Toast.makeText(getApplicationContext(), R.string.player_select_text_same_name,
                               Toast.LENGTH_SHORT).show();
            } else {
                startGhostGameActivity(namePlayer1, namePlayer2);
            }
        }
    }

    /*
     * Directs the user to the Ghost game activity (see GhostGame.java).
     */
    private void startGhostGameActivity(String namePlayer1, String namePlayer2) {
        Intent goToGhostGame = new Intent(getApplicationContext(), GhostGame.class);
        goToGhostGame.putExtra(keyPlayer1Name, namePlayer1);
        goToGhostGame.putExtra(keyPlayer2Name, namePlayer2);
        startActivity(goToGhostGame);
        finish();
    }

    /*
     * Hides the keyboard when the user presses outside the keyboard.
     *
     * NOTE: This code is obtained from stackoverflow. Source: http://stackoverflow.com/a/8697635
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = getCurrentFocus();
        if(focus != null) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
        return true;
    }
}