package com.example.domis.assignment2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView scoreText;
    private Button startButton;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int score;
    private ImageButton balloonButton;
    private Random rnd;
    private RelativeLayout rl;
    private boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rl = findViewById(R.id.relativeLayout);

        rnd = new Random();

        scoreText = findViewById(R.id.scoreText);
        startButton = findViewById(R.id.startButton);

        balloonButton = findViewById(R.id.balloonButton);

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
                    if(score <= 10) {
                        score++;
                    }
                    else if(score > 10 && score <= 20)
                    {
                        score += 2;
                        delay *= 0.8;
                    }
                    else if(score > 20 && score <= 30)
                    {
                        score += 3;
                        delay *= 0.7;
                    }
                    else if(score > 30 && score <= 40)
                    {
                        score += 4;
                        delay *= 0.6;
                    }
                    else
                    {
                        score += 5;
                        delay *= 0.5;
                    }
                    scoreText.setText(getString(R.string.score) + ": " + score);
                    timerHandler.postDelayed(this, delay);
                }
                else
                {
                    timerHandler.removeCallbacks(timerRunnable);
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
        ad.setMessage("You lost, score: " + score);
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeScoreToFile();
                        startButton.setVisibility(View.VISIBLE);
                        startButton.setActivated(true);
                    }
                });
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        writeScoreToFile();
                        play = true;
                        score = -1;
                        timerHandler.postDelayed(timerRunnable, 1000);
                        ad.cancel(); //hide the alert dialog
                    }
                });
        ad.show();
    }

    private void writeScoreToFile() {

    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

}
