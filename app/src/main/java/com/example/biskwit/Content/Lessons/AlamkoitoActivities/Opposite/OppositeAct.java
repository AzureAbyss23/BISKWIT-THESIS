package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Opposite;

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

import com.example.biskwit.R;

public class OppositeAct extends AppCompatActivity {

    Button ch1,ch3;
    String[] data = {"Malamig","Mataba","Mabango","Masaya","Gabi","Tuwid","Masipag","Mataas","Maikli",
            "Malaki","Matanda"};
    String[][] choice = {{"Mainit","Maginaw"},{"Payat","Manipis"},{"Malinis","Mabaho"}, {"Malungkot","Matingkad"},
            {"Umaga","Hapon"},{"Baluktot","Pantay"}, {"Masikap","Tamad"},{"Pandak","Malawak"},
            {"Mahaba","Makulay"}, {"Mabango","Maliit"},{"Bata","Maedad"}};
    int all_ctr = 0, score = 0;
    int status = 0;
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
        setContentView(R.layout.activity_opposite2);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        final String UserScore = "userscore"+id+"Opposite";
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

        ai = MediaPlayer.create(OppositeAct.this, R.raw.kab5_p4_2);
        ai.start();

        ch1 = findViewById(R.id.Choice1);
        ch3 = findViewById(R.id.Choice3);
        word = findViewById(R.id.Word);
        bot2 = findViewById(R.id.Bot2);

        word.setText(data[all_ctr]);
        ch1.setText(choice[all_ctr][0]);
        ch3.setText(choice[all_ctr][1]);

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
                ai = MediaPlayer.create(OppositeAct.this, R.raw.kab5_p4_2);
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
            case "malamig":
                if(z=="Mainit"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mataba":
                if(z=="Payat"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mabango":
                if(z=="Mabaho"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "masaya":
                if(z=="Malungkot"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "gabi":
                if(z=="Umaga"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "tuwid":
                if(z=="Baluktot"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "masipag":
                if(z=="Tamad"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "mataas":
                if(z=="Pandak"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "maikli":
                if(z=="Mahaba"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "malaki":
                if(z=="Maliit"){
                    score += 1;
                    showToast("TAMA!");
                } else showToast("MALI");
                break;
            case "matanda":
                if(z=="Bata"){
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
            Intent intent = new Intent(OppositeAct.this, Opposite.class);
            intent.putExtra("DataLength",data.length);
            intent.putExtra("Status",status);
            intent.putExtra("FScore", score);
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
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You will not be able to save your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        OppositeAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}