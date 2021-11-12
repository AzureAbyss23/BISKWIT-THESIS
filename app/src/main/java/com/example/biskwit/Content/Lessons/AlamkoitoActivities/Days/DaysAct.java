package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Days;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.biskwit.Content.Score;
import com.example.biskwit.R;

public class DaysAct extends AppCompatActivity {

    Button ch1,ch2,ch3;
    String[] data;
    String[][] choice = {{"1","3","4"},{"5","2","7"},{"3","2","7"},{"7","2","4"},{"3","5","7"},{"6","1","7"},{"2","5","7"}};
    int all_ctr = 0;
    int status = 0;
    double score = 0, add = 0;
    TextView word;
    ImageView bot2;
    MediaPlayer ai;

    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days2);

        score = getIntent().getIntExtra("FScore",0);
        data = getIntent().getStringArrayExtra("data");
        status = getIntent().getIntExtra("Status",0);

        ch1 = findViewById(R.id.Choice1);
        ch2 = findViewById(R.id.Choice2);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);

        word.setText(data[all_ctr]);
        ch1.setText(choice[all_ctr][0]);
        ch2.setText(choice[all_ctr][1]);
        ch3.setText(choice[all_ctr][2]);

        ai = MediaPlayer.create(DaysAct.this, R.raw.kab5_p1_2);
        ai.start();

        progressBar = findViewById(R.id.ProgressBar); // need ito para sa progress
        CurrentProgress = data.length;
        progressBar.setMax(data.length*2);
        progressBar.setProgress(data.length);
        CurrentProgress = CurrentProgress + 1;
        progressBar.setProgress(CurrentProgress);

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
                ai = MediaPlayer.create(DaysAct.this, R.raw.kab5_p1_2);
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
        if(s.equalsIgnoreCase("linggo") && num==1){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("lunes") && num==2){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("martes") && num==3){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("miyerkules") && num==4){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("huwebes") && num==5){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("biyernes") && num==6){
            add = 1;
            showToast("TAMA!");
        } else if(s.equalsIgnoreCase("sabado") && num==7){
            add = 1;
            showToast("TAMA!");
        } else {
            showToast("MALI");
            add = 0;
        }
        if(all_ctr < (data.length - 1)) {
            ++all_ctr;
            score += add;
            add = 0;
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch2.setText(choice[all_ctr][1]);
            ch3.setText(choice[all_ctr][2]);
            CurrentProgress = CurrentProgress + 1;
            progressBar.setProgress(CurrentProgress);
        } else {
            score += add;
            Intent intent = new Intent(DaysAct.this, Score.class);
            intent.putExtra("Average",data.length*2);
            intent.putExtra("Status",status);
            intent.putExtra("LessonType","Alamkoito");
            intent.putExtra("LessonMode","Days");
            intent.putExtra("Score", score);
            startActivity(intent);
            finish();
        }
    }

    public void showToast(String s) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(s);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You will not be able to save your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        DaysAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}