package com.example.biskwit.Content.Lessons.OrtonActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Content.Lessons.AlphabetActivities.Alphabet;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Pagbabaybay extends AppCompatActivity {

    EditText spell;
    ImageView nextButton;
    String[] words;
    String[] botins;
    int all_ctr = 0, all_ctr2 = 0;
    int status = 0;
    double score = 0;
    MediaPlayer ai;
    ImageView bot, bot2;

    ProgressDialog progressDialog;

    SharedPreferences scores,logger;
    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagbabaybay);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        final String UserScore = "userscore"+id+"Pagbabaybay";
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

        spell = findViewById(R.id.Spell);
        nextButton = findViewById(R.id.nextButton);
        bot = findViewById(R.id.Bot);
        bot2 = findViewById(R.id.Bot2);

        progressDialog = new ProgressDialog(Pagbabaybay.this);

        getData();
        ai = MediaPlayer.create(Pagbabaybay.this, R.raw.kab2_p4);
        ai.start();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                String word = spell.getText().toString();
                if(word.matches("")){
                    showToast("Subukan mo muna ispell ito");
                } else {
                    spell.getText().clear();
                    printSimilarity(word, words[all_ctr]);
                    if (all_ctr < 9) {
                        ++all_ctr;
                        if(all_ctr > 5) all_ctr2++;
                    } else {
                        Intent intent = new Intent(Pagbabaybay.this, Score.class);
                        intent.putExtra("Average",words.length);
                        intent.putExtra("Status",status);
                        intent.putExtra("LessonType","Orton");
                        intent.putExtra("LessonMode","Pagbabaybay");
                        intent.putExtra("Score", score);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                if(all_ctr < 5) {
                    int sound = res.getIdentifier(words[all_ctr].toLowerCase(), "raw", getPackageName());
                    ai = MediaPlayer.create(Pagbabaybay.this, sound);
                } else {
                    int sound = res.getIdentifier(botins[all_ctr2].toLowerCase(), "raw", getPackageName());
                    ai = MediaPlayer.create(Pagbabaybay.this, sound);
                }
                ai.start();
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
            showToast("HINDI TUGMA");
            ai = MediaPlayer.create(Pagbabaybay.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            score += 0.5;
            showToast("MABUTI");
            ai = MediaPlayer.create(Pagbabaybay.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            score += 1;
            showToast("MAHUSAY!");
            ai = MediaPlayer.create(Pagbabaybay.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    private void getData() {

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_pagbabaybay.php";

        StringRequest stringRequest = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Pagbabaybay.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
        ArrayList<String> data = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                data.add(collegeData.getString("word"));
            }
            Collections.shuffle(data);
            words = new String[data.size()];
            words = data.toArray(words);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!words[0].equals("")){
            botins = new String[5];
            for(int i = 0, j = 5;i < 5 && j < 10; i++, j++) {
                botins[i] = words[j].replaceAll(" ","_").toLowerCase();
            }
            progressDialog.dismiss();
        } else {
            Toast.makeText(Pagbabaybay.this, "No data", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
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
                        Pagbabaybay.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}