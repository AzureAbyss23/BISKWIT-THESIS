package com.example.biskwit.Content.Lessons.KatinigActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.biskwit.R;

public class KatinigChoices1 extends AppCompatActivity {

    Intent intent;
    ImageButton LetterB, LetterD, LetterG, LetterH, LetterK, LetterL, LetterM, LetterN,
                LetterNG , LetterP, LetterR, LetterS, LetterT, LetterY ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katinig_choices1);

        LetterB = findViewById(R.id.LetterB);
        LetterD = findViewById(R.id.LetterD);
        LetterG = findViewById(R.id.LetterG);
        LetterH = findViewById(R.id.LetterH);
        LetterK = findViewById(R.id.LetterK);
        LetterL = findViewById(R.id.LetterL);
        LetterM = findViewById(R.id.LetterM);
        LetterN = findViewById(R.id.LetterN);
        LetterNG = findViewById(R.id.LetterNG);
        LetterP = findViewById(R.id.LetterP);
        LetterR = findViewById(R.id.LetterR);
        LetterS = findViewById(R.id.LetterS);
        LetterT = findViewById(R.id.LetterT);
        LetterY = findViewById(R.id.LetterY);

        LetterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","B");
                startActivity(intent);
            }
        });

        LetterD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","D");
                startActivity(intent);
            }
        });

        LetterG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","G");
                startActivity(intent);
            }
        });

        LetterH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","H");
                startActivity(intent);
            }
        });

        LetterK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","K");
                startActivity(intent);
            }
        });

        LetterL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","L");
                startActivity(intent);
            }
        });

        LetterM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","M");
                startActivity(intent);
            }
        });

        LetterN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","N");
                startActivity(intent);
            }
        });

        LetterNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","NG");
                startActivity(intent);
            }
        });

        LetterP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","P");
                startActivity(intent);
            }
        });

        LetterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","R");
                startActivity(intent);
            }
        });

        LetterS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","S");
                startActivity(intent);
            }
        });

        LetterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","T");
                startActivity(intent);
            }
        });

        LetterY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(KatinigChoices1.this, KatinigLesson2.class);
                intent.putExtra("letter","Y");
                startActivity(intent);
            }
        });

    }
}