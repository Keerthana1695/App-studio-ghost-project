package com.example.vincent.ghost;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class ExitDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        // Create the dialog.
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        theDialog.setTitle(R.string.exit_dialog_text_title);
        theDialog.setMessage(R.string.exit_dialog_text_message);

        theDialog.setPositiveButton(R.string.resume_game_dialog_text_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        theDialog.setNegativeButton(R.string.exit_dialog_text_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), R.string.exit_dialog_text_toast, Toast.LENGTH_SHORT).show();
            }
        });

        return theDialog.create();
    }
}
