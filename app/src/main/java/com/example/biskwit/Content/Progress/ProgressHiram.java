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

public class ProgressHiram extends AppCompatActivity {
    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id = 0;
    ProgressDialog progressDialog;

    TextView score_ck, score_f, score_j, score_q, score_v,
             score_xk, score_xs, score_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_hiram);

        score_ck = findViewById(R.id.Hiram_CK_Score);
        score_f = findViewById(R.id.Hiram_F_Score);
        score_j = findViewById(R.id.Hiram_J_Score);
        score_q = findViewById(R.id.Hiram_Q_Score);
        score_v = findViewById(R.id.Hiram_V_Score);
        score_xk = findViewById(R.id.Hiram_XK_Score);
        score_xs = findViewById(R.id.Hiram_XS_Score);
        score_z = findViewById(R.id.Hiram_Z_Score);

        getData();

    }
    private void getData() {
        progressDialog = new ProgressDialog(ProgressHiram.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading your scores...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
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
                        Toast.makeText(ProgressHiram.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
        ArrayList<String> data4 = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            int length = result.length();
            for(int i = 0; i < length; i++) {
                JSONObject collegeData = result.getJSONObject(i);
                data.add(collegeData.getString("lessontype"));
                data2.add(collegeData.getString("lessonmode"));
                data3.add(collegeData.getString("letter"));
                data4.add(collegeData.getString("score"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(!data.isEmpty() && !data2.isEmpty() && !data3.isEmpty() && !data4.isEmpty()){
            Iterator<String> i1= data.iterator();
            Iterator<String> i2= data2.iterator();
            Iterator<String> i3= data3.iterator();
            Iterator<String> i4= data4.iterator();
            while(i1.hasNext() && i2.hasNext() && i3.hasNext() && i4.hasNext()) {
                String type = i1.next();
                String mode = i2.next();
                String ltr = i3.next();
                String sco = i4.next();
                if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("CK")) {
                    score_ck.setText(sco + "%");
                } else if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("F")) {
                    score_f.setText(sco + "%");
                } else if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("J")) {
                    score_j.setText(sco + "%");
                } else if(type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("Q")){
                    score_q.setText(sco + "%");
                } else if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("V")) {
                    score_v.setText(sco + "%");
                } else if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("XK")) {
                    score_xk.setText(sco + "%");
                } else if (type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("XS")) {
                    score_xs.setText(sco + "%");
                } else if(type.equals("Hiram") && mode.equals("K_Aralin3") && ltr.equals("Z")){
                    score_z.setText(sco + "%");
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