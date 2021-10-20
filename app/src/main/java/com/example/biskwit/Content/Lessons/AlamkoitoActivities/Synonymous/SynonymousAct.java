package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Synonymous;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class SynonymousAct extends AppCompatActivity {

    Button ch1,ch2,ch3;
    String[] data = {"masaya","maganda","masarap","mabagal","maingay","mayaman","malungkot","mababa","mataas","mabango"};
    String[][] choice = {{"so","pa","te"},{"nak","kay","raw"},{"lis","kis","dis"},
            {"llisi","nergy","nsayo"},{"saw","law","sda"},{"saw","law","sda"},
            {"po","lo","so"},{"po","lo","so"},{"lo","od","so"},{"sa","bo","lo"}};
    int all_ctr = 0, score = 0;
    TextView word;
    ImageView bot2;
    MediaPlayer ai;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blending);

        ch1 = findViewById(R.id.Choice1);
        ch2 = findViewById(R.id.Choice2);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);

        word.setText(data[all_ctr].charAt(0)+" _ _");
        ch1.setText(choice[all_ctr][0]);
        ch2.setText(choice[all_ctr][1]);
        ch3.setText(choice[all_ctr][2]);

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char ltr = word.getText().toString().charAt(0);
                String wrd = ch1.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char ltr = word.getText().toString().charAt(0);
                String wrd = ch2.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char ltr = word.getText().toString().charAt(0);
                String wrd = ch3.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(SynonymousAct.this, R.raw.kab2_p3);
                ai.start();
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

    public void result(String s){
        if(s.equals(data[all_ctr])){
            Toast toast = Toast.makeText(this, "Correct!", Toast.LENGTH_LONG);
            toast.show();
            score += 2;
        }
        else{
            Toast toast = Toast.makeText(this, "Wrong!", Toast.LENGTH_LONG);
            toast.show();
        }

        if(all_ctr < 9) {
            ++all_ctr;
            word.setText(data[all_ctr].charAt(0) + " _ _");
            ch1.setText(choice[all_ctr][0]);
            ch2.setText(choice[all_ctr][1]);
            ch3.setText(choice[all_ctr][2]);
        } else {
            Intent intent = new Intent(SynonymousAct.this, Score.class);
            intent.putExtra("Score",score);
            startActivity(intent);
        }
    }
}