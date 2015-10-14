/*
 * Player.java
 *
 * A 'helper' class that represents a single player. A player has a name, a score, and a ranking.
 *
 * Author: Vincent Erich
 * Version: October, 2015
 */

package com.example.vincent.ghost;

public class Player {

    /*
     * Properties of the class.
     */
    private String name;
    private int score, ranking;

    /*
     * Constructor of the class.
     */
    public Player(String name, int score, int ranking) {
        this.name = name;
        this.score = score;
        this.ranking = ranking;
    }

    /*
     * Returns the property 'name' (i.e., the name of the player).
     */
    public String getName() {
        return name;
    }

    /*
     * Returns the property 'score' (i.e., the score of the player).
     */
    public int getScore() {
        return score;
    }

    /*
     * Returns the property 'ranking' (i.e., the ranking of the player).
     */
    public int getRanking() {
        return ranking;
    }
}