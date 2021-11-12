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

public class ProgressKatinig extends AppCompatActivity {
    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id = 0;
    ProgressDialog progressDialog;

    TextView score_b, score_d, score_g, score_h, score_k, score_l, score_m,
             score_n, score_ng, score_p, score_r, score_s, score_t, score_w, score_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_katinig);

        score_b = findViewById(R.id.Katinig_B_Score);
        score_d = findViewById(R.id.Katinig_D_Score);
        score_g = findViewById(R.id.Katinig_G_Score);
        score_h = findViewById(R.id.Katinig_H_Score);
        score_k = findViewById(R.id.Katinig_K_Score);
        score_l = findViewById(R.id.Katinig_L_Score);
        score_m = findViewById(R.id.Katinig_M_Score);
        score_n = findViewById(R.id.Katinig_N_Score);
        score_ng = findViewById(R.id.Katinig_NG_Score);
        score_p = findViewById(R.id.Katinig_P_Score);
        score_r = findViewById(R.id.Katinig_R_Score);
        score_s = findViewById(R.id.Katinig_S_Score);
        score_t = findViewById(R.id.Katinig_T_Score);
        score_w = findViewById(R.id.Katinig_W_Score);
        score_y = findViewById(R.id.Katinig_Y_Score);

        getData();
    }

    private void getData() {
        progressDialog = new ProgressDialog(ProgressKatinig.this);
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
                        Toast.makeText(ProgressKatinig.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("B")) {
                    score_b.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("D")) {
                    score_d.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("G")) {
                    score_g.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("H")) {
                    score_h.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("K")){
                    score_k.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("L")) {
                    score_l.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("M")) {
                    score_m.setText(sco + "%");
                } else if (type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("N")) {
                    score_n.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("NG")){
                    score_ng.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("P")){
                    score_p.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("R")){
                    score_r.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("S")){
                    score_s.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("T")){
                    score_t.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("W")){
                    score_w.setText(sco + "%");
                } else if(type.equals("Katinig") && mode.equals("K_Aralin2") && ltr.equals("Y")){
                    score_y.setText(sco + "%");
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