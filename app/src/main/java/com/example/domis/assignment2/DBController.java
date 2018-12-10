package com.example.domis.assignment2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class DBController {

    public static DBController instance = null;

    private DatabaseReference dbRef;

    private ArrayList<GameResult> scores;
    private long size;

    private DBController()
    {
        dbRef = FirebaseDatabase.getInstance().getReference();
        getScoresFromDB();
    }

    public static DBController getInstance()
    {
        if(instance == null)
        {
            instance = new DBController();
        }
        return instance;
    }

    public void writeScoreToDB(GameResult res)
    {
        Log.e("Pushing: ", res.toString());
        getScoreCount();
        Log.e("Key", size + "");
        Log.e("DBRef", dbRef.toString());
        dbRef.child("SCORES").child(size + "").setValue(res);
        Log.e("Pushed? ", "True");
    }

    private void getScoreCount()
    {
        dbRef.child("SCORES").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getScoresFromDB()
    {
        Log.e("Calling ", "DB");
        scores = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            dbRef.child("SCORES").child(size + "").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    scores.add(dataSnapshot.getValue(GameResult.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
         /**
        scores = new ArrayList<GameResult>();
        readData(new MyCallback() {
            @Override
            public void onCallback(GameResult value) {
                scores.add(value);
                //Log.e("Res: ", value.toString());
            }
        });
         */
        Log.e("Returned from ", "DB");
    }

    public ArrayList<GameResult> getScores()
    {
        //getScoresFromDB();
        for(GameResult g : scores)
        {
            Log.e("GR: ", g.toString());
        }
        return scores;
    }

    /**
    private void readData(final MyCallback myCallback) {
        final DatabaseReference scoresRef = dbRef.child("SCORES");
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    GameResult res = ds.getValue(GameResult.class);
                    myCallback.onCallback(res);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
     */
}
