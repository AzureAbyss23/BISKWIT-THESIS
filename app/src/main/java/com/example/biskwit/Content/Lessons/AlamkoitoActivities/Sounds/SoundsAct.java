package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Sounds;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.R;

public class SoundsAct extends AppCompatActivity {

    EditText spell;
    ImageView nextButton;
    String[] sound_data = {"tunog_car","tunog_cat","tunog_chicken","tunog_bird","tunog_ambulance",
            "tunog_bee","tunog_thunder","tunog_pig","tunog_dog","tunog_frog"};
    String[] data = {"tunog_car","tunog_cat","tunog_chicken","tunog_bird",",tunog_ambulance",
            "tunog_bee","tunog_thunder","tunog_pig","tunog_dog","tunog_frog"};
    int all_ctr = 0, score = 0;
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

        ai = MediaPlayer.create(SoundsAct.this, R.raw.kab5_p5_2);
        ai.start();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                String word = spell.getText().toString();
                if(word.matches("")){
                    toastMsg("You must enter your answer.");
                } else {
                    spell.getText().clear();
                    printSimilarity(word, data[all_ctr]);
                    if (all_ctr < 9) {
                        ++all_ctr;
                        toastMsg("Next Sound.");
                    } else {
                        Intent intent = new Intent(SoundsAct.this, Score.class);
                        intent.putExtra("Score", score);
                        startActivity(intent);
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

    public void showToast(String s) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_root));
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(s);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
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