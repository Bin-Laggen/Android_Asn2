package com.example.domis.assignment2.controller;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import com.example.domis.assignment2.model.GameResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        readScoreCount(new MyCallback() {
            @Override
            public void onCallback(Object value) {
                size = (long) value;
                //Log.e("Res: ", value.toString());
            }
        });
        Log.e("Size: ", size + "");
    }

    public void getScoresFromDB()
    {
        getScoreCount();
        Log.e("Calling ", "DB");
        scores = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            /**
            dbRef.child("SCORES").child(size + "").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GameResult res = dataSnapshot.getValue(GameResult.class);
                    Log.e("Res: ", res.toString());
                    scores.add(res);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
             */

         /**
        scores = new ArrayList<GameResult>();
          */
            readGameResult(new MyCallback() {
                @Override
                public void onCallback(Object value) {
                    scores.add((GameResult) value);
                //Log.e("Res: ", value.toString());
                }
            });
        }
        Log.e("Returned from ", "DB");
    }

    public ArrayList<GameResult> getScores()
    {
        for(GameResult g : scores)
        {
            Log.e("GR: ", g.toString());
        }
        return scores;
    }


    private void readGameResult(final MyCallback myCallback) {
        final DatabaseReference scoresRef = dbRef.child("SCORES");
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    GameResult res = ds.getValue(GameResult.class);
                    myCallback.onCallback(res);
                }
                 */
                GameResult res = dataSnapshot.getValue(GameResult.class);
                myCallback.onCallback(res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readScoreCount(final MyCallback myCallback) {
        final DatabaseReference scoresRef = dbRef.child("SCORES");
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                 for(DataSnapshot ds : dataSnapshot.getChildren())
                 {
                 GameResult res = ds.getValue(GameResult.class);
                 myCallback.onCallback(res);
                 }
                 */
                //GameResult res = dataSnapshot.getValue(GameResult.class);
                long count = dataSnapshot.getChildrenCount();
                Log.e("Count", count + "");
                myCallback.onCallback(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
