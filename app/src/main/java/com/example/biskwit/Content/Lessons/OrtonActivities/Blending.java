package com.example.biskwit.Content.Lessons.OrtonActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Blending extends AppCompatActivity {

    Button ch1,ch2,ch3;
    String[] words;
    String[][] choice = {{"so","pa","te"},{"nak","kay","raw"},{"lis","kis","dis"},
            {"llisi","nergy","nsayo"},{"saw","law","sda"},{"saw","law","sda"},
            {"po","lo","so"},{"po","lo","so"},{"lo","od","so"},{"sa","bo","lo"}};
    String spaces = "";
    int all_ctr = 0, id = 0;
    double score = 0;
    TextView txtword;
    ImageView bot,bot2,wordimg;
    MediaPlayer ai;
    ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blending);

        ch1 = findViewById(R.id.Choice1);
        ch2 = findViewById(R.id.Choice2);
        ch3 = findViewById(R.id.Choice3);
        txtword = findViewById(R.id.Word);
        bot = findViewById(R.id.Bot);
        bot2 = findViewById(R.id.Bot2);
        wordimg = findViewById(R.id.WordImg);
        progressDialog = new ProgressDialog(Blending.this);

        getData();

        ch1.setText(choice[all_ctr][0]);
        ch2.setText(choice[all_ctr][1]);
        ch3.setText(choice[all_ctr][2]);

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                char ltr = txtword.getText().toString().charAt(0);
                String wrd = ch1.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                char ltr = txtword.getText().toString().charAt(0);
                String wrd = ch2.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                char ltr = txtword.getText().toString().charAt(0);
                String wrd = ch3.getText().toString();
                String Rword = ltr+wrd;
                result(Rword);
            }
        });

        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                Resources res = getResources();
                int sound = res.getIdentifier(words[all_ctr].toLowerCase(), "raw", getPackageName());
                ai = MediaPlayer.create(Blending.this, sound);
                ai.start();
            }
        });

        bot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlaying();
                ai = MediaPlayer.create(Blending.this, R.raw.kab2_p3);
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

    public int setImg(){
        Resources res = this.getResources();
        int resID;
        return resID = res.getIdentifier("patinig_"+words[all_ctr].toLowerCase(), "drawable", this.getPackageName());
    }

    public void result(String s){
        if(s.equals(words[all_ctr])){
            showToast("CORRECT!");
            score += 5;
        }
        else{
            showToast("WRONG!");
        }

        if(all_ctr < (words.length - 1)) {
            ++all_ctr;
            for(int i = 0;i<(words[all_ctr].length()-1);i++){ spaces += "_ "; }
            txtword.setText(words[all_ctr].charAt(0)+spaces);
            spaces = "";
            id = setImg();
            wordimg.setImageResource(id);
            ch1.setText(choice[all_ctr][0]);
            ch2.setText(choice[all_ctr][1]);
            ch3.setText(choice[all_ctr][2]);
        } else {
            Intent intent = new Intent(Blending.this, Score.class);
            intent.putExtra("Score",score);
            startActivity(intent);
        }
    }

    private void getData() {

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_blending.php";

        StringRequest stringRequest = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Blending.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
            words = new String[data.size()];
            words = data.toArray(words);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!words[0].equals("")){
            for(int i = 0;i<(words[all_ctr].length()-1);i++){ spaces += "_ "; }
            txtword.setText(words[all_ctr].charAt(0)+spaces);
            spaces = "";
            id = setImg();
            wordimg.setImageResource(id);
            progressDialog.dismiss();
        } else {
            Toast.makeText(Blending.this, "No data", Toast.LENGTH_LONG).show();
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
                        Blending.super.onBackPressed();
                        stopPlaying();
                    }
                }).create().show();
    }
}