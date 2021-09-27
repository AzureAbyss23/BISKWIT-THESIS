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

public class MainActivity extends AppCompatActivity{
    Button LoginButton,CreateAccButton;
    public EditText User, Pass;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Process ng pagpunta sa MainMenu.java
        LoginButton = (Button) findViewById(R.id.login);

        //Process ng pagpunta sa SignUp_Form.java
        CreateAccButton = (Button) findViewById(R.id.create_account);
    }
    //Papuntang MainMenu.java ito;
    public void Login(View v)
    {
        intent = new Intent(MainActivity.this, MainNavMenu.class);
        startActivity(intent);
    }
    //Papuntang SignUp_Form.java ito;
    public void Create_Account(View v)
    {
        intent = new Intent(MainActivity.this, SignUp_Form.class);
        startActivity(intent);
    }
}
