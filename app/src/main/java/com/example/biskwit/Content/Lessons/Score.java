package com.example.biskwit.Content.Lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.Data.Database;
import com.example.biskwit.MainActivity;
import com.example.biskwit.MainNavMenu;
import com.example.biskwit.R;
import com.example.biskwit.SignUp_Form;

import java.util.HashMap;

public class Score extends AppCompatActivity {

    TextView score;
    ImageView next;
    String str_id = "";
    String str_score = "";
    String lessontype = "";
    String lessonmode = "";
    String letter = "";
    double average = 0;
    double compute = 0;

    private static final String REGISTER_URL = "https://biskwitteamdelete.000webhostapp.com/insert_score.php";
    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        str_id = Integer.toString(id);
        average = getIntent().getIntExtra("Average",0);
        lessontype = getIntent().getStringExtra("LessonType");
        lessonmode = getIntent().getStringExtra("LessonMode");
        letter = getIntent().getStringExtra("Letter");
        if(letter==null) letter = "default";
        double s = getIntent().getDoubleExtra("Score",0);
        compute = (s / average) * 100;
        str_score = Double.toString(compute);
        String Score = Double.toString(compute);
        score = findViewById(R.id.Score);
        next = findViewById(R.id.nextButton);

        score.setText(Score);
        insert(str_id,lessontype,lessonmode,letter,str_score);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, MainNavMenu.class);
                startActivity(intent);
            }
        });
    }

    private void insert(String id,String lessontype,String lessonmode,String letter,String score) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Database ruc = new Database();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Score.this, "Saving your score...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("id", params[0]);
                data.put("lessontype", params[1]);
                data.put("lessonmode", params[2]);
                data.put("letter",params[3]);
                data.put("score", params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(id,lessontype,lessonmode,letter,score);
    }
}