package com.example.domis.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    private static FileManager instance = null;
    private File file;
    private Scanner in;
    private PrintWriter out;

    private FileManager()
    {
        file = new File("scores.txt");
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            in = new Scanner(file);
            out = new PrintWriter(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static FileManager getInstance()
    {
        if(instance == null)
        {
            instance = new FileManager();
        }
        return instance;
    }

    public ArrayList<GameResult> readScores()
    {
        ArrayList<GameResult> scores = new ArrayList<>();
        while (in.hasNext())
        {
            String user = in.nextLine();
            int score = Integer.parseInt(in.nextLine());
            scores.add(new GameResult(user, score));
        }
        return scores;
    }

    public void writeToFile(GameResult res)
    {
        out.println(res.getUserName());
        out.println(res.getScore());
    }
}
