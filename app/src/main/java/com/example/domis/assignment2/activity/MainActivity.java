package com.example.domis.assignment2.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.domis.assignment2.model.GameResult;
import com.example.domis.assignment2.controller.OnSwipeTouchListener;
import com.example.domis.assignment2.R;
import com.example.domis.assignment2.model.ScoreList;

public class MainActivity extends AppCompatActivity {

    private Button rightButton;
    private Button leftButton;
    private EditText userNameInput;
    private Drawable inputBG;
    //private DBController db;
    private ScoreList sl;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db = DBController.getInstance();
        sl = ScoreList.getInstance();
        sl.add(new GameResult("User", 25f));
        sl.add(new GameResult("User 2", 15f));
        sl.add(new GameResult("User 3", 3.7f));
        sl.add(new GameResult("jsafkldskjahflkdjasjhjsad", 150f));
        sl.add(new GameResult("j", 1f));

        //rightButton = findViewById(R.id.rightButton);
        //leftButton = findViewById(R.id.leftButton);
        userNameInput = findViewById(R.id.userEditText);
        inputBG = userNameInput.getBackground();
        cl = findViewById(R.id.cl);

        cl.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this)
        {
            @Override
            public void onSwipeRight() {String userName = userNameInput.getText().toString();
                Log.e("User Name: ", userName);
                if(!userName.isEmpty())
                {
                    userNameInput.setBackground(inputBG);
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("User", userName);
                    startActivity(intent);
                }
                else
                {
                    userNameInput.setBackgroundColor(Color.RED);
                }
            }

            /**
             * On swipe left.
             */
            @Override
            public void onSwipeLeft() {
                startActivity(new Intent(MainActivity.this, ScoresActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.settings:
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return(true);
        case R.id.exit:
            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
