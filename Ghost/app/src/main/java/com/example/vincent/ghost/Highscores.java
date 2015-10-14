/*
 * Highscores.java
 *
 * The highscores Activity. This Activity allows the user(s) to view the (local) highscores(*).
 *
(*) Every time a player wins a game, his/her score is incremented by one.
 *
 * Author: Vincent Erich
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.app.Activity;
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

public class Highscores extends Activity {

    /*
     * Initializes the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_highscores);
        ListView theListView = (ListView) findViewById(R.id.highscores_listView);
        Player[] arrayAdapterData = getArrayAdapterData();
        ArrayAdapter theAdapter = new MyCustomAdapter(getApplicationContext(), arrayAdapterData);
        theListView.setAdapter(theAdapter);
    }

    /*
     * Obtains the data for the ArrayAdapter. A HighscoresData instance (see HighscoresData.java) is
     * created with which a Hashtable is retrieved with name-score pairs. This Hashtable is
     * transferred to an ArrayList with map entries (name-score) sorted by value in descending order
     * (i.e., sorted by score in descending order, see the method 'sortByValue(...)'. This
     * ArrayList is subsequently used to create an array with Player objects (see Player.java),
     * which is the array that is returned by this method.
     */
    private Player[] getArrayAdapterData() {
        HighscoresData highscoresData = new HighscoresData(getApplicationContext());
        Hashtable<String, Integer> highscores = highscoresData.getNamesAndScores();
        ArrayList<Map.Entry<String, Integer>> sortedHighscores = sortByValue(highscores);
        Player[] data = new Player[sortedHighscores.size()];
        String name;
        int score;
        Player player;
        for(int i=0; i<data.length; i++) {
            name = sortedHighscores.get(i).getKey();
            score = sortedHighscores.get(i).getValue();
            player = new Player(name, score, i + 1);
            data[i] = player;
        }
        return data;
    }

    /*
     * Transfers a Hashtable with name-score pairs to an ArrayList with map entries (name-score)
     * sorted by value in descending order (i.e., sorted by score in descending order).
     *
     * NOTE: This code is obtained from stackoverflow. Source: http://stackoverflow.com/a/5176861
     */
    private ArrayList<Map.Entry<String, Integer>> sortByValue(Hashtable<String, Integer> unsortedHashtable) {
        /*
         * Transfer the Hashtable to an ArrayList and sort it.
         */
        ArrayList<Map.Entry<String, Integer>> sortedArrayList = new ArrayList<>(unsortedHashtable.entrySet());
        Collections.sort(sortedArrayList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        return sortedArrayList;
    }

    /*
     * Initializes the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscores, menu);
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
         * Handles on 'Back' click.
         */
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}