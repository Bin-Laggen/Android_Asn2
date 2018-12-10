package com.example.domis.assignment2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    private DBController db;
    //private FileManager fm;
    private ArrayList<GameResult> scores;
    private TextView userText;
    private TextView scoreText;

    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        db = DBController.getInstance();
        //fm = FileManager.getInstance();

        userText = findViewById(R.id.userList);
        scoreText = findViewById(R.id.scoreList);
        refreshButton = findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawScoreList();
            }
        });
        drawScoreList();
    }

    private void drawScoreList()
    {
        db.getScoresFromDB();
        /**
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
         */
        scores = db.getScores();
        //scores = fm.readScores();

        String userList = "";
        String scoreList = "";

        scores.sort(GameResult.scoreComparator);

        for(int i = 0; i < scores.size(); i++)
        {
            Log.e("Result: ", scores.get(i).toString());
            userList += scores.get(i).getUserName() + "\n";
            scoreList += scores.get(i).getScore() + "\n";
        }

        userText.setText(userList);
        scoreText.setText(scoreList);
    }

}
