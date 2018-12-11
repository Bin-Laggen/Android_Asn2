package com.example.domis.assignment2.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.domis.assignment2.model.GameResult;
import com.example.domis.assignment2.R;
import com.example.domis.assignment2.model.ScoreList;
import com.example.domis.assignment2.view.BarChart;

import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    //private DBController db;
    //private FileManager fm;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private ScoreList sl;
    private ArrayList<GameResult> scores;
    private TextView userText;
    private TextView scoreText;
    private CheckBox checkButton;
    private RelativeLayout rl;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        rl = findViewById(R.id.rl);

        //db = DBController.getInstance();
        //fm = FileManager.getInstance();
        sl = ScoreList.getInstance();

        userText = findViewById(R.id.userList);
        scoreText = findViewById(R.id.scoreList);
        checkButton = findViewById(R.id.checkBox);
        barChart = findViewById(R.id.barChart);

        timerHandler = new Handler();
        timerRunnable = new Runnable() {

            @Override
            public void run()
            {
                drawScoreList();
                timerHandler.postDelayed(this, 1000);
            }
        };

        timerHandler.postDelayed(timerRunnable, 0);

        /**
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawScoreList();
            }
        });
         */
        drawScoreList();
    }

    private void drawScoreList()
    {
        //db.getScoresFromDB();
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
        //scores = db.getScores();
        //scores = fm.readScores();
        scores = sl.getList();

        String userList = "USER\n";
        String scoreList = "SCORE\n";

        if(!scores.isEmpty()) {
            scores.sort(GameResult.scoreComparator);
            float highest = sl.getHighestScore();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x / 2;

            float widthScoreRatio = ((width - 90) - (size.x / 10)) / highest;
            Log.e("Ratio: ", widthScoreRatio + "");

            float[] data = new float[scores.size()];

            for (int i = 0; i < scores.size(); i++)
            {
                Log.e("Result: ", scores.get(i).toString());
                userList += scores.get(i).getUserName() + "\n";
                scoreList += scores.get(i).getScore() + "\n";
                data[i] = scores.get(i).getScore() * widthScoreRatio;
            }

            if(checkButton.isChecked())
            {
                barChart.setData(data);
            }
            else
            {
                barChart.setData(null);
            }
            userText.setWidth(width + 100);
            scoreText.setWidth(width - 100);
        }

        userText.setText(userList);

        scoreText.setTextColor(Color.RED);
        scoreText.setText(scoreList);
    }

    @Override
    protected void onPause() {
        timerHandler.removeCallbacks(timerRunnable);
        super.onPause();
    }

    @Override
    protected void onResume() {
        timerHandler.postDelayed(timerRunnable, 0);
        super.onResume();
    }
}
