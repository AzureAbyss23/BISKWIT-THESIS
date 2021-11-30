package com.example.biskwit.Content.Progress;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Progress_Easy extends AppCompatActivity {

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id = 0;
    ProgressDialog progressDialog;

    TextView score_alphabet;
    TextView score_p_aralin1, score_k_aralin1;

    Button score_p_aralin2, score_k_aralin2, score_k_aralin3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_easy);

        score_alphabet = findViewById(R.id.alphabetscore);

        score_p_aralin1 = findViewById(R.id.patinigscore);
        score_p_aralin2 = findViewById(R.id.patinig2score);
        score_k_aralin1 = findViewById(R.id.katinig1score);
        score_k_aralin2 = findViewById(R.id.katinig2score);
        score_k_aralin3 = findViewById(R.id.katinig3score);

        getData();

        score_p_aralin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Progress_Easy.this, ProgressPatinig.class);
                startActivity(intent);
            }
        });
        score_k_aralin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Progress_Easy.this, ProgressKatinig.class);
                startActivity(intent);
            }
        });
        score_k_aralin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Progress_Easy.this, ProgressHiram.class);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        progressDialog = new ProgressDialog(Progress_Easy.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading your scores...");
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
                        Toast.makeText(Progress_Easy.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                } else if(type.equals("Patinig") && mode.equals("P_Aralin1")){
                    score_p_aralin1.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin1")){
                    score_k_aralin1.setText(sco + "%");
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