/*
 * ExitDialogFragment.java
 *
 * A dialog that asks the user whether he/she is sure to exit the game. This dialog is shown when
 * the user presses the exit icon or the physical back button in the main menu (MainMenu.java).
 *
 * Author: Vincent Erich
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class ExitDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        /*
         * Create the dialog.
         */
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.exit_dialog_text_title);
        theDialog.setMessage(R.string.exit_dialog_text_message);

        /*
         * Implements the behaviour of the positive button ('OK'). If the positive button is
         * clicked, the activity will be finished (i.e., MainMenu.java).
         */
        theDialog.setPositiveButton(R.string.exit_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        /*
         * Implements the behaviour of the negative button ('Cancel'). If the negative button is
         * clicked, nothing happens (a toast is shown indicating that the exit has been canceled).
         */
        theDialog.setNegativeButton(R.string.exit_dialog_text_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), R.string.exit_dialog_text_toast, Toast.LENGTH_SHORT).show();
            }
        });

        return theDialog.create();
    }
}