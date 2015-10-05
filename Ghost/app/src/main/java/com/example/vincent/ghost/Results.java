package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//public class Results extends AppCompatActivity {
public class Results extends Activity {

    private TextView winnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_results);
        winnerText = (TextView) findViewById(R.id.winner_textView);
        Intent activityThatCalled = getIntent();
        String nameWinner = activityThatCalled.getExtras().getString(GhostGame.nameWinnerKey);
        winnerText.setText(getString(R.string.results_text_winner1) + " " + nameWinner + getString(R.string.results_text_winner2));
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
        Intent goToMainMenu = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(goToMainMenu);
        finish();
    }
}
