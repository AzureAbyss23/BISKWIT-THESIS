package com.example.biskwit;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    Button LoginButton,CreateAccButton;
    public EditText User, Pass;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Process ng pagpunta sa MainMenu.java
        LoginButton = (Button) findViewById(R.id.login);
        LoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });

        //Process ng pagpunta sa SignUp_Form.java
        CreateAccButton = (Button) findViewById(R.id.create_account);
        CreateAccButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Create_Account();
            }
        });
    }
    //Papuntang MainMenu.java ito;
    public void Login()
    {
        intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
    }
    //Papuntang SignUp_Form.java ito;
    public void Create_Account()
    {
        intent = new Intent(MainActivity.this, SignUp_Form.class);
        startActivity(intent);
    }
}
