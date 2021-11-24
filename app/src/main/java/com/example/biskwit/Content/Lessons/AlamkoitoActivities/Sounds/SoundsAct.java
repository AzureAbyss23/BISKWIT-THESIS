package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Sounds;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.Content.Score;
import com.example.biskwit.R;

public class SoundsAct extends AppCompatActivity {

    EditText spell;
    ImageView nextButton;
    String[] sound_data = {"tunog_jeep","tunog_cat","tunog_chicken","tunog_bird","tunog_ambulance",
            "tunog_bee","tunog_thunder","tunog_pig","tunog_dog","tunog_frog"};
    String[] data = {"Jeep","Cat","Manok","Ibon","Ambulansya","Bubuyog","Kidlat","Baboy","Aso","Palaka"};
    int all_ctr = 0;
    int status = 0;
    double score = 0, add = 0;
    MediaPlayer ai;
    ImageView sound, bot2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds2);

        spell = findViewById(R.id.Spell);
        nextButton = findViewById(R.id.nextButton);
        sound = findViewById(R.id.Sound);
        bot2 = findViewById(R.id.Bot2);

        score = getIntent().getIntExtra("Score",0);
        status = getIntent().getIntExtra("Status",0);

        if(LoadPreferences()){
            //getData();
            //CurrentProgress = all_ctr + 1;
            //progressBar.setProgress(CurrentProgress);
        } else {
            //getData();
            //progressBar.setProgress(CurrentProgress);
        }

        ai = MediaPlayer.create(SoundsAct.this, R.raw.kab5_p5_2);
        ai.start();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                String word = spell.getText().toString();
                if(word.matches("")){
                    showToast("Ilagay ang iyong sagot");
                } else {
                    spell.getText().clear();
                    printSimilarity(word, data[all_ctr]);
                    if (all_ctr < (data.length-1)) {
                        ++all_ctr;
                        score += add;
                    } else {
                        score += add;
                        Intent intent = new Intent(SoundsAct.this, Score.class);
                        intent.putExtra("Average",data.length);
                        intent.putExtra("Status",status);
                        intent.putExtra("LessonType","Alamkoito");
                        intent.putExtra("LessonMode","Sounds");
                        intent.putExtra("Score", score);
                        SharedPreferences sharedPreferencesF = getSharedPreferences("Sounds", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorF = sharedPreferencesF.edit();
                        editorF.clear();
                        editorF.apply();
                        SharedPreferences sharedPreferences = getSharedPreferences("SoundsAct", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        SharedPreferences sharedPreferences2 = getSharedPreferences("SoundsFin",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.clear();
                        editor2.apply();
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(sound_data[all_ctr], "raw", getPackageName());
                ai = MediaPlayer.create(SoundsAct.this, sound);
                ai.start();
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(SoundsAct.this, R.raw.kab5_p5_2);
                ai.start();
            }
        });

    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SoundsAct", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Counter", all_ctr);
        editor.putString("Score",Double.toString(score));
        editor.apply();
    }

    private boolean LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("SoundsAct",Context.MODE_PRIVATE);
        if(sharedPreferences.contains("Counter") && sharedPreferences.contains("Score")) {
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

    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(ai!=null){
            ai.stop();
            ai.release();
            ai = null;
        }
    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0;}

        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    public void printSimilarity(String s, String t) {

        float val = Float.parseFloat(String.format(
                "%.3f", similarity(s, t), s, t));
        if(val >= 0.0 && val <= 0.49){
            add = 0;
            showToast("HINDI TUGMA");
            ai = MediaPlayer.create(SoundsAct.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            add = 0.5;
            showToast("MABUTI");
            ai = MediaPlayer.create(SoundsAct.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            add = 1;
            showToast("MAHUSAY!");
            ai = MediaPlayer.create(SoundsAct.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    @Override
    public void onBackPressed() {
        SavePreferences();
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You can resume your progress later.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SoundsAct.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}