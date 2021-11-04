package com.example.biskwit.Content.Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Progress_Aralin extends AppCompatActivity {

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id = 0;
    ProgressDialog progressDialog;

    TextView score_alphabet,score_phonemic,score_sight,score_blending,score_pagbabaybay;
    TextView score_days,score_years,score_synonymous,score_opposite,score_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_aralin);

        score_alphabet = findViewById(R.id.alphabetscore);
        score_phonemic = findViewById(R.id.phonemicscore);
        score_sight = findViewById(R.id.sightscore);
        score_blending = findViewById(R.id.blendingscore);
        score_pagbabaybay = findViewById(R.id.pagbabaybayscore);

        score_days = findViewById(R.id.Daysscore);
        score_years = findViewById(R.id.Yearsscore);
        score_synonymous = findViewById(R.id.Synonymousscore);
        score_opposite = findViewById(R.id.Oppositescore);
        score_sound = findViewById(R.id.Soundscore);

        getData();

    }

    private void getData() {
        progressDialog = new ProgressDialog(Progress_Aralin.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading lesson...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences logger = getSharedPreferences(filename,Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        String url = "https://biskwitteamdelete.000webhostapp.com/fetch_scores.php?id="+id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Progress_Aralin.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @SuppressLint("SetTextI18n")
    private void showJSONS(String response) {
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();
        ArrayList<String> data3 = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                data.add(collegeData.getString("lessontype"));
                data2.add(collegeData.getString("lessonmode"));
                data3.add(collegeData.getString("score"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!data.isEmpty() && !data2.isEmpty() && !data3.isEmpty()){
            Iterator<String> i1= data.iterator();
            Iterator<String> i2= data2.iterator();
            Iterator<String> i3= data3.iterator();
            while(i1.hasNext() && i2.hasNext() && i3.hasNext()) {
                String type = i1.next();
                String mode = i2.next();
                String sco = i3.next();
                if (type.equals("Alphabet") && mode.equals("ABCD")) {
                    score_alphabet.setText(sco + "%");
                } else if (type.equals("Orton") && mode.equals("Phonemic")) {
                    score_phonemic.setText(sco + "%");
                } else if (type.equals("Orton") && mode.equals("Sight")) {
                    score_sight.setText(sco + "%");
                } else if (type.equals("Orton") && mode.equals("Blending")) {
                    score_blending.setText(sco + "%");
                } else if(type.equals("Orton") && mode.equals("Pagbabaybay")){
                    score_pagbabaybay.setText(sco + "%");
                } else if(type.equals("Alamkoito") && mode.equals("Days")){
                    score_days.setText(sco + "%");
                } else if(type.equals("Alamkoito") && mode.equals("Years")){
                    score_years.setText(sco + "%");
                } else if(type.equals("Alamkoito") && mode.equals("Synonymous")){
                    score_synonymous.setText(sco + "%");
                } else if(type.equals("Alamkoito") && mode.equals("Opposite")){
                    score_opposite.setText(sco + "%");
                } else if(type.equals("Alamkoito") && mode.equals("Sounds")){
                    score_sound.setText(sco + "%");
                }
            }
        } else {
            showToast("No scores yet");
        }

        progressDialog.dismiss();
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

}