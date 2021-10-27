package com.example.biskwit.Content.Lessons.PatinigActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.biskwit.R;

public class PatinigChoices1 extends AppCompatActivity {

    Intent intent;
    ImageButton LetterA, LetterE, LetterI, LetterO, LetterU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patinig_choices1);

        LetterA = findViewById(R.id.LetterA);
        LetterE = findViewById(R.id.LetterE);
        LetterI = findViewById(R.id.LetterI);
        LetterO = findViewById(R.id.LetterO);
        LetterU = findViewById(R.id.LetterU);

        LetterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PatinigChoices1.this, PatinigLesson2.class);
                intent.putExtra("letter","A");
                startActivity(intent);
            }
        });

        LetterE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PatinigChoices1.this, PatinigLesson2.class);
                intent.putExtra("letter","E");
                startActivity(intent);
            }
        });

        LetterI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PatinigChoices1.this, PatinigLesson2.class);
                intent.putExtra("letter","I");
                startActivity(intent);
            }
        });

        LetterO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PatinigChoices1.this, PatinigLesson2.class);
                intent.putExtra("letter","O");
                startActivity(intent);
            }
        });

        LetterU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PatinigChoices1.this, PatinigLesson2.class);
                intent.putExtra("letter","U");
                startActivity(intent);
            }
        });
    }
}