/*
 * ResumeGameDialogFragment.java
 *
 * This class implements a dialog that asks the user(s) whether he/she/(they) want(s) to resume an
 * unfinished game or start a new game. This dialog is shown when the user presses the 'Start a
 * game' TextView in the main menu and the application has detected an unfinished game.
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class ResumeGameDialogFragment extends DialogFragment {

    /*
     * Builds a custom Dialog container.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*
         * Create the dialog.
         */
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.resume_game_dialog_text_title);
        theDialog.setMessage(R.string.resume_game_dialog_text_message);

        /*
         * Implements the behaviour of the positive button (i.e., 'OK'). If the positive button is
         * clicked, the user will be directed to the Ghost game Activity (see GhostGame.java).
         */
        theDialog.setPositiveButton(R.string.resume_game_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent goToGhostGame = new Intent(getActivity().getApplicationContext(), GhostGame.class);
                startActivity(goToGhostGame);
            }
        });

        /*
         * Implements the behaviour of the negative button (i.e., 'No, start a new game'). If the
         * negative button is clicked, the value of the key 'savedGameKey' (defined in
         * GhostGame.java) in the shared preferences is set to false. Furthermore, the user will be
         * directed to the player select Activity (see PlayerSelect.java).
         */
        theDialog.setNegativeButton(R.string.resume_game_dialog_text_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getActivity().getSharedPreferences(Settings.prefsName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(GhostGame.savedGameKey, false);
                editor.apply();
                Intent goToPlayerSelect = new Intent(getActivity().getApplicationContext(), PlayerSelect.class);
                startActivity(goToPlayerSelect);
            }
        });

        return theDialog.create();
    }
}