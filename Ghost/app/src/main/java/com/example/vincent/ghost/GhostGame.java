package com.example.vincent.ghost;

import android.app.Activity;
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

//public class GhostGame extends AppCompatActivity {
public class GhostGame extends Activity {

    private TextView textNamePlayer1, textNamePlayer2;
    private String namePlayer1, namePlayer2;
    private TextView lettersPlayer1, lettersPlayer2;
    private ImageView playerTurn;
    private TextView wordFormed;
    private EditText playerInput;
    private Button enterButton;
    private Game game;
    private SharedPreferences prefs;
    private final List<String> validInput = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "'");

    private static final String namePlayer1Key = "namePlayer1Key";
    private static final String namePlayer2Key = "namePlayer2Key";
    private static final String lettersPlayer1Key = "lettersPlayer1Key";
    private static final String lettersPlayer2Key = "lettersPlayer2Key";
    private static final String playerTurnKey = "playerTurnKey";
    private static final String wordFormedKey = "wordFormedKey";
    private static final String playerInputKey = "playerInputKey";

    public static final String nameWinnerKey = "nameWinnerKey";
    public static final String savedGameKey = "savedGameKey";
    public static final String activityName = "GhostGame";
    public static final String setLanguageBeforeCallKey = "setLanguageBeforeCallKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ghost_game);
        initializeViews();
        prefs = getSharedPreferences(Settings.prefsName, MODE_PRIVATE);
        boolean savedGame = prefs.getBoolean(savedGameKey, false);
        Lexicon lexicon;
        if(!savedGame) {
            setPlayerNames();
            lexicon = createLexicon();
            createNewGame(lexicon);
        }
        else {
            // Refactor!
            namePlayer1 = prefs.getString(namePlayer1Key, "");
            namePlayer2 = prefs.getString(namePlayer2Key, "");
            textNamePlayer1.setText(namePlayer1 + " (1)");
            textNamePlayer2.setText(namePlayer2 + " (2)");
            String lettersPlayer1String = prefs.getString(lettersPlayer1Key, "");
            String lettersPlayer2String = prefs.getString(lettersPlayer2Key, "");
            lettersPlayer1.setText(lettersPlayer1String);
            lettersPlayer2.setText(lettersPlayer2String);
            int playerTurn = prefs.getInt(playerTurnKey, 1);
            String wordFormedString = prefs.getString(wordFormedKey, "");
            wordFormed.setText(wordFormedString);
            playerInput.setText(prefs.getString(playerInputKey, ""));
            lexicon = createLexicon();
            createSavedGame(lexicon, lettersPlayer1String, lettersPlayer2String, playerTurn, wordFormedString);
            setSavedGameToFalse();
        }
    }

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

    private void setPlayerNames() {
        Intent activityThatCalled = getIntent();
        namePlayer1 = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer1Name);
        namePlayer2 = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer2Name);
        textNamePlayer1.setText(namePlayer1 + " (1)");
        textNamePlayer2.setText(namePlayer2 + " (2)");
    }

    private Lexicon createLexicon() {
        String language = prefs.getString(Settings.languageKey, "");
        System.out.println(language);
        Lexicon lexicon;
        switch(language) {
            case Settings.dutchLanguage:
                lexicon = new Lexicon(getApplicationContext(), Settings.dutchLanguage);
                break;
            case Settings.englishLanguage:
                lexicon = new Lexicon(getApplicationContext(), Settings.englishLanguage);
                break;
            default:
                lexicon = new Lexicon(getApplicationContext(), Settings.dutchLanguage);
        }
        return lexicon;
    }

    private void createNewGame(Lexicon lexicon) {
        game = new Game(lexicon);
        setImageTurnAndPlayerText();
        Toast.makeText(getApplication(), getString(R.string.ghost_game_text_start1) + " " + game.turn() + " " +
                getString(R.string.ghost_game_text_start2), Toast.LENGTH_SHORT).show();
    }

    private void createSavedGame(Lexicon lexicon, String lettersPlayer1, String lettersPlayer2,
                                 int playerTurn, String wordFormed) {
        game = new Game(lexicon, lettersPlayer1, lettersPlayer2, playerTurn, wordFormed);
        setImageTurnAndPlayerText();
        Toast.makeText(getApplication(), getString(R.string.ghost_game_text_start1) + " " + game.turn() + " " +
                getString(R.string.ghost_game_text_start2), Toast.LENGTH_SHORT).show();
    }

    private void setImageTurnAndPlayerText() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_new_game) {
            setSavedGameToFalse(); // Important!
            System.out.println("savedGame set to false...");
            Intent goToPlayerSelect = new Intent(getApplication(), PlayerSelect.class);
            startActivity(goToPlayerSelect);
            finish();
            return true;
        }
        else if(id == R.id.action_change_language) {
            Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
            goToSettings.putExtra(MainMenu.activityThatCalledKey, activityName);
            String setLanguageBeforeCall = prefs.getString(Settings.languageKey, "");
            goToSettings.putExtra(setLanguageBeforeCallKey, setLanguageBeforeCall);
            int result = 1;
            startActivityForResult(goToSettings, result);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setSavedGameToFalse(); // Important!
        System.out.println("savedGame set to false...");
        boolean languageChanged = data.getBooleanExtra(Settings.languageChangedKey, false);
        if(languageChanged) {
            Intent goToPlayerSelect = new Intent(getApplication(), PlayerSelect.class);
            startActivity(goToPlayerSelect);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.ghost_game_text_language_not_changed, Toast.LENGTH_SHORT).show();
        }
    }

    public void onInputButtonClick(View view) {
        enterButton.setEnabled(false);
        if(processInput()) {
            if (game.endRound()) {
                setLettersLosingPlayer();
                if (game.ended()) {
                    wordFormed.setText("");
                    setSavedGameToFalse(); // Important!
                    int winner = game.winner();
                    Intent goToResults = new Intent(getApplicationContext(), Results.class);
                    if(winner == 1) {
                        goToResults.putExtra(nameWinnerKey, namePlayer1);
                    } else {
                        goToResults.putExtra(nameWinnerKey, namePlayer2);
                    }
                    startActivity(goToResults);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.ghost_game_text_new_round), Toast.LENGTH_SHORT).show();
                    game.startNewRound();
                    wordFormed.setText("");
                }
            }
            game.changeTurn();
            setImageTurnAndPlayerText();
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.ghost_game_text_invalid_input), Toast.LENGTH_SHORT).show();
        }
        enterButton.setEnabled(true);
    }

    private boolean processInput() {
        String inputLetter = String.valueOf(playerInput.getText()).toUpperCase();
        if(!validInput.contains(inputLetter)) {
            return false;
        }
        else {
            playerInput.setText("");
            wordFormed.append(inputLetter);
            String word = String.valueOf(wordFormed.getText()).toLowerCase();
            game.setWordFormed(word);
            game.guess(word);
            return true;
        }
    }

    private void setLettersLosingPlayer() {
        if(game.turn() == 1) {
            game.setLettersPlayer1();
            lettersPlayer1.setText(game.getLettersPlayer1());
        } else {
            game.setLettersPlayer2();
            lettersPlayer2.setText(game.getLettersPlayer2());
        }
    }

    private void setSavedGameToFalse() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(savedGameKey, false);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        saveGameState();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveGameState();
    }

    private void saveGameState() {
        System.out.println("In 'saveGameState' in 'GhostGame'...");
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
    }
}
