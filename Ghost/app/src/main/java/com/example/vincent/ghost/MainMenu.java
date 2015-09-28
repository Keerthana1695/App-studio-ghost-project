package com.example.vincent.ghost;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//public class MainMenu extends AppCompatActivity {
public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_menu);

        // Lexicon test...
        /*
        Lexicon lexicon = new Lexicon(getApplicationContext());
        System.out.println(lexicon.baseLexicon);
        System.out.println(lexicon.filteredLexicon);
        System.out.println();

        String input = "a";
        lexicon.filter(input);
        System.out.println(lexicon.baseLexicon);
        System.out.println(lexicon.filteredLexicon);
        System.out.println();

        input = "ap";
        lexicon.filter(input);
        System.out.println(lexicon.baseLexicon);
        System.out.println(lexicon.filteredLexicon);
        System.out.println();

        input="appelboom";
        lexicon.filter(input);
        System.out.println(lexicon.baseLexicon);
        System.out.println(lexicon.filteredLexicon);
        System.out.println(lexicon.result());
        System.out.println();

        lexicon.reset();
        System.out.println(lexicon.baseLexicon);
        System.out.println(lexicon.filteredLexicon);
        System.out.println();
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    public void onStartAGameClick(View view) {
        Intent goToPlayerSelect = new Intent(getApplicationContext(), PlayerSelect.class);
        startActivity(goToPlayerSelect);
    }

    public void onHighscoresClick(View view) {
        Intent goToHighScores = new Intent(getApplicationContext(), Highscores.class);
        startActivity(goToHighScores);
    }

    public void onHowToPlayClick(View view) {
        Intent goToHowToPlay = new Intent(getApplicationContext(), HowToPlay.class);
        startActivity(goToHowToPlay);
    }

    public void onSettingsClick(View view) {
        Intent goToSettings = new Intent(getApplicationContext(), Settings.class);
        startActivity(goToSettings);
    }

    public void onExitClick(View view) {
        DialogFragment myExitDialogFragment = new ExitDialogFragment();
        myExitDialogFragment.show(getFragmentManager(), "theExitDialog");
    }
}
