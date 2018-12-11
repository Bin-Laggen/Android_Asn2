package com.example.domis.assignment2.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.domis.assignment2.model.GameResult;
import com.example.domis.assignment2.R;
import com.example.domis.assignment2.model.ScoreList;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView scoreText;
    private Button startButton;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private float score;
    private ImageButton balloonButton;
    private Random rnd;
    private RelativeLayout rl;
    private boolean play;
    //private DBController db;
    //private FileManager fm;
    private ScoreList sl;
    private GameResult gameResult;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent in = getIntent();
        userName = in.getStringExtra("User");

        rl = findViewById(R.id.relativeLayout);

        //db = DBController.getInstance();
        //fm = FileManager.getInstance();
        sl = ScoreList.getInstance();
        rnd = new Random();

        balloonButton = findViewById(R.id.balloonButton);

        scoreText = findViewById(R.id.scoreText);
        startButton = findViewById(R.id.startButton);

        setBalloon();
        setDiff();

        timerHandler = new Handler();
        timerRunnable = new Runnable() {

            @Override
            public void run()
            {
                long delay = 1000;
                if(play) {
                    play = false;
                    Log.e("Score: ", "" + score);
                    int x = 50 + rnd.nextInt(rl.getWidth() - 100);
                    Log.e("Width: ", x + "");
                    int y = 50 + rnd.nextInt(rl.getHeight() - 100);
                    Log.e("Height: ", y + "");
                    balloonButton.setX(x);
                    balloonButton.setY(y);
                    if(score <= 10) { score++; }
                    else if(score > 10 && score <= 20) { score += 2; delay *= 0.9; }
                    else if(score > 20 && score <= 30) { score += 3; delay *= 0.8; }
                    else if(score > 30 && score <= 50) { score += 4; delay *= 0.75; }
                    else if(score > 50 && score <= 70) { score += 5; delay *= 0.7; }
                    else if(score > 70 && score <= 100) { score += 6; delay *= 0.67; }
                    else if(score > 100 && score <= 130) { score += 7; delay *= 0.63; }
                    else if(score > 130 && score <= 170) { score += 8; delay *= 0.6; }
                    else if(score > 170 && score <= 220) { score += 9; delay *= 0.58; }
                    else if(score > 220 && score <= 300) { score += 10; delay *= 0.56; }
                    else { score += 11; delay *= 0.55; }
                    scoreText.setText(getString(R.string.score) + ": " + score);
                    timerHandler.postDelayed(this, delay);
                }
                else
                {
                    score *= (1 + (0.2 * sl.getDifficulty()));
                    gameResult = new GameResult(userName, score);
                    timerHandler.removeCallbacks(timerRunnable);
                    sl.add(gameResult);
                    showLossPopup();
                }
            }
        };

        balloonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play = true;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.GONE);
                startButton.setActivated(false);
                play = true;
                score = -1;
                timerHandler.postDelayed(timerRunnable, 0);
            }
        });
    }

    private void showLossPopup() {
        final AlertDialog ad = new AlertDialog.Builder(GameActivity.this).create();
        ad.setMessage("Game Over, Score: " + score);
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Menu",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //db.writeScoreToDB(gameResult);
                        //db.getScoresFromDB();
                        //fm.writeToFile(gameResult);
                        finish();
                    }
                });
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "Play Again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //db.writeScoreToDB(gameResult);
                        //db.getScoresFromDB();
                        //fm.writeToFile(gameResult);
                        startButton.setVisibility(View.VISIBLE);
                        startButton.setActivated(true);
                        ad.cancel(); //hide the alert dialog
                    }
                });
        ad.setCancelable(false);
        ad.show();
    }

    private void setBalloon() {
        if(sl.getColor().equals("GREEN"))
        {
            balloonButton.setImageResource(R.drawable.green_balloon);
        }
        else if(sl.getColor().equals("RED"))
        {
            balloonButton.setImageResource(R.drawable.red_balloon);
        }
        else
        {
            balloonButton.setImageResource(R.drawable.blue_balloon);
        }
    }

    private void setDiff() {
        Log.e("Diff: ", sl.getDifficulty() + "");
        ViewGroup.LayoutParams balloonParams = balloonButton.getLayoutParams();
        switch (sl.getDifficulty())
        {
            case 0:
                balloonParams.width *= 1.5;
                balloonParams.height *= 1.5;
                break;
            case 1:
                balloonParams.width *= 1;
                balloonParams.height *= 1;
                break;
            case 2:
                balloonParams.width *= 0.75;
                balloonParams.height *= 0.75;
                break;
            default:
                balloonParams.width *= 1.5;
                balloonParams.height *= 1.5;
        }
        balloonButton.setLayoutParams(balloonParams);
        balloonButton.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void onPause()
    {
        timerHandler.removeCallbacks(timerRunnable);
        super.onPause();
    }

    @Override
    public void onResume()
    {
        setBalloon();
        setDiff();
        super.onResume();
    }

}
