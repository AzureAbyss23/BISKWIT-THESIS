package com.example.biskwit.Content.Lessons.OrtonActivities.Phonemic;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.biskwit.R;

public class PhonemicAct extends AppCompatActivity {

    Button ch1,ch3;
    String[] data = {"Ibon","Aso","Kuto","Matsing","Pusa","Barong","Pantalon","Medyas","Kwintas",
            "Palda","Pinya","Pakwan","Abokado","Ubas","Atis","Sitaw","Kalabasa","Upo","Talong",
            "Singkamas"};
    String[][] choice = {{"Hipon","Pusit"},{"Pusa","Oso"},{"Garapata","Pato"}, {"Kambing","Kabayo"},
            {"Aso","Daga"},{"Putong","Palda"}, {"Sinturon","Blusa"},{"Sapatos","Tsinelas"},
            {"Pulseras","Hikaw"}, {"Blusa","Sinturon"},{"Mangga","Papaya"},{"Rambutan","Mansanas"},
            {"Guyabano","Langka"},{"Mansanas","Pongkan"},{"Ubas","Aratilis"},{"Bataw","Kangkong"},
            {"Mustasa","Singkamas"},{"Lettuce","Repolyo"},{"Kangkong","Kamatis"},{"Sigarilyas","Kalabasa"}};
    int all_ctr = 0;
    int status = 0;
    double score = 0;
    TextView word,scorectr;
    ImageView bot2;
    MediaPlayer ai;

    SharedPreferences scores,logger;
    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";

    private int CurrentProgress = 1;
    private ProgressBar progressBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemic2);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        final String UserScore = "userscore"+id+"Phonemic";
        if(scores.contains(UserScore)) {
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

        ai = MediaPlayer.create(PhonemicAct.this, R.raw.kab2_p1_1);
        ai.start();

        ch1 = findViewById(R.id.Choice1);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);
        scorectr = findViewById(R.id.scorecounter);

        if(LoadPreferences()){
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        } else {
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        }

        progressBar = findViewById(R.id.ProgressBar);

        CurrentProgress += all_ctr;
        progressBar.setProgress(CurrentProgress);
        progressBar.setMax(data.length*2);

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tword = word.getText().toString();
                String wrd = ch1.getText().toString();
                result(Tword,wrd);
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tword = word.getText().toString();
                String wrd = ch3.getText().toString();
                result(Tword,wrd);
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(PhonemicAct.this, R.raw.kab2_p1_1);
                ai.start();
            }
        });
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("PhonemicAct",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Counter", all_ctr);
        editor.putString("Score",Double.toString(score));
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private boolean LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("PhonemicAct",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("PhonemicFin",Context.MODE_PRIVATE);
        if(sharedPreferences2.contains("Status")){
            all_ctr = sharedPreferences.getInt("Counter", 0);
            score = Double.parseDouble(sharedPreferences.getString("Score", null));
            Intent intent = new Intent(PhonemicAct.this, Phonemic.class);
            intent.putExtra("DataLength",data.length);
            intent.putExtra("Status",status);
            intent.putExtra("FScore", score);
            startActivity(intent);
            finish();
            return true;
        } else if(sharedPreferences.contains("Counter") && sharedPreferences.contains("Score")) {
            all_ctr = sharedPreferences.getInt("Counter", 0);
            score = Double.parseDouble(sharedPreferences.getString("Score", null));
            scorectr.setText("Score: " + score);
            return true;
        } else return false;
    }

    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(ai!=null){
            ai.stop();
            ai.release();
            ai = null;
        }
    }

    @SuppressLint("SetTextI18n")
    public void result(String s, String z){
        switch(s.toLowerCase()){
            case "ibon":
                if(z=="Hipon"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "aso":
                if(z=="Oso"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "kuto":
                if(z=="Pato"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "matsing":
                if(z=="Kambing"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "pusa":
                if(z=="Daga"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "barong":
                if(z=="Putong"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "pantalon":
                if(z=="Sinturon"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "medyas":
                if(z=="Tsinelas"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "kwintas":
                if(z=="Pulseras"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "palda":
                if(z=="Blusa"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "pinya":
                if(z=="Papaya"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "pakwan":
                if(z=="Rambutan"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "abokado":
                if(z=="Guyabano"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "ubas":
                if(z=="Mansanas"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "atis":
                if(z=="Aratilis"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "sitaw":
                if(z=="Bataw"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "kalabasa":
                if(z=="Mustasa"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "upo":
                if(z=="Repolyo"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "talong":
                if(z=="Kangkong"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "singkamas":
                if(z=="Sigarilyas"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
        }

        if(all_ctr < (data.length-1)) {
            ++all_ctr;
            CurrentProgress = CurrentProgress + 1;
            progressBar.setProgress(CurrentProgress);
            scorectr.setText("Score: " + score + "/" + (data.length*2));
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        } else {
            scorectr.setText("Score: " + score + "/" + (data.length*2));
            Intent intent = new Intent(PhonemicAct.this, Phonemic.class);
            intent.putExtra("DataLength",data.length);
            intent.putExtra("Status",status);
            intent.putExtra("FScore", score);
            SavePreferences();
            SharedPreferences sharedPreferences2 = getSharedPreferences("PhonemicFin",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            editor2.putInt("Status",1);
            editor2.apply();
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

    // code para di magkeep playing yung sounds
    @Override
    public void onBackPressed() {
        SavePreferences();
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You can resume your progress later.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        PhonemicAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}