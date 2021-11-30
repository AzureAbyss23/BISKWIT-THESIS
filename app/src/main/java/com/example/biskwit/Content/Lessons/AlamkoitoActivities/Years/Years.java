package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Years;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Days.Days;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Days.DaysAct;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Years extends AppCompatActivity {

    TextView txtresult,txtword,scorectr,accuracyctr;
    ImageView next,bot,bot2,wordimg;
    ImageButton mic;
    String word = "";
    String[] P_Lesson_Words = {"Enero","Pebrero","Marso","Abril","Mayo","Hunyo","Hulyo","Agosto","Setyembre","Oktubre","Nobyembre","Disyembre"};
    String[] Title = {"Bagong Taon","Araw ng mga Puso","Buwan ng Pagtatapos","Bakasyon","Flores de Mayo","Araw ng Kalayaan","Buwan ng Nutrisyon",
            "Buwan ng Wika","Buwan ng Palakasan","Mga Nagkakaisang Bansa","Araw ng mga Patay","Pasko"};
    int all_ctr = 0;
    int click = 0;
    int mic_ctr = 0;
    int id = 0;
    int status = 0;
    double score = 0, add = 0;
    MediaPlayer ai;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

    //ito yung sa progress bar
    private int CurrentProgress = 1;
    private ProgressBar progressBar;

    ProgressDialog progressDialog;

    SharedPreferences scores,logger;
    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id2 = logger.getInt(UserID,0);
        final String UserScore = "userscore"+id2+"ABCD";
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
        scorectr = findViewById(R.id.scorecounter);
        accuracyctr = findViewById(R.id.accuracycounter);
        progressBar = findViewById(R.id.ProgressBar); // need ito para sa progress

        if(LoadPreferences()){
            CurrentProgress += all_ctr;
            progressBar.setProgress(CurrentProgress);
        } else {
            progressBar.setProgress(CurrentProgress);
        }

        txtword.setText(P_Lesson_Words[all_ctr]);
        id = setImg();
        wordimg.setImageResource(id);

        progressBar.setMax(P_Lesson_Words.length * 2);

        ai = MediaPlayer.create(Years.this, R.raw.kab5_p2_1);
        ai.start();

        progressBar.setProgress(CurrentProgress);
        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(all_ctr < (P_Lesson_Words.length - 1)) {
                    if(mic_ctr == 0){
                        txtresult.setText("Try it first!");
                    } else {
                        ++all_ctr;
                        txtword.setText(P_Lesson_Words[all_ctr]);
                        txtresult.setText("Speak Now");
                        score += add;
                        add = 0;
                        mic_ctr = 0;
                        scorectr.setText("Score: " + score + "/" + (P_Lesson_Words.length * 2));
                        accuracyctr.setText("Accuracy: 0%");
                        stopPlaying();
                        id = setImg();
                        wordimg.setImageResource(id);
                        CurrentProgress = CurrentProgress + 1;
                        progressBar.setProgress(CurrentProgress);
                    }
                } else {
                    if(mic_ctr == 0){
                        txtresult.setText("Try it first!");
                    } else {
                        score += add;
                        scorectr.setText("Score: " + score + "/" + (P_Lesson_Words.length * 2));
                        Intent intent = new Intent(Years.this, YearsAct.class);
                        intent.putExtra("Status",status);
                        intent.putExtra("Score", score);
                        //intent.putExtra("Title",Title);
                        //intent.putExtra("data",P_Lesson_Words);
                        SavePreferences();
                        SharedPreferences sharedPreferences2 = getSharedPreferences("YearsFin",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.putInt("Status",1);
                        editor2.apply();
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
                int sound = res.getIdentifier(P_Lesson_Words[all_ctr].toLowerCase(), "raw", getPackageName());
                ai = MediaPlayer.create(Years.this, sound);
                ai.start();
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Years.this, R.raw.kab5_p2_1);
                ai.start();
            }
        });

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
                txtresult.setText("Listening...");
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
                //micButton.setImageResource(R.drawable.ic_mic_black_off);
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
                    stopPlaying();
                    speechRecognizer.startListening(speechRecognizerIntent);
                    mic.setImageResource(R.drawable.mic_on);
                    txtresult.setText("Speak Now");
                    mic_ctr++;
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
                    mic.setImageResource(R.drawable.mic_off);
                    txtresult.setText("Press the Mic Button");
                    click=0;
                }
            }
        });
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("Years",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Counter", all_ctr);
        editor.putString("Score",Double.toString(score));
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private boolean LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("Years",Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("YearsFin",Context.MODE_PRIVATE);
        if(sharedPreferences2.contains("Status")){
            all_ctr = sharedPreferences.getInt("Counter", 0);
            score = Double.parseDouble(sharedPreferences.getString("Score", null));
            Intent intent = new Intent(Years.this, YearsAct.class);
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

    public int setImg(){
        Resources res = this.getResources();
        int resID;
        return resID = res.getIdentifier("years_"+Title[all_ctr].replace(" ","").toLowerCase(), "drawable", this.getPackageName());
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

    @SuppressLint("SetTextI18n")
    public void printSimilarity(String s, String t) {

        float val = Float.parseFloat(String.format(
                "%.3f", similarity(s, t), s, t));
        if(val >= 0.0 && val <= 0.49){
            add = 0;
            showToast("onestar");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Years.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            add = 0.5;
            showToast("twostars");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Years.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            add = 1;
            showToast("threestars");
            accuracyctr.setText("Accuracy: "+Math.round(val*100)+"%");
            ai = MediaPlayer.create(Years.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    public void showToast(String s) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast_root));

        ImageView toastImage = layout.findViewById(R.id.toast_image);
        Resources res = this.getResources();
        int resID;
        resID = res.getIdentifier(s, "drawable", this.getPackageName());
        toastImage.setBackgroundResource(resID);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void getData() {
        progressDialog = new ProgressDialog(Years.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_years.php";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Years.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                data.add(collegeData.getString("title"));
                data2.add(collegeData.getString("word"));
            }
            Title = new String[data.size()];
            Title = data.toArray(Title);
            P_Lesson_Words = new String[data2.size()];
            P_Lesson_Words = data2.toArray(P_Lesson_Words);
            progressBar.setMax(P_Lesson_Words.length*2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!P_Lesson_Words[0].equals("")){
            txtword.setText(P_Lesson_Words[all_ctr]);
            id = setImg();
            wordimg.setImageResource(id);
            progressDialog.dismiss();
        } else {
            Toast.makeText(Years.this, "No data", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        SavePreferences();
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You will not be able to save your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Years.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}