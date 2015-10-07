package com.example.vincent.ghost;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

//public class Highscores extends AppCompatActivity {
public class Highscores extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_highscores);
        ListView theListView = (ListView) findViewById(R.id.highscores_listView);
        Player[] arrayAdaperData = getArrayAdapterData();
        ArrayAdapter theAdapter = new MyCustomAdapter(getApplicationContext(), arrayAdaperData);
        theListView.setAdapter(theAdapter);
    }

    private Player[] getArrayAdapterData() {
        HighscoresData highscoresData = new HighscoresData(getApplicationContext());
        Hashtable<String, Integer> highscores = highscoresData.getNamesAndScores();
        ArrayList<Map.Entry<String, Integer>> sortedHighscores = sortByValues(highscores);
        Player[] data = new Player[sortedHighscores.size()];
        String name;
        int score;
        Player player;
        for(int i=0; i<sortedHighscores.size(); i++) {
            name = sortedHighscores.get(i).getKey();
            score = sortedHighscores.get(i).getValue();
            player = new Player(name, score, i + 1);
            data[i] = player;
        }
        return data;
    }

    // Source: http://stackoverflow.com/questions/5176771/sort-hashtable-by-values
    private ArrayList<Map.Entry<String, Integer>> sortByValues(Hashtable<String, Integer> unsortedHashtable) {
        // Transfer as List and sort it.
        ArrayList<Map.Entry<String, Integer>> sortedArrayList = new ArrayList(unsortedHashtable.entrySet());
        Collections.sort(sortedArrayList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                return rhs.getValue().compareTo(lhs.getValue());
            }
        });
        return sortedArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
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
