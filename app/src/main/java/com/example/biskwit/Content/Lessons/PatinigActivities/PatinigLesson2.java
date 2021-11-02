package com.example.biskwit.Content.Lessons.PatinigActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
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
import java.util.ArrayList;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Content.Lessons.OrtonActivities.Pagbabaybay;
import com.example.biskwit.Content.Lessons.OrtonActivities.Sight;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatinigLesson2 extends AppCompatActivity {

    TextView txtresult,txtword;
    ImageView next,bot,bot2,wordimg;
    ImageButton mic;
    String word = "";
    String[] P_Lesson_Words;
    int all_ctr = 0;
    int click = 0;
    int id = 0;
    int mic_ctr = 0, score = 0, add = 0;
    MediaPlayer ai;
    Intent intent;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

    //ito yung sa progress bar
    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patinig_lesson2);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);
        }

        txtresult = (TextView) findViewById(R.id.result);
        txtword = (TextView) findViewById(R.id.Word);
        next = findViewById(R.id.nextButton);
        bot = findViewById(R.id.Bot);
        bot2 = findViewById(R.id.Bot2);
        mic = findViewById(R.id.imageView2);
        wordimg = findViewById(R.id.WordImg);

        getData();

        String letter = intent.getStringExtra("letter");
        Resources res = getResources();
        int sound = res.getIdentifier("kab3_p2_"+letter.toLowerCase(), "raw", getPackageName());
        ai = MediaPlayer.create(PatinigLesson2.this, sound);
        ai.start();

        progressBar = findViewById(R.id.ProgressBar); // need ito para sa progress

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all_ctr < (P_Lesson_Words.length - 1)) {
                    if(mic_ctr == 0){
                        showToast("Try it first!");
                    } else {
                        ++all_ctr;
                        mic_ctr = 0;
                        score += add;
                        txtword.setText(P_Lesson_Words[all_ctr]);
                        txtresult.setText("Press the Mic Button");
                        id = setImg();
                        wordimg.setImageResource(id);
                        stopPlaying();
                        CurrentProgress = CurrentProgress + 714;
                        progressBar.setProgress(CurrentProgress);
                        progressBar.setMax(10000);
                    }
                } else {
                    if(mic_ctr == 0){
                        showToast("Try it first!");
                    } else {
                        score += add;
                        Intent intent = new Intent(PatinigLesson2.this, Score.class);
                        intent.putExtra("Score", score);
                        startActivity(intent);
                    }
                }
            }
        });

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(P_Lesson_Words[all_ctr], "raw", getPackageName());
                ai = MediaPlayer.create(PatinigLesson2.this, sound);
                ai.start();
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                String letter = intent.getStringExtra("letter");
                Resources res = getResources();
                int sound = res.getIdentifier("kab3_p2_"+letter.toLowerCase(), "raw", getPackageName());
                ai = MediaPlayer.create(PatinigLesson2.this, sound);
                ai.start();
            }
        });

        //SPEECH RECOGNIZER
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
                txtresult.setHint("Listening...");
            }

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
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                word = data.get(0);
                printSimilarity(word.toString(),P_Lesson_Words[all_ctr]);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click==0){
                    speechRecognizer.startListening(speechRecognizerIntent);
                    mic.setImageResource(R.drawable.mic_on);
                    txtresult.setText("Speak Now");
                    mic_ctr++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    txtresult.setText("Press the Mic Button");
                    mic.setImageResource(R.drawable.mic_off);
                    click=0;
                }
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
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
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
            showToast("TRY AGAIN");
            ai = MediaPlayer.create(PatinigLesson2.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            add = 1;
            showToast("GOOD, BUT YOU CAN DO BETTER");
            ai = MediaPlayer.create(PatinigLesson2.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            add = 2;
            showToast("GREAT! YOU DID IT!");
            ai = MediaPlayer.create(PatinigLesson2.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    public int setImg(){
        Resources res = this.getResources();
        int resID;
        return resID = res.getIdentifier("patinig_"+P_Lesson_Words[all_ctr].toLowerCase(), "drawable", this.getPackageName());
    }

    private void getData() {
        progressDialog = new ProgressDialog(PatinigLesson2.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        intent = getIntent();
        String letter = intent.getStringExtra("letter");
        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_patinig.php?letter="+letter;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PatinigLesson2.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
            P_Lesson_Words = new String[data.size()];
            P_Lesson_Words = data.toArray(P_Lesson_Words);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!P_Lesson_Words[0].equals("")){
            txtword.setText(P_Lesson_Words[0]);
            id = setImg();
            wordimg.setImageResource(id);
            progressDialog.dismiss();
        } else {
            Toast.makeText(PatinigLesson2.this, "No data", Toast.LENGTH_LONG).show();
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
                        PatinigLesson2.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}