package com.example.biskwit.Content.Lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biskwit.MainNavMenu;
import com.example.biskwit.R;

public class Score extends AppCompatActivity {

    TextView score;
    ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        double s = getIntent().getDoubleExtra("Score",0);
        String Score = Double.toString(s);
        score = findViewById(R.id.Score);
        next = findViewById(R.id.nextButton);

        score.setText(Score);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, MainNavMenu.class);
                startActivity(intent);
            }
        });
    }
}