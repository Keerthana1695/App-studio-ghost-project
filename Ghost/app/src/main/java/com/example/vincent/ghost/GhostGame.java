package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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

    private TextView namePlayer1, namePlayer2;
    private TextView lettersPlayer1, lettersPlayer2;
    private ImageView playerTurn;
    private TextView wordFormed;
    private EditText playerInput;
    private Game game;

    private final List<String> validInput = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "'");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ghost_game);
        initializeViews();
        setPlayerNames();
        createGame();
    }

    private void initializeViews() {
        namePlayer1 = (TextView) findViewById(R.id.player1_name_textView);
        namePlayer2 = (TextView) findViewById(R.id.player2_name_textView);
        lettersPlayer1 = (TextView) findViewById(R.id.player1_letters_textView);
        lettersPlayer2 = (TextView) findViewById(R.id.player2_letters_textView);
        playerTurn = (ImageView) findViewById(R.id.turn_imageView);
        wordFormed = (TextView) findViewById(R.id.word_textView);
        playerInput = (EditText) findViewById(R.id.player_input_editText);
    }

    private void setPlayerNames() {
        Intent activityThatCalled = getIntent();
        String player1Name = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer1Name);
        String player2Name = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer2Name);
        namePlayer1.setText(player1Name + " (1)");
        namePlayer2.setText(player2Name + " (2)");
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
        setImageTurn();
        Toast.makeText(getApplication(), "Player " + game.turn() + " starts the game. Good luck!", Toast.LENGTH_LONG).show();
        // Start a new intent.
    }

    private void setImageTurn() {
        if (game.turn() == 1) {
            playerTurn.setImageResource(R.drawable.ghost);
        }
        else {
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
                    int winner = game.winner();
                    Toast.makeText(getApplicationContext(), "Game has ended! Player " + String.valueOf(winner) + " has won the game!", Toast.LENGTH_LONG).show();
                    wordFormed.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "New round!", Toast.LENGTH_SHORT).show();
                    game.startNewRound();
                    wordFormed.setText("");
                }
            }
            game.changeTurn();
            setImageTurn();
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
