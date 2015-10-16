/*
 * Game.java
 *
 * A model class that represents a single game. This model class implements all relevant game rules
 * and uses a Lexicon instance (see Lexicon.java) to serve as the lexicon.
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


public class Game {

    /*
     * Properties of the class.
     */
    private Lexicon lexicon;
    private String lettersPlayer1, lettersPlayer2;
    private int playerTurn;
    private String wordFormed;

    /*
     * Constructor of the class. This constructor is called when a new game is created/started.
     */
    public Game(Lexicon lexicon) {
        this.lexicon = lexicon;
        lettersPlayer1 = ".....";
        lettersPlayer2 = ".....";
        playerTurn = 1;
        wordFormed = "";
    }

    /*
     * Constructor of the class. This constructor is called when an unfinished/saved game is
     * created/resumed.
     */
    public Game(Lexicon lexicon, String lettersPlayer1, String lettersPlayer2,
                int playerTurn, String wordFormed) {
        this.lexicon = lexicon;
        this.lettersPlayer1 = lettersPlayer1;
        this.lettersPlayer2 = lettersPlayer2;
        this.playerTurn = playerTurn;
        this.wordFormed = wordFormed;
    }

    /*
     * Returns the property 'playerTurn' (i.e., which player is up for guessing, 1 or 2).
     */
    public int turn() {
        return playerTurn;
    }

    /*
     * Sets the value of the property 'wordFormed' (i.e., the word [fragment] formed thus far).
     */
    public void setWordFormed(String word) {
        wordFormed = word;
    }

    /*
     * Uses the Lexicon instance to decide (see Lexicon.java).
     */
    public void guess() {
        lexicon.filter(wordFormed);
    }

    /*
     * Returns a boolean that indicates whether a round has ended. A round has ended when:
     * 1) No valid word can be formed, or
     * 2) A valid word longer than 3 letters has been formed.
     * If a round has ended, a toast with the reason for this is shown.
     */
    public boolean endRound(Context context) {
        if(lexicon.count() == 0) {
            Toast.makeText(context, "'" + wordFormed + "' " +
                           context.getString(R.string.game_text_no_valid_word),
                           Toast.LENGTH_LONG).show();
            return true;
        }
        else if(wordFormed.length() > 3 && lexicon.getFilteredLexicon().contains(wordFormed)) {
            Toast.makeText(context, "'" + wordFormed + "' " +
                           context.getString(R.string.game_text_valid_word),
                           Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Sets the value of the property 'lettersPlayer1' (i.e., the letters that player 1 has) based
     * on the current value of the property.
     */
    public void setLettersPlayer1() {
        switch(lettersPlayer1) {
            case ".....":
                lettersPlayer1 = "G....";
                break;
            case "G....":
                lettersPlayer1 = "GH...";
                break;
            case "GH...":
                lettersPlayer1 = "GHO..";
                break;
            case "GHO..":
                lettersPlayer1 = "GHOS.";
                break;
            case "GHOS.":
                lettersPlayer1 = "GHOST";
        }
    }

    /*
     * Sets the value of the property 'lettersPlayer2' (i.e., the letters that player 2 has) based
     * on the current value of the property.
     */
    public void setLettersPlayer2() {
        switch(lettersPlayer2) {
            case ".....":
                lettersPlayer2 = "G....";
                break;
            case "G....":
                lettersPlayer2 = "GH...";
                break;
            case "GH...":
                lettersPlayer2 = "GHO..";
                break;
            case "GHO..":
                lettersPlayer2 = "GHOS.";
                break;
            case "GHOS.":
                lettersPlayer2 = "GHOST";
        }
    }

    /*
     * Returns the property 'lettersPlayer1' (i.e., the letters that player 1 has).
     */
    public String getLettersPlayer1() {
        return lettersPlayer1;
    }

    /*
     * Returns the property 'lettersPlayer2' (i.e., the letters that player 2 has).
     */
    public String getLettersPlayer2() {
        return lettersPlayer2;
    }

    /*
     * Resets the lexicon (see Lexicon.java) and the value of the property 'wordFormed' (i.e., the
     * word [fragment] formed thus far).
     */
    public void startNewRound() {
        lexicon.reset();
        wordFormed = "";
    }

    /*
     * Returns a boolean that indicates whether a game has ended. A game has ended when:
     * 1) 'lettersPlayer1' is equal to 'GHOST', or
     * 2) 'lettersPlayer2' is equal to 'GHOST'.
     */
    public boolean ended() {
        return (lettersPlayer1.equals("GHOST") || lettersPlayer2.equals("GHOST"));
    }

    /*
     * Returns an int that indicates which player has won the game (1 or 2).
     */
    public int winner() {
        if(lettersPlayer1.equals("GHOST")) {
            return 2;
        }
        else {
            return 1;
        }
    }

    /*
     * Sets the value of the property 'playerTurn' (i.e., which player is up for guessing) based on
     * the current value of the property.
     */
    public void changeTurn() {
        if(playerTurn == 1) {
            playerTurn = 2;
        }
        else {
            playerTurn = 1;
        }
    }
}