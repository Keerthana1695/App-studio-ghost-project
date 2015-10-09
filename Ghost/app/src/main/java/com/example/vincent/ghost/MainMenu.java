package com.example.vincent.ghost;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//public class MainMenu extends AppCompatActivity {
public class MainMenu extends Activity {

    public static final String activityThatCalledKey = "activityThatCalledKey";
    public static final String activityName = "MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_menu);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }
    */

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

    public void onHighscoresClickInMainMenu(View view) {
        Intent goToHighscores = new Intent(getApplicationContext(), Highscores.class);
        startActivity(goToHighscores);
    }

    public void onHowToPlayClick(View view) {
        Intent goToHowToPlay = new Intent(getApplicationContext(), HowToPlay.class);
        startActivity(goToHowToPlay);
    }

    public void onSettingsClick(View view) {
        Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
        goToSettings.putExtra(activityThatCalledKey, activityName);
        startActivity(goToSettings);
    }

    public void onExitClick(View view) {
        handleExit();
    }

    @Override
    public void onBackPressed() {
        handleExit();
        //super.onBackPressed();
    }

    private void handleExit() {
        DialogFragment myExitDialogFragment = new ExitDialogFragment();
        myExitDialogFragment.show(getFragmentManager(), "theExitDialog");
    }
}
