package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Synonymous;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Opposite.Opposite;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Opposite.OppositeAct;
import com.example.biskwit.R;

public class SynonymousAct extends AppCompatActivity {

    Button ch1,ch3;
    String[] data = {"Maganda","Masarap","Mabagal","Maingay","Mayaman","Malungkot","Mababa","Mataas","Mabango",
            "Mataba","Matalas"};
    String[][] choice = {{"Pangit","Marikit"},{"Malinamnam","Maamoy"},{"Makupad","Mabilis"}, {"Maayos","Magulo"},
            {"Masalapi","Mahirap"}, {"Malumbay","Masaya"},{"Pandak","Mataas"},{"Matangkad","Mababa"},
            {"Maayos","Mahalimuyak"},{"Malusog","Payat"},{"Mapurol","Matalim"}};
    int all_ctr = 0;
    int status = 0;
    double score = 0;
    TextView word;
    ImageView bot2;
    MediaPlayer ai;

    SharedPreferences scores,logger;
    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonymous2);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        final String UserScore = "userscore"+id+"Synonymous";
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

        ai = MediaPlayer.create(SynonymousAct.this, R.raw.kab5_p3_2);
        ai.start();

        ch1 = findViewById(R.id.Choice1);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);

        if(LoadPreferences()){
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        } else {
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        }

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
                ai = MediaPlayer.create(SynonymousAct.this, R.raw.kab5_p3_2);
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

    public void result(String s, String z){
        switch(s.toLowerCase()){
            case "maganda":
                if(z=="Marikit"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "masarap":
                if(z=="Malinamnam"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mabagal":
                if(z=="Makupad"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "maingay":
                if(z=="Magulo"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mayaman":
                if(z=="Masalapi"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "malungkot":
                if(z=="Malumbay"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mababa":
                if(z=="Pandak"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mataas":
                if(z=="Matangkad"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mabango":
                if(z=="Mahalimuyak"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mataba":
                if(z=="Malusog"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "matalas":
                if(z=="Matalim"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
        }

        if(all_ctr < (data.length-1)) {
            ++all_ctr;
            word.setText(data[all_ctr]);
            ch1.setText(choice[all_ctr][0]);
            ch3.setText(choice[all_ctr][1]);
        } else {
            Intent intent = new Intent(SynonymousAct.this, Synonymous.class);
            intent.putExtra("DataLength",data.length);
            intent.putExtra("Status",status);
            intent.putExtra("FScore", score);
            SavePreferences();
            SharedPreferences sharedPreferences2 = getSharedPreferences("SynonymousFin",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            editor2.putInt("Status",1);
            editor2.apply();
            startActivity(intent);
            finish();
        }
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SynonymousAct",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Counter", all_ctr);
        editor.putString("Score",Double.toString(score));
        editor.apply();
    }

    private boolean LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SynonymousAct",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("SynonymousFin",Context.MODE_PRIVATE);
        if(sharedPreferences2.contains("Status")){
            all_ctr = sharedPreferences.getInt("Counter", 0);
            score = Double.parseDouble(sharedPreferences.getString("Score", null));
            Intent intent = new Intent(SynonymousAct.this, Synonymous.class);
            intent.putExtra("DataLength",data.length);
            intent.putExtra("Status",status);
            intent.putExtra("FScore", score);
            startActivity(intent);
            finish();
            return true;
        } else if(sharedPreferences.contains("Counter") && sharedPreferences.contains("Score")) {
            all_ctr = sharedPreferences.getInt("Counter", 0);
            score = Double.parseDouble(sharedPreferences.getString("Score", null));
            return true;
        } else return false;
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
                        SynonymousAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}