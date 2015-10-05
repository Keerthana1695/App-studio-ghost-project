package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private Game game;

    private final List<String> validInput = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "'");

    public static final String nameWinnerKey = "nameWinnerKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ghost_game);
        initializeViews();
        setPlayerNames();
        createGame();
    }

    private void initializeViews() {
        textNamePlayer1 = (TextView) findViewById(R.id.player1_name_textView);
        textNamePlayer2 = (TextView) findViewById(R.id.player2_name_textView);
        lettersPlayer1 = (TextView) findViewById(R.id.player1_letters_textView);
        lettersPlayer2 = (TextView) findViewById(R.id.player2_letters_textView);
        playerTurn = (ImageView) findViewById(R.id.turn_imageView);
        wordFormed = (TextView) findViewById(R.id.word_textView);
        playerInput = (EditText) findViewById(R.id.player_input_editText);
    }

    private void setPlayerNames() {
        Intent activityThatCalled = getIntent();
        namePlayer1 = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer1Name);
        namePlayer2 = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer2Name);
        textNamePlayer1.setText(namePlayer1 + " (1)");
        textNamePlayer2.setText(namePlayer2 + " (2)");
    }

    private void createGame() {
        SharedPreferences prefs = getSharedPreferences(Settings.prefsName, MODE_PRIVATE);
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
        game = new Game(lexicon);
        setImageTurnAndPlayerText();
        Toast.makeText(getApplication(), "Player " + game.turn() + " starts the game. Good luck!", Toast.LENGTH_LONG).show();
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
            Intent goToPlayerSelect = new Intent(getApplication(), PlayerSelect.class);
            startActivity(goToPlayerSelect);
            finish();
            return true;
        }
        else if(id == R.id.action_change_language) {
            Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
            startActivity(goToSettings);
            // Do not finish the activity!
            return true;
        }
        else if(id == R.id.action_change_player_names){
            Intent goToPlayerSelect = new Intent(getApplication(), PlayerSelect.class);
            startActivity(goToPlayerSelect);
            // Do not finish the activity!
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onInputButtonClick(View view) {
        if(processInput()) {
            if (game.endRound()) {
                setLettersLosingPlayer();
                if (game.ended()) {
                    wordFormed.setText("");
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
                    Toast.makeText(getApplicationContext(), "New round!", Toast.LENGTH_SHORT).show();
                    game.startNewRound();
                    wordFormed.setText("");
                }
            }
            game.changeTurn();
            setImageTurnAndPlayerText();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid letter!", Toast.LENGTH_SHORT).show();
        }
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
}
