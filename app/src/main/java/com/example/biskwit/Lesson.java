package com.example.biskwit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lesson extends AppCompatActivity{
    Button Patinig,Katinig,LarAtSal;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson);

        Patinig = (Button) findViewById(R.id.Patinig);

        Katinig = (Button) findViewById(R.id.Katinig);

        LarAtSal = (Button) findViewById(R.id.LarAtSal);
    }

    /*public void Login(View v)
    {
        intent = new Intent(Lesson.this, MainMenu.class);
        startActivity(intent);
    }*/
}
