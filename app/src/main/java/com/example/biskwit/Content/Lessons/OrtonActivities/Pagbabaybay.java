package com.example.biskwit.Content.Lessons.OrtonActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class Pagbabaybay extends AppCompatActivity {

    EditText spell;
    ImageView nextButton;
    String[] data = {"aso","elepante","ibon","oso",",unggoy","ako ay isang pilipino","ako ay masipag",
            "si maria ay maganda","naglinis ng banyo si mama","napakainit ng panahon ngayon"};
    int all_ctr = 0, score = 0;
    MediaPlayer ai;
    ImageView bot, bot2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagbabaybay);

        spell = findViewById(R.id.Spell);
        nextButton = findViewById(R.id.nextButton);
        bot2 = findViewById(R.id.Bot2);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = spell.getText().toString();
                if(word.matches("")){
                    toastMsg("You must enter your spelling.");
                } else {
                    spell.getText().clear();
                    printSimilarity(word, data[all_ctr]);
                    if (all_ctr < 9) {
                        ++all_ctr;
                        toastMsg("Next Word.");
                    } else {
                        Intent intent = new Intent(Pagbabaybay.this, Score.class);
                        intent.putExtra("Score", score);
                        startActivity(intent);
                    }
                }
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Pagbabaybay.this, R.raw.kab2_p4);
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

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
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
            score += 0;
        }
        else if(val >= 0.5 && val <= 0.99){
            score += 1;
        }
        else if(val ==1.0){
            score += 2;
        }

    }
}