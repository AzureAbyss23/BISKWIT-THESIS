package com.example.biskwit.Content.Story.PabulaActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import com.example.biskwit.DBHelper;
import com.example.biskwit.R;

public class TipaklongLanggam extends AppCompatActivity {

    TextView txtstory,txtword,txtqueue;
    ImageView next,bot;
    ImageButton mic;
    String word = "";
    DBHelper DB;
    Cursor c;
    String[] P_Lesson_Words = {"Mataas ang sikat ng araw","Si Langgam ay masipag na nag-iipon ng pagkain","Si Tipaklong naman ay masayang umaawit habang naglalaro",
                        "Kaibigang Langgam halika maglaro tayo sabi ni Tipaklong","Pasensiya na kaibigang Tipaklong","Kailangan kong mag-ipon ng pagkain para sa tag-ulan sagot naman ni Langgam",
                        "Malayo pa naman ang tag-ulan","Tignan mo mataas pa ang sikat ng araw sagot ni Tipaklong","Pilit na niyayaya ni Tipaklong si Langgam ngunit patuloy lamang ito sa pag-iipon ng pagkain",
                        "Dumating ang tag-ulan at bumaha","Nasira ang halos lahat ng pananim","Sagana sa pagkain ang bahay ni langgam","Si Tipaklong naman ay nanginginig sa ginaw at gutom",
                        "Gutom na gutom na ako","Saan kaya ako makakukuha ng pagkain sabi ni Tipaklong","Pupunta na lang ako kay kaibigang Langgam",
                        "Basang basa at nanginginig sa lamig na pumunta si Tipaklong sa bahay ni Langgam","Kaibigang Langgam kaibigang Langgam tawag ni Tipaklong",
                        "Agad agad na binuksan ni Langgam ang pintuan nang marinig niya ang pagtawag ni Tipaklong","Kaibigang Langgam ako ay gutom na gutom","Wala akong naipong pagkain",
                        "Maaari bang makahingi sa iyo ng kahit kaunting makakain pakiusap ni Tipaklong","Oo naman kaibigang Tipaklong","Halika Tumuloy ka sa bahay ko",
                        "May naipon naman akong pagkain na maaari nating pagsaluhan ang sabi ni Langgam", "Salamat sa iyo kaibigang Langgam","Mula ngayon ay mag iipon na ako ng pagkain",
                        "Magiging masipag na rin ako katulad mo pangako ni Tipaklong","Mula noon ay natuto na si Tipaklong na mag-ipon ng pagkain kahit tag araw pa lamang"};
    String queue="",story="";
    StringBuffer buff;
    int all_ctr = 0;
    int click = 0;
    int queuectr=2;
    MediaPlayer ai;

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipaklong_langgam);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);
        }

        txtword = (TextView) findViewById(R.id.Word);
        txtstory = (TextView) findViewById(R.id.Story);
        txtqueue = (TextView) findViewById(R.id.Queue);
        next = findViewById(R.id.nextButton);
        bot = findViewById(R.id.Bot);
        mic = findViewById(R.id.imageView2);

        //DB = new DBHelper(this);

        //String letter = getIntent().getStringExtra("letter");

        //c = DB.getlessondata(letter);

        /*if(c.getCount()==0){
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
            return;
        } else {
            for (int i = 0;c.moveToNext();i++) {
                buff = new StringBuffer();
                //buff.append(c.getString(c.getColumnIndex("P_Lesson_Word")));
                //P_Lesson_Words[i] = buff.toString();
            }
        }
        c.close();*/
        progressBar = findViewById(R.id.ProgressBar); // need ito para sa progress

        txtword.setText(P_Lesson_Words[all_ctr]);
        for(int i = 1; i < 9; i++) {
            queue += (P_Lesson_Words[i] + "\n ");
            txtqueue.setText(queue);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++all_ctr;
                story += (P_Lesson_Words[all_ctr-1] + ". ");
                txtstory.setText(story);

                txtword.setText(P_Lesson_Words[all_ctr]);
                queue = " ";

                for(int i = queuectr; i < 9; i++) {
                    queue += (P_Lesson_Words[i] + ". ");
                }

                txtqueue.setText(queue);
                ++queuectr;

                stopPlaying();

                CurrentProgress = CurrentProgress +11;
                progressBar.setProgress(CurrentProgress);
                progressBar.setMax(100);

                //pampagrayscale lang to nung bot na icon
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                bot.setColorFilter(new ColorMatrixColorFilter(matrix));
            }
        });

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(P_Lesson_Words[all_ctr], "raw", getPackageName());
                ai = MediaPlayer.create(TipaklongLanggam.this, sound);
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
                    click++;
                }
                else{
                    speechRecognizer.stopListening();
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


    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
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


    // TRY NEW ALGORITHM
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
            ai = MediaPlayer.create(TipaklongLanggam.this, R.raw.response_0_to_49);
            ai.start();
        }
        else if(val >= 0.5 && val <= 0.99){
            ai = MediaPlayer.create(TipaklongLanggam.this, R.raw.response_50_to_69);
            ai.start();
        }
        else if(val ==1.0){
            ai = MediaPlayer.create(TipaklongLanggam.this, R.raw.response_70_to_100);
            ai.start();
        }

    }
}