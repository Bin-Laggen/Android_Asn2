package com.example.domis.assignment2;

import java.util.Date;

public class GameResult {

    private String userName;
    private Date date;
    private int score;

    public GameResult(String userName, Date date, int score)
    {
        this.userName = userName;
        this.date = date;
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString()
    {
        return userName + ": " + score + " at: " + date.toString();
    }
}
