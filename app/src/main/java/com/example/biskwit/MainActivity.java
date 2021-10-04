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
    DBHelper DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new DBHelper(this);

        LoginButton = (Button) findViewById(R.id.login);

        CreateAccButton = (Button) findViewById(R.id.create_account);

        User = findViewById(R.id.email);
        Pass = findViewById(R.id.password);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = User.getText().toString();
                String Password = Pass.getText().toString();

                Boolean checklogindata = DB.login(Email, Password);
                if(checklogindata==true) {
                    intent = new Intent(MainActivity.this, MainNavMenu.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();


            }
        });
    }


    //Papuntang Terms.java ito;
    public void Create_Account(View v)
    {
        intent = new Intent(MainActivity.this, Terms.class);
        startActivity(intent);
    }




}
