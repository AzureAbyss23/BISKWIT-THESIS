package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Years;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class YearsAct extends AppCompatActivity {

    Button ch1,ch2,ch3;
    String[] data = {"enero","pebrero","marso","abril","mayo","hunyo","hulyo","agosto","setyembre","oktubre","nobyembre","disyembre"};
    String[][] choice = {{"1","3","4"},{"5","2","7"},{"3","2","7"},{"5","2","4"},{"5","2","7"},{"6","2","7"},{"5","2","7"}};
    int all_ctr = 0, score = 0;
    TextView word;
    ImageView bot2;
    MediaPlayer ai;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years2);

        score = getIntent().getIntExtra("Score",0);

        ch1 = findViewById(R.id.Choice1);
        ch2 = findViewById(R.id.Choice2);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);

        word.setText(data[all_ctr]);
        ch1.setText(choice[all_ctr][0]);
        ch2.setText(choice[all_ctr][1]);
        ch3.setText(choice[all_ctr][2]);

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = word.getText().toString();
                int num = Integer.parseInt(ch1.getText().toString());
                result(day,num);
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = word.getText().toString();
                int num = Integer.parseInt(ch2.getText().toString());
                result(day,num);
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = word.getText().toString();
                int num = Integer.parseInt(ch3.getText().toString());
                result(day,num);
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(YearsAct.this, R.raw.kab5_p1_1);
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

    public void result(String s,int num){
        switch(s){
            case "enero":
                if(num==1){
                    score += 2;
                }
                break;
            case "pebrero":
                if(num==2){
                    score += 2;
                }
                break;
            case "marso":
                if(num==3){
                    score += 2;
                }
                break;
            case "abril":
                if(num==4){
                    score += 2;
                }
                break;
            case "mayo":
                if(num==5){
                    score += 2;
                }
                break;
            case "hunyo":
                if(num==6){
                    score += 2;
                }
                break;
            case "hulyo":
                if(num==7){
                    score += 2;
                }
                break;
            case "agosto":
                if(num==8){
                    score += 2;
                }
                break;
            case "setyembre":
                if(num==9){
                    score += 2;
                }
                break;
            case "oktubre":
                if(num==10){
                    score += 2;
                }
                break;
            case "nobyembre":
                if(num==11){
                    score += 2;
                }
                break;
            case "disyembre":
                if(num==12){
                    score += 2;
                }
                break;
        }
        if(all_ctr < 11) {
            ++all_ctr;
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch2.setText(choice[all_ctr][1]);
            ch3.setText(choice[all_ctr][2]);
        } else {
            Intent intent = new Intent(YearsAct.this, Score.class);
            intent.putExtra("Score",score);
            startActivity(intent);
        }
    }
}