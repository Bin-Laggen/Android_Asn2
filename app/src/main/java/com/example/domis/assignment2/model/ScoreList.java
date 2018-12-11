package com.example.domis.assignment2.model;

import java.util.ArrayList;

public class ScoreList {

    private ArrayList<GameResult> scores;
    private int difficulty;
    private String color;

    public static ScoreList instance = null;

    private ScoreList()
    {
        scores = new ArrayList<>();
        difficulty = 1;
        color = "BLUE";
    }

    public static ScoreList getInstance()
    {
        if(instance == null)
        {
            instance = new ScoreList();
        }
        return instance;
    }

    public void add(GameResult res)
    {
        scores.add(res);
    }

    public ArrayList<GameResult> getList()
    {
        return scores;
    }

    public float getHighestScore()
    {
        float res = 0;
        for(GameResult g : scores)
        {
            if(g.getScore() > res)
            {
                res = g.getScore();
            }
        }
        return res;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    public int getDifficulty()
    {
        return difficulty;
    }
}
