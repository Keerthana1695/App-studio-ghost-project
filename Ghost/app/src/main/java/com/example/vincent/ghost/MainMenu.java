/*
 * MainMenu.java
 *
 * The main menu Activity. This is the launch Activity (i.e., the Activity that is shown to the
 * user(s) when he/she/(they) start(s) the application). From this Activity, the user(s) can start
 * a (new) game, and go to the highscores, the how to play, and the settings.
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * The necessary import statements.
 */
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class MainMenu extends Activity {

    /*
     * Properties of the class.
     */
    public static final String activityName = "MainMenu";

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_menu);
    }

    /*
     * Handles a click on the 'Start a game' TextView. It is first checked whether the value of
     * the key 'savedGameKey' (defined in GhostGame.java) in the shared preferences is false. If so,
     * the user is directed to the player select Activity (see PlayerSelect.java). If not, a dialog
     * is shown to the user that asks whether he/she wants to start a new game or wants to resume
     * the saved game (see ResumeGameDialogFragment.java).
     */
    public void onStartAGameClick(View view) {
        SharedPreferences prefs = getSharedPreferences(Settings.prefsName, MODE_PRIVATE);
        boolean savedGame = prefs.getBoolean(GhostGame.savedGameKey, false);
        if(!savedGame) {
            Intent goToPlayerSelect = new Intent(getApplicationContext(), PlayerSelect.class);
            startActivity(goToPlayerSelect);
        }
        else {
            DialogFragment myResumeGameDialogFragment = new ResumeGameDialogFragment();
            myResumeGameDialogFragment.show(getFragmentManager(), "theResumeGameDialog");
        }
    }

    /*
     * Handles a click on the 'Highscores' TextView; directs the user to the highscores Activity
     * (see Highscores.java).
     */
    public void onHighscoresClickInMainMenu(View view) {
        Intent goToHighscores = new Intent(getApplicationContext(), Highscores.class);
        startActivity(goToHighscores);
    }

    /*
     * Handles a click on the 'How to play' TextView; directs the user to the 'how to play' Activity
     * (see HowToPlay.java).
     */
    public void onHowToPlayClick(View view) {
        Intent goToHowToPlay = new Intent(getApplicationContext(), HowToPlay.class);
        startActivity(goToHowToPlay);
    }

    /*
     * Handles a click on the 'Settings' TextView; directs the user to the settings Activity (see
     * Settings.java).
     */
    public void onSettingsClick(View view) {
        Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
        goToSettings.putExtra(Settings.activityThatCalledKey, activityName);
        startActivity(goToSettings);
    }

    /*
     * Handles a click on the 'Credits' TextView; directs the user to the credits Activity (see
     * Credits.java).
     */
    public void onCreditsClick(View view) {
        Intent goToCredits = new Intent(getApplicationContext(), Credits.class);
        startActivity(goToCredits);
    }

    /*
     * Handles a click on the 'Exit' TextView; calls the method 'handleExit()'.
     */
    public void onExitClick(View view) {
        handleExit();
    }

    /*
     * Handles a click on the physical back button; calls the method 'handleExit()'.
     */
    @Override
    public void onBackPressed() {
        handleExit();
    }

    /*
     * Shows a dialog that asks the user whether he/she is sure to exit the game (see
     * ExitDialogFragment.java).
     */
    private void handleExit() {
        DialogFragment myExitDialogFragment = new ExitDialogFragment();
        myExitDialogFragment.show(getFragmentManager(), "theExitDialog");
    }
}