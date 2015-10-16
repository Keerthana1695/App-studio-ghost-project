/*
 * Settings.java
 *
 * The settings Activity. This Activity allows the user(s) to set the language of the dictionary
 * used in the game (i.e., Dutch or English).
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;


public class Settings extends Activity {

    /*
     * Properties of the class.
     */
    public static final String prefsName = "myPREFERENCES";
    public static final String languageKey = "languageKey";
    public static final String dutchLanguage = "DUTCH";
    public static final String englishLanguage = "ENGLISH";
    public static final String activityThatCalledKey = "activityThatCalledKey";
    public static final String languageChangedKey = "languageChangedKey";

    private RadioGroup radioLanguageGroup;
    private SharedPreferences prefs;

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        radioLanguageGroup = (RadioGroup) findViewById(R.id.language_radioGroup);
        setPreviouslySelectedLanguage();
        setRadioGroupListener();
    }

    /*
     * Obtains the preferred dictionary language from the shared preferences and checks the
     * corresponding RadioButton. If no preference is set, the Dutch RadioButton is checked.
     */
    private void setPreviouslySelectedLanguage() {
        String previousSelection = prefs.getString(languageKey, "");
        switch (previousSelection) {
            case dutchLanguage:
                radioLanguageGroup.check(R.id.dutch_radioButton);
                break;
            case englishLanguage:
                radioLanguageGroup.check(R.id.english_radioButton);
                break;
            default:
                radioLanguageGroup.check(R.id.dutch_radioButton);
        }
    }

    /*
     * Sets/implements a listener for the RadioGroup. The listener is invoked when the checked state
     * of the RadioGroup changes (i.e., when a different RadioButton is checked). When a different
     * RadioButton is checked, the preferred dictionary language in the shared preferences is
     * updated accordingly.
     */
    private void setRadioGroupListener() {
        radioLanguageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = prefs.edit();
                if(checkedId == R.id.dutch_radioButton) {
                    editor.putString(languageKey, dutchLanguage);
                } else {
                    editor.putString(languageKey, englishLanguage);
                }
                editor.apply();
            }
        });
    }

    /*
     * Initializes the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /*
     * Handles clicks on the menu options.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
         * Handles on 'Back' click; calls the method 'handleBackAction()'.
         */
        if (id == R.id.action_back) {
            handleBackAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Handles a click on the physical back button; calls the method 'handleBackAction()'.
     */
    @Override
    public void onBackPressed() {
        handleBackAction();
    }

    /*
     * Called when the user clicks the 'Back' menu item or the physical back button. This method
     * first obtains the name of the Activity that called. There are two Activities that can call
     * this Activity: MainMenu.java and GhostGame.java. If the Activity that called is MainMenu.java,
     * then simply finish this Activity. Otherwise, obtain the preferred dictionary language
     * before the call, and the current preferred dictionary language (might have changed). If the
     * two are different, the user is directed to the Ghost game Activity (see GhostGame.java) with
     * the boolean true as extra. Otherwise, the user is directed to the Ghost game Activity with
     * the boolean false as extra.
     */
    private void handleBackAction() {
        Intent activityThatCalled = getIntent();
        Bundle extras = activityThatCalled.getExtras();
        String nameActivityThatCalled = extras.getString(activityThatCalledKey);
        if(nameActivityThatCalled != null && nameActivityThatCalled.equals(GhostGame.activityName)) {
            String setLanguageBeforeCall = extras.getString(GhostGame.setLanguageBeforeCallKey, "");
            String setLanguage = prefs.getString(languageKey, "");
            Intent goToGhostGame = new Intent();
            if(!setLanguageBeforeCall.equals(setLanguage)) {
                goToGhostGame.putExtra(languageChangedKey, true);
            }
            else {
                goToGhostGame.putExtra(languageChangedKey, false);
            }
            setResult(RESULT_OK, goToGhostGame);
        }
        finish();
    }
}