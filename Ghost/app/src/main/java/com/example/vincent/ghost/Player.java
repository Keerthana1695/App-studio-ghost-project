package com.example.vincent.ghost;

public class Player {

    private String name;
    private int score, ranking;

    public Player(String name, int score, int ranking) {
        this.name = name;
        this.score = score;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getRanking() {
        return ranking;
    }
}
