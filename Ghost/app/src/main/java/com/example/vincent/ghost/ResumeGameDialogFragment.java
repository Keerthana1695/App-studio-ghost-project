package com.example.vincent.ghost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ResumeGameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the dialog.
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.resume_game_dialog_text_title);
        theDialog.setMessage(R.string.resume_game_dialog_text_message);

        theDialog.setPositiveButton(R.string.resume_game_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent goToGhostGame = new Intent(getActivity().getApplicationContext(), GhostGame.class);
                startActivity(goToGhostGame);
            }
        });

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
