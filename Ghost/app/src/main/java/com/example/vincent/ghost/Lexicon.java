/*
 * Lexicon.java
 *
 * A model class that represents a list of words in Dutch or English (i.e., a lexicon) that can be
 * filtered by prefix.
 *
 * Author: Vincent Erich <vincent.erich@live.nl>
 * Version: October, 2015
 */

package com.example.vincent.ghost;

/*
 * Necessary import statements.
 */
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;


public class Lexicon {

    /*
     * Properties of the class. 'baseLexicon' is the base lexicon and 'filteredLexicon' is the
     * lexicon used for filtering. The base lexicon remains unchanged so that it can be re-used.
     */
    private HashSet<String> baseLexicon;
    private HashSet<String> filteredLexicon;

    /*
     * Constructor of the class.
     */
    public Lexicon(Context context, String language) {
        baseLexicon = new HashSet<>();
        filteredLexicon = new HashSet<>();
        fillBaseLexicon(context, language);
        filteredLexicon.addAll(baseLexicon);    // Adds all the elements (i.e. words) in 'baseLexicon'
                                                // to 'filteredLexicon'.
    }

    /*
     * Fills the base lexicon (i.e., the HashSet) with words from either 'dutch.txt' or
     * 'english.txt' (these files can be found in the 'assets' folder). Which text file is used,
     * depends on the value of the string argument ('language').
     */
    private void fillBaseLexicon(Context context, String language) {
        try {
            InputStream inputStream;
            AssetManager assets = context.getResources().getAssets();
            if(language.equals(Settings.dutchLanguage)) {
                inputStream = assets.open("dutch.txt");
            }
            else {
                inputStream = assets.open("english.txt");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                baseLexicon.add(line);
            }
            reader.close();
        }
        catch (IOException e) {
            Toast.makeText(context, R.string.lexicon_text_retrieval_error, Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Filters the lexicon (i.e., 'filteredLexicon') using a string as input. A second HashSet is
     * used ('resultLexicon') to prevent a 'ConcurrentModificationException' to be thrown.
     */
    public void filter (String input) {
        HashSet<String> resultLexicon = new HashSet<>();
        Iterator<String> iterator = filteredLexicon.iterator();
        String word;
        while(iterator.hasNext()) {
            word = iterator.next();
            if(word.startsWith(input)) {
                resultLexicon.add(word);
            }
        }
        filteredLexicon = resultLexicon;
    }

    /*
     * Returns the number of words in 'filteredLexicon' (i.e., the number of words in the filtered
     * word list).
     */
    public int count() {
        return filteredLexicon.size();
    }


    /*
     * Removes all the elements (i.e., words) from 'filteredLexicon' and fills it with all the
     * elements in 'baseLexicon'.
     */
    public void reset() {
        filteredLexicon.clear();
        filteredLexicon.addAll(baseLexicon);
    }

    /*
     * Returns the property 'filteredLexicon' (i.e., the filtered word list).
     */
    public HashSet<String> getFilteredLexicon() {
        return filteredLexicon;
    }
}