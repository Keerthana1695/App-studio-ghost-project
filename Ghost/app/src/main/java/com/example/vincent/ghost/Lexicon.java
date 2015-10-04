package com.example.vincent.ghost;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public class Lexicon {

    // Properties of the class...
    private HashSet<String> baseLexicon;
    private HashSet<String> filteredLexicon;

    public Lexicon(Context context, String language) {
        baseLexicon = new HashSet<>();
        filteredLexicon = new HashSet<>();
        createBaseLexicon(context, language);
        filteredLexicon.addAll(baseLexicon);
    }

    // Methods of the class...

    private void createBaseLexicon(Context context, String language) {
        // Create the base lexicon. Use the String argument to read the right text file.
        try {
            InputStream inputStream;
            if(language.equals(Settings.dutchLanguage)) {
                inputStream = context.getResources().getAssets().open("dutch.txt");
            }
            else {
                inputStream = context.getResources().getAssets().open("english.txt");
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

    public void filter (String input) {
        // Filter the word list using the String argument. Because loading the base lexicon takes
        // quite a bit of time, this method does not destroy the base lexicon and thus allows it to
        // be re-used.
        // Use an 'iteratorHashSet' to prevent a 'ConcurrentModificationException' to be thrown.
        HashSet<String> iteratorHashSet = new HashSet<>();
        iteratorHashSet.addAll(filteredLexicon);
        Iterator<String> iterator = iteratorHashSet.iterator();
        String word;
        while(iterator.hasNext()) {
            word = iterator.next();
            if(!word.startsWith(input)) {
                filteredLexicon.remove(word);
            }
        }
    }

    public int count() {
        // Return the number of words in the filtered list.
        return filteredLexicon.size();
    }

    public String result() {
        // Return the single remaining word in the filtered list. If the filtered list contains
        // more than one word, return null.
        if(count() == 1) {
            return filteredLexicon.iterator().next();
        }
        return null;
    }

    public void reset() {
        // Remove the filter and re-start with the original (base) lexicon.
        filteredLexicon.clear();
        filteredLexicon.addAll(baseLexicon);
    }

    public HashSet<String> getFilteredLexicon() {
        return filteredLexicon;
    }
}
