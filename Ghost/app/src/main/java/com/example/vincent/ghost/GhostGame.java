/*
 * GhostGame.java
 *
 * The Ghost game Activity. This Activity allows the user(s) to actually play the game.
 *
 * Author: Vincent Erich
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class GhostGame extends Activity {

    /*
     * Properties of the class.
     */
    public static final String activityName = "GhostGame";
    public static final String savedGameKey = "savedGameKey";
    public static final String setLanguageBeforeCallKey = "setLanguageBeforeCallKey";
    public static final String nameWinnerKey = "nameWinnerKey";

    private TextView textNamePlayer1, textNamePlayer2;
    private String namePlayer1, namePlayer2;
    private TextView lettersPlayer1, lettersPlayer2;
    private ImageView playerTurn;
    private TextView wordFormed;
    private EditText playerInput;
    private Button enterButton;
    private Game game;
    private SharedPreferences prefs;

    private final List<String> validInput = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z", "'");
    private final String namePlayer1Key = "namePlayer1Key";
    private final String namePlayer2Key = "namePlayer2Key";
    private final String lettersPlayer1Key = "lettersPlayer1Key";
    private final String lettersPlayer2Key = "lettersPlayer2Key";
    private final String playerTurnKey = "playerTurnKey";
    private final String wordFormedKey = "wordFormedKey";
    private final String playerInputKey = "playerInputKey";

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ghost_game);
        initializeViews();
        /*
         * Check whether a saved game is present.
         */
        prefs = getSharedPreferences(Settings.prefsName, MODE_PRIVATE);
        boolean savedGame = prefs.getBoolean(savedGameKey, false);
        if(!savedGame) {
            /*
             * Start/create a new game.
             */
            setPlayerNames();
            Lexicon lexicon = createLexicon();
            createNewGame(lexicon);
        }
        else {
            /*
             * Start/create a saved game.
             */
            getSavedDataAndCreateSavedGame();
            setSavedGameToFalse();
        }
    }

    /*
     * Initializes all the (required) views.
     */
    private void initializeViews() {
        textNamePlayer1 = (TextView) findViewById(R.id.player1_name_textView);
        textNamePlayer2 = (TextView) findViewById(R.id.player2_name_textView);
        lettersPlayer1 = (TextView) findViewById(R.id.player1_letters_textView);
        lettersPlayer2 = (TextView) findViewById(R.id.player2_letters_textView);
        playerTurn = (ImageView) findViewById(R.id.turn_imageView);
        wordFormed = (TextView) findViewById(R.id.word_textView);
        playerInput = (EditText) findViewById(R.id.player_input_editText);
        enterButton = (Button) findViewById(R.id.enter_button);
    }

    /*
     * Retrieves the player names from the Activity that called and sets the player names in the
     * corresponding TextViews.
     */
    private void setPlayerNames() {
        Intent activityThatCalled = getIntent();
        Bundle extras = activityThatCalled.getExtras();
        namePlayer1 = extras.getString(PlayerSelect.keyPlayer1Name);
        namePlayer2 = extras.getString(PlayerSelect.keyPlayer2Name);
        textNamePlayer1.setText(namePlayer1 + " (1)");
        textNamePlayer2.setText(namePlayer2 + " (2)");
    }

    /*
     * Creates and returns a Lexicon instance (see Lexicon.java) based on the preferred dictionary
     * language (Dutch or English). If no preference is set, a Dutch Lexicon instance is created and
     * returned.
     */
    private Lexicon createLexicon() {
        String language = prefs.getString(Settings.languageKey, "");
        Lexicon lexicon;
        Context context = getApplicationContext();
        switch(language) {
            case Settings.dutchLanguage:
                lexicon = new Lexicon(context, Settings.dutchLanguage);
                break;
            case Settings.englishLanguage:
                lexicon = new Lexicon(context, Settings.englishLanguage);
                break;
            default:
                lexicon = new Lexicon(context, Settings.dutchLanguage);
        }
        return lexicon;
    }

    /*
     * Creates a Game instance (see Game.java) and uses the method 'setImageTurnAndHighlightPlayer()'
     * to:
     * 1) Set the source of the ImageView that indicates the player turn, and
     * 2) Make the name of the player whose turn it is bold.
     */
    private void createNewGame(Lexicon lexicon) {
        game = new Game(lexicon);
        setImageTurnAndHighlightPlayer();
        Toast.makeText(getApplication(), getString(R.string.ghost_game_text_start1) + " " +
                game.turn() + " " + getString(R.string.ghost_game_text_start2),
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Sets the source of the ImageView that indicates the player turn and makes the name of the
     * player whose turn it is bold (and make the name of the other player not bold).
     */
    private void setImageTurnAndHighlightPlayer() {
        if (game.turn() == 1) {
            textNamePlayer1.setTypeface(Typeface.DEFAULT_BOLD);
            textNamePlayer2.setTypeface(Typeface.DEFAULT);
            playerTurn.setImageResource(R.drawable.ghost);
        }
        else {
            textNamePlayer1.setTypeface(Typeface.DEFAULT);
            textNamePlayer2.setTypeface(Typeface.DEFAULT_BOLD);
            playerTurn.setImageResource(R.drawable.ghost2);
        }
    }

    /*
     * Retrieves all the saved game data from the shared preferences and uses the method
     * 'setSavedDataAndCreateSavedGame(...)' to set the saved game data in the corresponding
     * TextViews and to create a Game instance.
     */
    private void getSavedDataAndCreateSavedGame() {
        namePlayer1 = prefs.getString(namePlayer1Key, "");
        namePlayer2 = prefs.getString(namePlayer2Key, "");
        String lettersPlayer1String = prefs.getString(lettersPlayer1Key, "");
        String lettersPlayer2String = prefs.getString(lettersPlayer2Key, "");
        int playerTurn = prefs.getInt(playerTurnKey, 1);
        String wordFormedString = prefs.getString(wordFormedKey, "");
        String playerInputString = prefs.getString(playerInputKey, "");
        setSavedDataAndCreateSavedGame(lettersPlayer1String, lettersPlayer2String, playerTurn,
                                       wordFormedString, playerInputString);
    }

    /*
     * Sets the saved game data in the corresponding TextViews, uses the method 'createLexicon()'
     * to create a Lexicon instance (see Lexicon.java), and uses the method 'createSavedGame(...)'
     * to create a Game instance.
     */
    private void setSavedDataAndCreateSavedGame(String lettersPlayer1String, String lettersPlayer2String,
                                                int playerTurn, String wordFormedString,
                                                String playerInputString) {
        textNamePlayer1.setText(namePlayer1 + " (1)");
        textNamePlayer2.setText(namePlayer2 + " (2)");
        lettersPlayer1.setText(lettersPlayer1String);
        lettersPlayer2.setText(lettersPlayer2String);
        wordFormed.setText(wordFormedString);
        playerInput.setText(playerInputString);
        Lexicon lexicon = createLexicon();
        createSavedGame(lexicon, lettersPlayer1String, lettersPlayer2String, playerTurn, wordFormedString);
    }

    /*
     * Creates a Game instance (see Game.java) and uses the method 'setImageTurnAndHighlightPlayer()'
     * to:
     * 1) Set the source of the ImageView that indicates the player turn, and
     * 2) Make the name of the player whose turn it is bold.
     */
    private void createSavedGame(Lexicon lexicon, String lettersPlayer1, String lettersPlayer2,
                                 int playerTurn, String wordFormed) {
        game = new Game(lexicon, lettersPlayer1, lettersPlayer2, playerTurn, wordFormed);
        setImageTurnAndHighlightPlayer();
        Toast.makeText(getApplication(), getString(R.string.ghost_game_text_start1) + " " +
                       game.turn() + " " + getString(R.string.ghost_game_text_start2),
                       Toast.LENGTH_SHORT).show();
    }

    /*
     * Initializes the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_game, menu);
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
         * Handles on 'New game' click.
         */
        if(id == R.id.action_new_game) {
            handleNewGameAction();
            return true;
        }
        /*
         * Handles on 'Change language' click.
         */
        else if(id == R.id.action_change_language) {
            handleChangeLanguageOption();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Handles a click on the 'New game' menu option. If this menu option is clicked, the Ghost game
     * Activity is finished and the user is directed to the player select Activity (see
     * PlayerSelect.java).
     */
    private void handleNewGameAction() {
        setSavedGameToFalse();
        Intent goToPlayerSelect = new Intent(getApplicationContext(), PlayerSelect.class);
        startActivity(goToPlayerSelect);
        finish();
    }

    /*
     * Handles a click on the 'Change language' menu option. If this menu option is clicked, the
     * user is directed to the settings Activity (see Settings.java). The ghost game Activity
     * expects a result from the setting Activity (i.e., a boolean value that indicates whether
     * the dictionary language has been changed).
     */
    private void handleChangeLanguageOption() {
        Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
        goToSettings.putExtra(Settings.activityThatCalledKey, activityName);
        String setLanguageBeforeCall = prefs.getString(Settings.languageKey, "");
        goToSettings.putExtra(setLanguageBeforeCallKey, setLanguageBeforeCall);
        int result = 1;
        startActivityForResult(goToSettings, result);
    }

    /*
     * Handles the result of the settings Activity (i.e., the boolean value that indicates whether
     * the dictionary language has been changed, see Settings.java). If the returned boolean is
     * true (i.e., the dictionary language has been changed), simply call the method
     * 'handleNewGameAction()' (i.e., start a new game). If the returned boolean is false,
     * simply show a toast that the dictionary language has not been changed.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean languageChanged = data.getBooleanExtra(Settings.languageChangedKey, false);
        if(languageChanged) {
            handleNewGameAction();
        }
        else {
            setSavedGameToFalse();
            Toast.makeText(getApplicationContext(), R.string.ghost_game_text_language_not_changed,
                           Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Handles a click on the 'Enter' button.
     */
    public void onInputButtonClick(View view) {
        enterButton.setEnabled(false);
        enterButton.setClickable(false);
        /*
         * Process the player input.
         */
        if(processInput()) {
            /*
             * Check whether the round has ended.
             */
            if (game.endRound(getApplicationContext())) {
                setLettersLosingPlayer();
                /*
                 * Check whether the game has ended.
                 */
                if (game.ended()) {
                    handleEndGame();
                } else {
                    game.startNewRound();
                    wordFormed.setText("");
                }
            }
            game.changeTurn();
            setImageTurnAndHighlightPlayer();
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.ghost_game_text_invalid_input),
                           Toast.LENGTH_SHORT).show();
        }
        enterButton.setEnabled(true);
        enterButton.setClickable(true);
    }

    /*
     * Processes the player input and returns a boolean value that indicates whether the input is
     * successfully processed or not.
     */
    private boolean processInput() {
        String inputLetter = String.valueOf(playerInput.getText()).toUpperCase();
        /*
         * Check whether the input is invalid.
         */
        if(!validInput.contains(inputLetter)) {
            return false;
        }
        else {
            /*
             * Append the player input (i.e., a letter) to the word formed thus far, obtain the new
             * word fragment, and process this word fragment.
             */
            playerInput.setText("");
            wordFormed.append(inputLetter);
            String word = String.valueOf(wordFormed.getText()).toLowerCase();
            game.setWordFormed(word);
            game.guess();
            return true;
        }
    }

    /*
     * Sets the letters of the player that has lost the round.
     */
    private void setLettersLosingPlayer() {
        if(game.turn() == 1) {
            game.setLettersPlayer1();
            lettersPlayer1.setText(game.getLettersPlayer1());
        } else {
            game.setLettersPlayer2();
            lettersPlayer2.setText(game.getLettersPlayer2());
        }
    }

    /*
     * Obtains the player that has won the game (i.e., player 1 or player 2) and directs the user
     * to the results Activity (see Results.java). The name of the player that has won the game is
     * passed to the results Activity.
     */
    private void handleEndGame() {
        wordFormed.setText("");
        setSavedGameToFalse();
        int winner = game.winner();
        Intent goToResults = new Intent(getApplicationContext(), Results.class);
        if(winner == 1) {
            goToResults.putExtra(nameWinnerKey, namePlayer1);
        } else {
            goToResults.putExtra(nameWinnerKey, namePlayer2);
        }
        startActivity(goToResults);
        finish();
    }

    /*
     * Sets the value of the key 'savedGameKey' in the shared preferences to false.
     */
    private void setSavedGameToFalse() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(savedGameKey, false);
        editor.apply();
    }

    /*
     * Handles a click on the physical back button. This method uses the method 'saveGameState()' to
     * save all the game data to the shared preferences.
     */
    @Override
    public void onBackPressed() {
        saveGameState();
        super.onBackPressed();
    }

    /*
     * Saves additional data about the Activity state. This method uses the method 'saveGameState()'
     * to save all the game data to the shared preferences.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveGameState();
    }

    /*
     * Saves all the game data to the shared preferences.
     */
    private void saveGameState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(namePlayer1Key, namePlayer1);
        editor.putString(namePlayer2Key, namePlayer2);
        editor.putString(lettersPlayer1Key, String.valueOf(lettersPlayer1.getText()));
        editor.putString(lettersPlayer2Key, String.valueOf(lettersPlayer2.getText()));
        editor.putInt(playerTurnKey, game.turn());
        editor.putString(wordFormedKey, String.valueOf(wordFormed.getText()));
        editor.putString(playerInputKey, String.valueOf(playerInput.getText()));
        editor.putBoolean(savedGameKey, true);
        editor.apply();
        Toast.makeText(getApplicationContext(), R.string.ghost_game_text_game_saved,
                       Toast.LENGTH_SHORT).show();
    }
}