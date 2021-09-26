package com.example.biskwit;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

public class SignUp_Form extends AppCompatActivity {
    Button SignUp,Login;
    public EditText Email, Password, Confirm_Password, Parents_Instructor, Name_Child, Age, Birthdate, Severity_Level;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        SignUp = (Button) findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Created_Account_Message();
            }
        });
        Login = (Button) findViewById(R.id.go_back_login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go_Login();
            }
        });

    }
    //maglalabas ng message ito kapag pinindot nya ung signup button
    public void Created_Account_Message()
    {
        //ung DialogueMessage class to wag idelete
        DialogueMessage exampleDialog = new DialogueMessage();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }
    //babalik ito sa login screen
    public void Go_Login()
    {
        intent = new Intent(SignUp_Form.this, MainActivity.class);
        startActivity(intent);
    }
}
