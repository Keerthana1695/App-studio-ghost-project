package com.example.vincent.ghost;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

//public class Settings extends AppCompatActivity {
public class Settings extends Activity {

    private RadioGroup radioLanguageGroup;
    private SharedPreferences prefs;

    public static final String prefsName = "myPREFERENCES";
    public static final String languageKey = "languageKey";
    public static final String dutchLanguage = "DUTCH";
    public static final String englishLanguage = "ENGLISH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        radioLanguageGroup = (RadioGroup) findViewById(R.id.language_radioGroup);
        setPreviouslySelectedLanguage();
        setRadioGroupListener();
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
}
