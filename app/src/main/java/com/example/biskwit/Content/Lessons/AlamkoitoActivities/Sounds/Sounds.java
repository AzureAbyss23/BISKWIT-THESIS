package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Sounds;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.biskwit.R;

public class Sounds extends AppCompatActivity {

    ImageView next;
    ImageView bot2;
    MediaPlayer ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);

        next = findViewById(R.id.nextButton);
        bot2 = findViewById(R.id.Bot2);

        ai = MediaPlayer.create(Sounds.this, R.raw.kab5_p5_1);
        ai.start();

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Sounds.this, R.raw.kab5_p5_1);
                ai.start();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Intent intent = new Intent(Sounds.this, SoundsAct.class);
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
