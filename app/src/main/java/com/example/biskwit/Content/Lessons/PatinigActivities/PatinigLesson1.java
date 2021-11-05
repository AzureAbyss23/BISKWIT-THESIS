package com.example.biskwit.Content.Lessons.PatinigActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.biskwit.Content.Lessons.KatinigActivities.KatinigLesson2;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class PatinigLesson1 extends AppCompatActivity {

    ImageButton back;
    ImageView bot2;
    MediaPlayer ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patinig_lesson1);

        back = findViewById(R.id.backbutton);
        bot2 = findViewById(R.id.Bot2);

        ai = MediaPlayer.create(PatinigLesson1.this, R.raw.kab3_p1);
        ai.start();

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(PatinigLesson1.this, R.raw.kab3_p1);
                ai.start();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Intent intent = new Intent(PatinigLesson1.this, Score.class);
                intent.putExtra("Average",100);
                intent.putExtra("LessonType","Patinig");
                intent.putExtra("LessonMode","Aralin1");
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
