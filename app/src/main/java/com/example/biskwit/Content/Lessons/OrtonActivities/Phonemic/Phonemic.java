package com.example.biskwit.Content.Lessons.OrtonActivities.Phonemic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Content.Score;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Phonemic extends AppCompatActivity {

    TextView word1,word2,category,txtresult,scorectr,accuracyctr;
    ImageButton mic1,mic2;
    ImageView next,bot2;
    String word;
    String[] holder;
    String[][] words1;
    String[][] words2;
    String[] categ = {"Hayop","Kasuotan","Prutas","Gulay"};
    int all_ctr = 0, all_ctr2 = 0, all_ctr3 = 0, click = 0, micctr1 = 0, micctr2 = 0, mic_ctr1 = 0, mic_ctr2 = 0;
    int status = 0;
    double add = 0, add2 = 0, score = 0;
    int datalength = 0;
    MediaPlayer ai;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

    ProgressDialog progressDialog;

    private int CurrentProgress = 1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemic);

        score = getIntent().getIntExtra("FScore",0);
        status = getIntent().getIntExtra("Status",0);
        datalength = getIntent().getIntExtra("DataLength",0);

        word1 = findViewById(R.id.Word);
        word2 = findViewById(R.id.Word2);
        category = findViewById(R.id.Category);
        mic1 = findViewById(R.id.Mic);
        mic2 = findViewById(R.id.Mic2);
        bot2 = findViewById(R.id.Bot2);
        next = findViewById(R.id.nextButton);
        txtresult = findViewById(R.id.result);
        scorectr = findViewById(R.id.scorecounter);
        accuracyctr = findViewById(R.id.accuracycounter);
        progressBar = findViewById(R.id.ProgressBar);

        progressDialog = new ProgressDialog(Phonemic.this);
        ai = MediaPlayer.create(Phonemic.this, R.raw.kab2_p1);
        ai.start();

        if(LoadPreferences()){
            getData();
            CurrentProgress += all_ctr;
            progressBar.setProgress(CurrentProgress);
        } else {
            getData();
            progressBar.setProgress(CurrentProgress);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fil-PH");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() { txtresult.setText("Listening..."); }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                txtresult.setText("Press the Mic Button Again");
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                if(micctr1>0) {
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    word = data.get(0);
                    printSimilarity(word.toString(), words1[all_ctr][all_ctr2]);
                }
                else if(micctr2>0){
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    word = data.get(0);
                    printSimilarity(word.toString(), words2[all_ctr][all_ctr2]);
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
                    stopPlaying();
                    speechRecognizer.startListening(speechRecognizerIntent);
                    mic1.setImageResource(R.drawable.mic_on);
                    txtresult.setText("Speak Now");
                    micctr1++;
                    mic_ctr1++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    mic1.setImageResource(R.drawable.mic_off);
                    txtresult.setText("Press the Mic Button");
                    click=0;
                }
            }
        });

        mic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0){
                    stopPlaying();
                    speechRecognizer.startListening(speechRecognizerIntent);
                    mic2.setImageResource(R.drawable.mic_on);
                    txtresult.setText("Speak Now");
                    micctr2++;
                    mic_ctr2++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    mic2.setImageResource(R.drawable.mic_off);
                    txtresult.setText("Press the Mic Button");
                    click=0;
                }
            }
        });

        word1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(words1[all_ctr][all_ctr2].toLowerCase(), "raw", getPackageName());
                ai = MediaPlayer.create(Phonemic.this, sound);
                ai.start();
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Phonemic.this, R.raw.kab2_p1);
                ai.start();
            }
        });

       word2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(words2[all_ctr][all_ctr2].toLowerCase(), "raw", getPackageName());
                ai = MediaPlayer.create(Phonemic.this, sound);
                ai.start();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(all_ctr3 < ((words1.length * words1[0].length)-1)) {
                    if (mic_ctr1 == 0 || mic_ctr2 == 0) {
                        showToast("Subukan mo muna ito");
                    } else {
                        all_ctr3++;
                        setupnext();
                        txtresult.setText("Press the Mic Button");
                        word1.setText(words1[all_ctr][all_ctr2]);
                        word2.setText(words2[all_ctr][all_ctr2]);
                        category.setText(categ[all_ctr]);
                        mic_ctr1 = 0;
                        mic_ctr2 = 0;
                        score += add;
                        score += add2;
                        scorectr.setText("Score:" + score + "/" + (words1.length + words2.length));
                        accuracyctr.setText("Accuracy: 0%");
                        add = 0;
                        add2 = 0;
                        stopPlaying();
                        CurrentProgress = CurrentProgress + 1;
                        progressBar.setProgress(CurrentProgress);
                    }
                } else {
                    if (mic_ctr1 == 0 || mic_ctr2 == 0) {
                        showToast("Subukan mo muna ito");
                    } else {
                        score += add;
                        score += add2;
                        Intent intent = new Intent(Phonemic.this, Score.class);
                        intent.putExtra("Average",(((words1.length * words1[0].length)-1) + datalength));
                        intent.putExtra("Status",status);
                        intent.putExtra("LessonType","Orton");
                        intent.putExtra("LessonMode","Phonemic");
                        intent.putExtra("Score", score);
                        SharedPreferences sharedPreferences3 = getSharedPreferences("PhonemicAct",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                        editor3.clear();
                        editor3.apply();
                        SharedPreferences sharedPreferences = getSharedPreferences("Phonemic",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        SharedPreferences sharedPreferences2 = getSharedPreferences("PhonemicFin",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.clear();
                        editor2.apply();
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("Phonemic", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Counter", all_ctr);
        editor.putInt("Counter2", all_ctr2);
        editor.putInt("Counter3", all_ctr3);
        editor.putString("Score",Double.toString(score));
        editor.apply();
    }

    private boolean LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("Phonemic", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("Counter") && sharedPreferences.contains("Score") && sharedPreferences.contains("Counter2") && sharedPreferences.contains("Counter3")) {
            all_ctr = sharedPreferences.getInt("Counter", 0);
            all_ctr2 = sharedPreferences.getInt("Counter2", 0);
            all_ctr3 = sharedPreferences.getInt("Counter3", 0);
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

    @SuppressLint("SetTextI18n")
    public void printSimilarity(String s, String t) {

        float val = Float.parseFloat(String.format(
                "%.3f", similarity(s, t), s, t));
        if(val >= 0.0 && val <= 0.49){
            if(micctr1>0) { add = 0; micctr1=0; }
            else if(micctr2>0){ add2 = 0; micctr2=0; }
            showToast("onestar");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Phonemic.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            if(micctr1>0) { add = 0.25; micctr1=0; }
            else if(micctr2>0){ add2 = 0.25; micctr2=0; }
            showToast("twostars");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Phonemic.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            if(micctr1>0) { add = 0.5; micctr1=0; }
            else if(micctr2>0){ add2 = 0.5; micctr2=0; }
            showToast("threestars");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Phonemic.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    private void getData() {

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_phoenemic.php";

        StringRequest stringRequest = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Phonemic.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
        //HashSet<String> data = new HashSet<String>();
        ArrayList<String> data2 = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                //data.add(collegeData.getString("category"));
                data2.add(collegeData.getString("word"));
            }
            //categ = new String[data.size()];
            //categ = data.toArray(categ);
            holder = new String[data2.size()];
            holder = data2.toArray(holder);
            CurrentProgress += holder.length / 2;
            progressBar.setProgress(CurrentProgress);
            progressBar.setMax(holder.length);

            int holder_ctr=0;
            words1 = new String[4][5];
            words2 = new String[4][5];
            for(int z = 0;z < 4;z++) {
                for (int i = 0; i < 5; i++) {
                    words1[z][i] = holder[holder_ctr];
                    holder_ctr++;
                }
            }
            for(int q = 0;q < 4;q++) {
                for (int k = 0; k < 5; k++) {
                    words2[q][k] = holder[holder_ctr];
                    holder_ctr++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!holder[0].equals("")){
            category.setText(categ[0]);
            word1.setText(words1[all_ctr][all_ctr2]);
            word2.setText(words2[all_ctr][all_ctr2]);
            progressDialog.dismiss();
        } else {
            Toast.makeText(Phonemic.this, "No data", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    // code para di magkeep playing yung sounds
    @Override
    public void onBackPressed() {
        SavePreferences();
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You can resume your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Phonemic.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}