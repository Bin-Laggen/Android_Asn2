package com.example.domis.assignment2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button rightButton;
    private Button leftButton;
    private EditText userNameInput;
    private Drawable inputBG;
    //private DBController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db = DBController.getInstance();

        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);
        userNameInput = findViewById(R.id.userEditText);
        inputBG = userNameInput.getBackground();

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameInput.getText().toString();
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
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScoresActivity.class));
            }
        });
    }
}
