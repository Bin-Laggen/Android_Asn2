package com.example.domis.assignment2;

import java.util.Comparator;

public class GameResult {

    private String userName;
    private int score;

    public GameResult()
    {

    }

    public GameResult(String userName, int score)
    {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString()
    {
        return userName + ": " + score;
    }

    public static Comparator<GameResult> scoreComparator = new Comparator<GameResult>()
    {
        @Override
        public int compare(GameResult o1, GameResult o2) {
            if (o1.getScore() > o2.getScore()) {
                return -1;
            } else if (o1.getScore() < o2.getScore()) {
                return 1;
            } else {
                return 0;
            }
        }
    };
}
