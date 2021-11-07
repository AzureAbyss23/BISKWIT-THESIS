package com.example.biskwit.Content.Lessons.KatinigActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigLesson1;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class KatinigLesson1 extends AppCompatActivity {

    ImageButton back;
    ImageView bot2;
    MediaPlayer ai;

    int status = 0;
    String holder = "";

    SharedPreferences scores,logger;
    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";
    public static final String UserScore = "userscore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katinig_lesson1);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        if(scores.contains(UserScore)) {
            holder = scores.getString(UserScore, null);
            if (holder.equals("K_Aralin1" + id)) {
                new AlertDialog.Builder(this)
                        .setTitle("Retry lesson?")
                        .setMessage("Your previous progress will be reset.")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                stopPlaying();
                                finish();
                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                status = 1;
                            }
                        }).create().show();
            }
        }

        back = findViewById(R.id.backbutton);
        bot2 = findViewById(R.id.Bot2);

        ai = MediaPlayer.create(KatinigLesson1.this, R.raw.kab4_p1_1);
        ai.start();

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(KatinigLesson1.this, R.raw.kab4_p1_1);
                ai.start();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Intent intent = new Intent(KatinigLesson1.this, Score.class);
                intent.putExtra("Average",100);
                intent.putExtra("Status",status);
                intent.putExtra("LessonType","Katinig");
                intent.putExtra("LessonMode","K_Aralin1");
                intent.putExtra("Score", 100.0);
                startActivity(intent);
            }
        });

    }

    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(ai!=null){
            ai.stop();
            ai.release();
            ai = null;
        }
    }
}