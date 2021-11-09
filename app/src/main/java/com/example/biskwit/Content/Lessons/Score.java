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

import com.example.biskwit.Content.Lessons.KatinigActivities.KatinigChoices1;
import com.example.biskwit.Content.Lessons.KatinigActivities.KatinigChoices2;
import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigChoices1;
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
    String score_save = "";
    String UserScore = "";
    int status = 0;
    double conv_score;
    double average = 0;
    double compute = 0;
    SharedPreferences scores, logger;
    SharedPreferences.Editor editor;

    public static final String filename = "idfetch";
    public static final String filename2 = "scorer";
    public static final String UserID = "userid";

    String REGISTER_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        scores = getSharedPreferences(filename2, Context.MODE_PRIVATE);
        int id = logger.getInt(UserID,0);
        str_id = Integer.toString(id);
        status = getIntent().getIntExtra("Status",0);
        average = getIntent().getIntExtra("Average",0);
        lessontype = getIntent().getStringExtra("LessonType");
        lessonmode = getIntent().getStringExtra("LessonMode");
        letter = getIntent().getStringExtra("Letter");
        if(letter==null) {
            letter = "default";
            UserScore = "userscore"+str_id+lessonmode;
        } else { UserScore = "userscore"+str_id+lessonmode+letter; }
        editor = scores.edit();
        editor.putString(UserScore, score_save);
        editor.apply();
        double s = getIntent().getDoubleExtra("Score",0);
        compute = (s / average) * 100;
        conv_score = Math.round(compute);
        str_score = Double.toString(conv_score);
        String Score = Double.toString(conv_score);
        score = findViewById(R.id.Score);
        next = findViewById(R.id.nextButton);

        REGISTER_URL = "https://biskwitteamdelete.000webhostapp.com/insert_score.php?status="+status+"&lessonmode="+lessonmode+"&letter="+letter;

        score.setText(Score);
        insert(str_id,lessontype,lessonmode,letter,str_score);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lessontype.equals("Patinig") && lessonmode.equals("P_Aralin2")){
                    Intent intent = new Intent(Score.this, PatinigChoices1.class);
                    startActivity(intent);
                } else if(lessontype.equals("Katinig") && lessonmode.equals("K_Aralin2")){
                    Intent intent = new Intent(Score.this, KatinigChoices1.class);
                    startActivity(intent);
                } else if(lessontype.equals("Hiram") && lessonmode.equals("K_Aralin3")){
                    Intent intent = new Intent(Score.this, KatinigChoices2.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Score.this, MainNavMenu.class);
                    startActivity(intent);
                }
                finish();
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