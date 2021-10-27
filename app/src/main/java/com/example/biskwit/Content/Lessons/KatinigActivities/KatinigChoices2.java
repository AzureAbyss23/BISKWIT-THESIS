package com.example.biskwit.Content.Lessons.KatinigActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.biskwit.R;

public class KatinigChoices2 extends AppCompatActivity {

    Intent intent;
    ImageButton LetterCK, LetterCS, LetterF, LetterJ, LetterQ, LetterV, LetterW,
                LetterXS , LetterXK, LetterZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katinig_choices2);

        LetterCK = findViewById(R.id.LetterCK);
        LetterCS = findViewById(R.id.LetterCS);
        LetterF = findViewById(R.id.LetterF);
        LetterJ = findViewById(R.id.LetterJ);
        LetterQ = findViewById(R.id.LetterQ);
        LetterV = findViewById(R.id.LetterV);
        LetterW = findViewById(R.id.LetterW);
        LetterXK = findViewById(R.id.LetterXK);
        LetterXS = findViewById(R.id.LetterXS);
        LetterZ = findViewById(R.id.LetterZ);


        LetterCK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","C = K");
                startActivity(intent);
            }
        });

        LetterCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","C = S");
                startActivity(intent);
            }
        });

        LetterF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","F");
                startActivity(intent);
            }
        });

        LetterJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","J");
                startActivity(intent);
            }
        });

        LetterQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","Q");
                startActivity(intent);
            }
        });

        LetterV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","V");
                startActivity(intent);
            }
        });

        LetterW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","W");
                startActivity(intent);
            }
        });

        LetterXK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","X = EKS");
                startActivity(intent);
            }
        });

        LetterXS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","X = S");
                startActivity(intent);
            }
        });

        LetterZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices2.this, KatinigLesson3.class);
                intent.putExtra("letter","Z");
                startActivity(intent);
            }
        });


    }
}