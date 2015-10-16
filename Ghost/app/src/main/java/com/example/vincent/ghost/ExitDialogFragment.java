/*
 * ExitDialogFragment.java
 *
 * This class implements a dialog that asks the user(s) whether he/she/(they) is/(are) sure to exit
 * the game. This dialog is shown when the user presses the 'Exit' TextView or the physical back
 * button in the main menu (see MainMenu.java).
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


public class ExitDialogFragment extends DialogFragment {

    /*
     * Builds a custom Dialog container.
     */
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        /*
         * Creates the dialog.
         */
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.exit_dialog_text_title);
        theDialog.setMessage(R.string.exit_dialog_text_message);

        /*
         * Implements the behaviour of the positive button (i.e., 'OK'). If the positive button is
         * clicked, the Activity will be finished (i.e., MainMenu.java).
         */
        theDialog.setPositiveButton(R.string.exit_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        /*
         * Implements the behaviour of the negative button (i.e., 'Cancel'). If the negative button
         * is clicked, a toast will be shown indicating that the exit has been canceled.
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