package com.example.vincent.ghost;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public class Lexicon {

    // Properties of the class...
    public HashSet<String> baseLexicon;
    public HashSet<String> filteredLexicon;

    public Lexicon(Context context) {
        baseLexicon = new HashSet<String>();
        filteredLexicon = new HashSet<String>();
        createBaseLexicon(context);
        filteredLexicon.addAll(baseLexicon);
    }

    // Methods of the class...

    private void createBaseLexicon(Context context) {
        try {
            InputStream inputStream = context.getResources().getAssets().open("lexicon.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                baseLexicon.add(line);
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("IOException...");
        }
    }

    public void filter (String input) {
        HashSet<String> iteratorHashSet = new HashSet<String>();
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
        return filteredLexicon.size();
    }

    public String result() {
        String lastWord = "";
        if(count() == 1) {
            lastWord = filteredLexicon.iterator().next();
        }
        return lastWord;
    }

    public void reset() {
        filteredLexicon.clear();
        filteredLexicon.addAll(baseLexicon);
    }
}
