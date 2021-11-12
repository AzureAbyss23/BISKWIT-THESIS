package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Years;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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

public class YearsAct extends AppCompatActivity {

    Button ch1,ch3;
    String[][] choice = {{"Bagong Taon","Pasko"},{"Araw ng mga Puso","Buwan ng Pagtatapos"},
            {"Flores de Mayo","Buwan ng Pagtatapos"},{"Buwan ng Wika","Bakasyon"},
            {"Flores de Mayo","Pasko"},{"Araw ng Kalayaan","Araw ng mga Patay"},
            {"Bagong Taon","Buwan ng Nutrisyon"},{"Buwan ng Wika","Araw ng mga Puso"},
            {"Buwan ng Palakasan","Buwan ng Pagtatapos"},{"Araw ng mg Patay","Mga Nagkakaisang Bansa"},
            {"Flores de Mayo","Araw ng mga Patay"},{"Bagong Taon","Pasko"}};
    String[] title;
    String[] data;
    int all_ctr = 0, id = 0;
    int status = 0;
    double score = 0;
    ImageView bot2,wordimg;
    MediaPlayer ai;

    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years2);

        score = getIntent().getIntExtra("Score",0);
        title = getIntent().getStringArrayExtra("Title");
        data = getIntent().getStringArrayExtra("data");
        status = getIntent().getIntExtra("Status",0);

        ch1 = findViewById(R.id.Choice1);
        ch3 = findViewById(R.id.Choice3);
        bot2 = findViewById(R.id.Bot2);
        wordimg = findViewById(R.id.WordImg);

        ch1.setText(choice[all_ctr][0]);
        ch3.setText(choice[all_ctr][1]);
        id = setImg();
        wordimg.setImageResource(id);

        ai = MediaPlayer.create(YearsAct.this, R.raw.kab5_p2_2);
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
                stopPlaying();
                String day = data[all_ctr];
                String event = ch1.getText().toString();
                result(day,event);
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                String day = data[all_ctr];
                String event = ch3.getText().toString();
                result(day,event);
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

    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(ai!=null){
            ai.stop();
            ai.release();
            ai = null;
        }
    }

    public int setImg(){
        Resources res = this.getResources();
        int resID;
        return resID = res.getIdentifier("years_"+title[all_ctr].replace(" ","").toLowerCase(), "drawable", this.getPackageName());
    }

    public void result(String s,String z){
        switch(s.toLowerCase()){
            case "enero":
                if(z=="Bagong Taon"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "pebrero":
                if(z=="Araw ng mga Puso"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "marso":
                if(z=="Buwan ng Pagtatapos"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "abril":
                if(z=="Bakasyon"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mayo":
                if(z=="Flores de Mayo"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "hunyo":
                if(z=="Araw ng Kalayaan"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "hulyo":
                if(z=="Buwan ng Nutrisyon"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "agosto":
                if(z=="Buwan ng Wika"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "setyembre":
                if(z=="Buwan ng Palakasan"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "oktubre":
                if(z=="Mga Nagkakaisang Bansa"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "nobyembre":
                if(z=="Araw ng mga Patay"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "disyembre":
                if(z=="Pasko"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
        }
        if(all_ctr < (data.length - 1)) {
            ++all_ctr;
            CurrentProgress = CurrentProgress + 1;
            progressBar.setProgress(CurrentProgress);
            id = setImg();
            wordimg.setImageResource(id);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        } else {
            Intent intent = new Intent(YearsAct.this, Score.class);
            intent.putExtra("Average",data.length*2);
            intent.putExtra("Status",status);
            intent.putExtra("LessonType","Alamkoito");
            intent.putExtra("LessonMode","Years");
            intent.putExtra("Score", score);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You will not be able to save your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        YearsAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}