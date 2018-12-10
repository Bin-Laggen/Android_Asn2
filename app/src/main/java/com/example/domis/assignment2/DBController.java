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

    private DBController()
    {
        dbRef = FirebaseDatabase.getInstance().getReference();
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
        dbRef.child("BOOKINGS").push().setValue(res, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Log.e("Push: ", "Complete");
                    }
                }
        );
    }

    public ArrayList<GameResult> getScores()
    {
        final ArrayList<GameResult> scores = new ArrayList<GameResult>();
        final DatabaseReference scoresRef = dbRef.child("SCORES");
        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    GameResult res = ds.getValue(GameResult.class);
                    scores.add(res);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return scores;
    }
}
