package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//public class GhostGame extends AppCompatActivity {
public class GhostGame extends Activity {

    private TextView namePlayer1, namePlayer2;
    private TextView lettersPlayer1, lettersPlayer2;
    private TextView wordFormed;
    private EditText playerInput;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ghost_game);
        initializeViews();
        Intent activityThatCalled = getIntent();
        String player1Name = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer1Name);
        String player2Name = activityThatCalled.getExtras().getString(PlayerSelect.keyPlayer2Name);
        namePlayer1.setText(player1Name);
        namePlayer2.setText(player2Name);
        Lexicon lexicon = new Lexicon(getApplicationContext(), "TEST");
        game = new Game(lexicon);
    }

    private void initializeViews() {
        namePlayer1 = (TextView) findViewById(R.id.player1_name_textView);
        namePlayer2 = (TextView) findViewById(R.id.player2_name_textView);
        lettersPlayer1 = (TextView) findViewById(R.id.player1_letters_textView);
        lettersPlayer2 = (TextView) findViewById(R.id.player2_letters_textView);
        wordFormed = (TextView) findViewById(R.id.word_textView);
        playerInput = (EditText) findViewById(R.id.player_input_editText);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onInputButtonClick(View view) {
        processInput();
        if (game.endRound()) {
            setLettersLosingPlayer();
            if(game.ended()) {
                int winner = game.winner();
                System.out.println("Game has ended! Player " + String.valueOf(winner) + " has won the game!");
                wordFormed.setText("");
            } else {
                System.out.println("New round!");
                game.startNewRound();
                wordFormed.setText("");
            }
        }
        game.changeTurn();
    }

    private void processInput() {
        System.out.println("Player " + String.valueOf(game.turn()) + "'s turn!");
        String inputLetter = String.valueOf(playerInput.getText()).toUpperCase();
        playerInput.setText("");
        wordFormed.append(inputLetter);
        String word = String.valueOf(wordFormed.getText()).toLowerCase();
        game.setWordFormed(word);
        game.guess(word);
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
