package com.example.biskwit.Content.Lessons.AlamkoitoActivities.Sounds;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigLesson2;
import com.example.biskwit.Content.Lessons.Score;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Sounds extends AppCompatActivity {
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    ProgressDialog progressDialog;
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    ImageView next;
    ImageView bot2;
    MediaPlayer ai;
    Intent intent;
    String[] tunog;
    String word = "";
    TextView txtword, txtresult;
    ImageView wordimg;
    ImageButton mic;
    int all_ctr = 0;
    int click = 0;
    int id = 0;
    int mic_ctr = 0;
    double score = 0, add = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);

        next = findViewById(R.id.nextButton);
        bot2 = findViewById(R.id.Bot2);
        txtword = findViewById(R.id.Word);
        wordimg = findViewById(R.id.WordImg);
        txtresult = findViewById(R.id.result);
        mic = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.ProgressBar);
        progressDialog = new ProgressDialog(Sounds.this);
        getData();
        ai = MediaPlayer.create(Sounds.this, R.raw.kab5_p5_1);
        ai.start();


        //ate chay bot
        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Sounds.this, R.raw.kab5_p5_1);
                ai.start();
            }
        });

        //next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all_ctr < (tunog.length - 1)) {
                    if(mic_ctr == 0){
                        showToast("Try it first!");
                    } else {
                        ++all_ctr;
                        mic_ctr = 0;
                        score += add;
                        txtword.setText(tunog[all_ctr]);
                        txtresult.setText("Press the Mic Button");
                        id = setImg();
                        wordimg.setImageResource(id);
                        stopPlaying();
                        //yung automatic instruction
                        Resources res = getResources();
                        int sound = res.getIdentifier("kab5_p5_"+ tunog[all_ctr].toLowerCase(),"raw", getPackageName());
                        ai = MediaPlayer.create(Sounds.this, sound);
                        ai.start();
                        CurrentProgress = CurrentProgress + 714;
                        progressBar.setProgress(CurrentProgress);
                        progressBar.setMax(10000);
                    }
                } else {
                    if(mic_ctr == 0){
                        showToast("Try it first!");
                    } else {
                        score += add;
                        intent = new Intent(Sounds.this, SoundsAct.class);
                        intent.putExtra("Score", score);
                        startActivity(intent);
                    }
                }
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
                printSimilarity(word.toString(),tunog[all_ctr]);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        //mic button
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
                    txtresult.setText("Press the Mic Button");
                    mic.setImageResource(R.drawable.mic_off);
                    click=0;
                }
            }
        });

        //sound of the image to be button
        wordimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(tunog[all_ctr].toLowerCase(),"raw", getPackageName());
                ai = MediaPlayer.create(Sounds.this, sound);
                ai.start();
            }
        });


    }

    //algo
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
            ai = MediaPlayer.create(Sounds.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            add = 1;
            showToast("GOOD, BUT YOU CAN DO BETTER");
            ai = MediaPlayer.create(Sounds.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            add = 2;
            showToast("GREAT! YOU DID IT!");
            ai = MediaPlayer.create(Sounds.this, R.raw.response_70_to_100);
            ai.start();
        }
    }

    //get data for the database
    private void getData() {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_sounds.php";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sounds.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
           tunog = new String[data.size()];
            tunog = data.toArray(tunog);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!tunog[0].equals("")){
            txtword.setText(tunog[0]);
            id = setImg();
            wordimg.setImageResource(id);
            progressDialog.dismiss();
        } else {
            Toast.makeText(Sounds.this, "No data", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    //set image array
    public int setImg(){
        Resources res = this.getResources();
        int resID;
        return resID = res.getIdentifier("sound_"+tunog[all_ctr].toLowerCase(), "drawable", this.getPackageName());
    }

    //back button
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit now?")
                .setMessage("You will not be able to save your progress.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Sounds.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }

    //toast for correction
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

    //bg music stop playing
    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(ai!=null){
            ai.stop();
            ai.release();
            ai = null;
        }
    }
}
