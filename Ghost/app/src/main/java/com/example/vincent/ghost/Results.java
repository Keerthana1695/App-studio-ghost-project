package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.TreeMap;

//public class Results extends AppCompatActivity {
public class Results extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_results);
        TextView winnerText = (TextView) findViewById(R.id.winner_textView);
        Intent activityThatCalled = getIntent();
        Bundle extras = activityThatCalled.getExtras();
        String nameWinner = extras.getString(GhostGame.nameWinnerKey);
        winnerText.setText(getString(R.string.results_text_winner1) + " " + nameWinner + getString(R.string.results_text_winner2));
        updateHighscores(nameWinner);
    }

    private void updateHighscores(String nameWinner) {
        HighscoresData highscoresData = new HighscoresData(getApplicationContext());
        Hashtable<String, Integer> highscores = highscoresData.getNamesAndScores();
        int scoreWinner = highscores.get(nameWinner);
        scoreWinner++;
        highscoresData.addNameAndScore(getApplicationContext(), nameWinner, scoreWinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onHighscoresClickInResults(View view) {
        Intent goToHighscores = new Intent(getApplicationContext(), Highscores.class);
        startActivity(goToHighscores);
        finish();
    }

    public void onNewGameClick(View view) {
        Intent goToPlayerSelect = new Intent(getApplicationContext(), PlayerSelect.class);
        startActivity(goToPlayerSelect);
        finish();
    }

    public void onMainMenuClick(View view) {
        //Intent goToMainMenu = new Intent(getApplicationContext(), MainMenu.class);
        //startActivity(goToMainMenu);
        finish();
    }
}
