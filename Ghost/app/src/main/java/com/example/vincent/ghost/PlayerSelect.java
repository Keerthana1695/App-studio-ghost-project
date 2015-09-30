package com.example.vincent.ghost;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

//public class PlayerSelect extends AppCompatActivity {
public class PlayerSelect extends Activity {

    private Spinner player1Spinner, player2Spinner;
    private EditText player1EditText, player2EditText;
    private Players players;
    private ArrayList<String> playerNames;
    private ArrayAdapter<String> spinnerAdapter;

    private final String keyPlayer1EditText = "player1EditText";
    private final String keyPlayer2EditText = "player2EditText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player_select);
        players = new Players(getApplicationContext());
        playerNames = players.getPlayerNames();
        initializeViews();
        addItemsToSpinners();
    }

    private void initializeViews() {
        player1Spinner = (Spinner) findViewById(R.id.player1_spinner);
        player2Spinner = (Spinner) findViewById(R.id.player2_spinner);
        player1EditText = (EditText) findViewById(R.id.player1_editText);
        player2EditText = (EditText) findViewById(R.id.player2_editText);
    }

    private void addItemsToSpinners() {
        spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, playerNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        player1Spinner.setAdapter(spinnerAdapter);
        player2Spinner.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Finish this activity.
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNameButton1Click(View view) {
        String player1Name = String.valueOf(player1EditText.getText());
        player1EditText.setText("");
        addPlayer(player1Name);
        int spinnerPosition = spinnerAdapter.getPosition(player1Name);
        player1Spinner.setSelection(spinnerPosition);

    }

    public void onNameButton2Click(View view) {
        String player2Name = String.valueOf(player2EditText.getText());
        player2EditText.setText("");
        addPlayer(player2Name);
        int spinnerPosition = spinnerAdapter.getPosition(player2Name);
        player2Spinner.setSelection(spinnerPosition);
    }

    private void addPlayer(String name) {
        players.addPlayerName(getApplicationContext(), name);
        playerNames = players.getPlayerNames();
        spinnerAdapter.notifyDataSetChanged();
    }

    public void onPlayClick(View view) {
        String namePlayer1 = player1Spinner.getSelectedItem().toString();
        String namePlayer2 = player2Spinner.getSelectedItem().toString();
        if(namePlayer1.equals(namePlayer2)) {
            Toast.makeText(getApplicationContext(), R.string.player_select_text_same_name, Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Name player 1: " + namePlayer1);
            System.out.println("Name player 2: " + namePlayer2);
            // Start game...
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(String.valueOf(player1EditText.getText()), keyPlayer1EditText);
        outState.putString(String.valueOf(player2EditText.getText()), keyPlayer2EditText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String inputPlayer1 = savedInstanceState.getString(keyPlayer1EditText);
        String inputPlayer2 = savedInstanceState.getString(keyPlayer2EditText);
        player1EditText.setText(inputPlayer1);
        player2EditText.setText(inputPlayer2);
    }
}
