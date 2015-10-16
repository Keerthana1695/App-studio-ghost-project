/*
 * Results.java
 *
 * The results Activity. This Activity congratulates the winner of the game and allows the user(s)
 * to navigate to the highscores, the main menu, or to start a new game.
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * The necessary import statements.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Hashtable;


public class Results extends Activity {

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_results);
        setMessageWinnerAndUpdateHighscores();
    }

    /*
     * Sets the congratulatory message for the winner of the game and updates the highscores.
     */
    private void setMessageWinnerAndUpdateHighscores() {
        Intent activityThatCalled = getIntent();
        String nameWinner = activityThatCalled.getExtras().getString(GhostGame.nameWinnerKey);
        TextView winnerText = (TextView) findViewById(R.id.winner_textView);
        winnerText.setText(getString(R.string.results_text_winner1) + " " + nameWinner +
                           getString(R.string.results_text_winner2));
        updateHighscores(nameWinner);
    }

    /*
     * Updates the highscores. A HighscoresData instance (see HighscoresData.java) is created
     * with which the Hashtable with name-score pairs is obtained. The current score of the winner
     * is retrieved from this Hashtable and is incremented by one. The highscores are (actually)
     * updated using the method 'addNameAndScore(...)', which is defined in HighscoresData.java.
     */
    private void updateHighscores(String nameWinner) {
        HighscoresData highscoresData = new HighscoresData(getApplicationContext());
        Hashtable<String, Integer> highscores = highscoresData.getNamesAndScores();
        int currentScoreWinner = highscores.get(nameWinner);
        int newScore = currentScoreWinner + 1;
        highscoresData.addNameAndScore(getApplicationContext(), nameWinner, newScore);
    }

    /*
     * Handles a click on the 'Highscores' TextView; directs the user to the highscores Activity
     * (see Highscores.java).
     */
    public void onHighscoresClickInResults(View view) {
        Intent goToHighscores = new Intent(getApplicationContext(), Highscores.class);
        startActivity(goToHighscores);
        finish();
    }

    /*
     * Handles a click on the 'New game' TextView; directs the user to the player select Activity
     * (see PlayerSelect.java).
     */
    public void onNewGameClick(View view) {
        Intent goToPlayerSelect = new Intent(getApplicationContext(), PlayerSelect.class);
        startActivity(goToPlayerSelect);
        finish();
    }

    /*
     * Handles a click on the 'Main menu' TextView; directs the user to the main menu (see
     * MainMenu.java).
     */
    public void onMainMenuClick(View view) {
        finish();
    }
}