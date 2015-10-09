package com.example.vincent.ghost;

public class Game {

    // Properties of the class...
    private Lexicon lexicon;
    private String lettersPlayer1, lettersPlayer2;
    private int playerTurn;
    private String wordFormed;

    public Game(Lexicon lexicon) {
        this.lexicon = lexicon;
        lettersPlayer1 = ".....";
        lettersPlayer2 = ".....";
        playerTurn = 1;
        wordFormed = "";
    }

    public Game(Lexicon lexicon, String lettersPlayer1, String lettersPlayer2,
                int playerTurn, String wordFormed) {
        this.lexicon = lexicon;
        this.lettersPlayer1 = lettersPlayer1;
        this.lettersPlayer2 = lettersPlayer2;
        this.playerTurn = playerTurn;
        this.wordFormed = wordFormed;
    }

    public int turn() {
        return playerTurn;
    }

    public void setWordFormed(String word) {
        wordFormed = word;
    }

    public void guess(String input) {
        lexicon.filter(input);
    }

    // A new round starts when:
    // 1: No valid word can be formed, or
    // 2: A word longer than 3 letters has been formed, or
    // (3: The only possible word has been formed (can be omitted if 2 is used)).
    public boolean endRound() {
        return ((lexicon.count() == 0) ||
                (wordFormed.length() > 3 && lexicon.getFilteredLexicon().contains(wordFormed)));
//                || (lexicon.count() == 1 && wordFormed.equals(lexicon.result())));
    }

    public void setLettersPlayer1() {
        switch(lettersPlayer1) {
            case ".....":
                lettersPlayer1 = "G....";
                break;
            case "G....":
                lettersPlayer1 = "GH...";
                break;
            case "GH...":
                lettersPlayer1 = "GHO..";
                break;
            case "GHO..":
                lettersPlayer1 = "GHOS.";
                break;
            case "GHOS.":
                lettersPlayer1 = "GHOST";
        }
    }

    public void setLettersPlayer2() {
        switch(lettersPlayer2) {
            case ".....":
                lettersPlayer2 = "G....";
                break;
            case "G....":
                lettersPlayer2 = "GH...";
                break;
            case "GH...":
                lettersPlayer2 = "GHO..";
                break;
            case "GHO..":
                lettersPlayer2 = "GHOS.";
                break;
            case "GHOS.":
                lettersPlayer2 = "GHOST";
        }
    }


    public String getLettersPlayer1() {
        return lettersPlayer1;
    }

    public String getLettersPlayer2() {
        return lettersPlayer2;
    }

    public void startNewRound() {
        lexicon.reset();
        wordFormed = "";
    }

    public boolean ended() {
        return (lettersPlayer1.equals("GHOST") || lettersPlayer2.equals("GHOST"));
    }

    public int winner() {
        if(lettersPlayer1.equals("GHOST")) {
            return 2;
        }
        else {
            return 1;
        }
    }

    public void changeTurn() {
        if(playerTurn == 1) {
            playerTurn = 2;
        }
        else {
            playerTurn = 1;
        }
    }
}
