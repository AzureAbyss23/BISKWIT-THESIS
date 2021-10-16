package com.example.biskwit.Content.Lessons.OrtonActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigLesson2;
import com.example.biskwit.R;

import java.util.ArrayList;

public class Phoenimic extends AppCompatActivity {

    TextView word1,word2,category;
    ImageButton mic1,mic2;
    ImageView next;
    String word;
    String[][] words1 = {{"ibon","aso","bibe","matsing","pusa"},{"polo","pantalon","medyas","kwintas","palda"}};
    String[][] words2 = {{"hipon","oso","tigre","kambing","daga"},{"sando","sinturon","tsinelas","pulseras","blusa"}};
    String[] categ = {"Hayop","Kasuotan","Prutas","Gulay"};
    int all_ctr = 0, all_ctr2 = 0, click = 0, micctr1 = 0, micctr2 = 0;
    MediaPlayer ai;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoenimic);

        word1 = findViewById(R.id.Word);
        word2 = findViewById(R.id.Word2);
        category = findViewById(R.id.Category);
        mic1 = findViewById(R.id.Mic);
        mic2 = findViewById(R.id.Mic2);
        next = findViewById(R.id.nextButton);

        word1.setText(words1[all_ctr][all_ctr2]);
        word2.setText(words2[all_ctr][all_ctr2]);
        category.setText(categ[all_ctr]);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fil-PH");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                //txtresult.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //micButton.setImageResource(R.drawable.ic_mic_black_off);
                if(micctr1>0) {
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    word = data.get(0);
                    printSimilarity(word.toString(), words1[all_ctr][all_ctr2]);
                    micctr1=0;
                }
                else if(micctr2>0){
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    word = data.get(0);
                    printSimilarity(word.toString(), words2[all_ctr][all_ctr2]);
                    micctr2=0;
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        mic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0){
                    speechRecognizer.startListening(speechRecognizerIntent);
                    toastMsg("Start speaking");
                    micctr1++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    click=0;
                }
            }
        });

        mic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0){
                    speechRecognizer.startListening(speechRecognizerIntent);
                    toastMsg("Start speaking");
                    micctr2++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    click=0;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupnext();
                word1.setText(words1[all_ctr][all_ctr2]);
                word2.setText(words2[all_ctr][all_ctr2]);
                category.setText(categ[all_ctr]);

                stopPlaying();

                //pampagrayscale lang to nung bot na icon
               // ColorMatrix matrix = new ColorMatrix();
                //matrix.setSaturation(0);
               // bot.setColorFilter(new ColorMatrixColorFilter(matrix));
            }
        });
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void setupnext(){
        if(all_ctr2 < 4){
            ++all_ctr2;
        } else {
            ++all_ctr;
            all_ctr2=0;
        }
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
            ai = MediaPlayer.create(Phoenimic.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            ai = MediaPlayer.create(Phoenimic.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            ai = MediaPlayer.create(Phoenimic.this, R.raw.response_70_to_100);
            ai.start();
        }

    }
}