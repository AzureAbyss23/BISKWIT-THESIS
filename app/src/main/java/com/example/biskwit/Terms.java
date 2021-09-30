package com.example.biskwit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class Terms extends AppCompatActivity {

    Button Continue;
    Intent intent;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);

        //eto yung part na ginagawa niyang unclickable yung continue pag di pa nag aagree
    Continue = findViewById(R.id.Continue);
    Continue.setClickable(false);
    Continue.setBackgroundColor(Color.GRAY);
    }

    public void GoToSignUp(View v){
        intent = new Intent(Terms.this, SignUp_Form.class);
        startActivity(intent);
    }

    // code eto para sa check box para tignan kung nacheck na siya, pag checked na clickable na din si continue
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        Continue = findViewById(R.id.Continue);
        switch(view.getId()) {
            case R.id.Agree:
                if (checked){
                    Continue.setClickable(true);
                    Continue.setBackgroundColor(Color.BLUE);
                }

                else{
                    Continue.setClickable(false);
                    Continue.setBackgroundColor(Color.GRAY);
                }

                break;

        }
    }

}
