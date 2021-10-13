package com.example.biskwit.Content.Lessons.KatinigActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biskwit.DBHelper;
import com.example.biskwit.R;

import java.util.ArrayList;
import java.util.Locale;

public class KatinigLesson1 extends AppCompatActivity {

    TextView txtresult,txtword;
    ImageView next;
    String word = "";
    DBHelper DB;
    Cursor c;
    String[] P_Lesson_Words = {"bahay","baboy","baso","baka","bintana","bulak","buhok","buko","bawang"};
    StringBuffer buff;
    int all_ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katinig_lesson1);

        txtresult = (TextView) findViewById(R.id.result);
        txtword = (TextView) findViewById(R.id.Word);
        next = findViewById(R.id.nextButton);

        DB = new DBHelper(this);

        String letter = getIntent().getStringExtra("letter");

        c = DB.getlessondata(letter);

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

        txtword.setText(P_Lesson_Words[all_ctr]);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++all_ctr;
                txtword.setText(P_Lesson_Words[all_ctr]);
                txtresult.setText("Speak Now");
            }
        });

    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    word = result.get(0);
                    if(word.equals(P_Lesson_Words[all_ctr])){
                        txtresult.setText("CORRECT WORD!");
                    }
                    else{
                        txtresult.setText("Try again!");
                    }
                }
                break;
        }
    }
}