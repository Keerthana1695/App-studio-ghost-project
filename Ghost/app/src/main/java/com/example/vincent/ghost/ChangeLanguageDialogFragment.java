/*
 * ChangeLanguageDialogFragment.java
 *
 * This class implements a dialog that asks the user(s) whether he/she/(they) is/(are) sure to
 * change the preferred dictionary language. This dialog is shown when the user changes the
 * preferred dictionary language while playing the Ghost game (see GhostGame.java).
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
import android.widget.Toast;


public class ChangeLanguageDialogFragment extends DialogFragment {

    /*
     * Builds a custom Dialog container.
     */
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        /*
         * Creates the dialog.
         */
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.change_language_dialog_text_title);
        theDialog.setMessage(R.string.change_language_dialog_text_message);

        /*
         * Implements the behaviour of the positive button (i.e., 'OK'). If the positive button is
         * clicked, the value of the key 'savedGameKey' (defined in GhostGame.java) in the shared
         * preferences is set to false, and the user will be directed to the player select Activity
         * (see PlayerSelect.java).
         */
        theDialog.setPositiveButton(R.string.change_language_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getActivity().getSharedPreferences(Settings.prefsName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(GhostGame.savedGameKey, false);
                editor.apply();
                Intent goToPlayerSelect = new Intent(getActivity().getApplicationContext(), PlayerSelect.class);
                startActivity(goToPlayerSelect);
                getActivity().finish();
            }
        });

        /*
         * Implements the behaviour of the negative button (i.e., 'Cancel'). If the negative button
         * is clicked, the value of the key 'languageKey' (defined in Settings.java) in the shared
         * preferences is set to the language before the user tried to change the preferred
         * dictionary language. Furthermore, a toast will be shown saying that the preferred
         * dictionary language has not been changed.
         */
        theDialog.setNegativeButton(R.string.change_language_dialog_text_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String setLanguageBeforeCall = getArguments().getString(GhostGame.setLanguageBeforeCallKey);
                SharedPreferences prefs = getActivity().getSharedPreferences(Settings.prefsName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Settings.languageKey, setLanguageBeforeCall);
                editor.apply();
                Toast.makeText(getActivity().getApplicationContext(),
                               R.string.ghost_game_text_language_not_changed, Toast.LENGTH_SHORT).show();
            }
        });

        return theDialog.create();
    }
}