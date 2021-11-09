package com.example.biskwit.MainActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biskwit.R;

public class Terms extends AppCompatActivity {

    Button Continue, Read;
    Intent intent;
    CheckBox Agree,Disagree;
    TextView Terms;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);

        //eto yung part na ginagawa niyang unclickable yung continue pag di pa nag aagree
        Continue = findViewById(R.id.Continue);
        Continue.setClickable(false);
        Continue.setBackgroundColor(Color.GRAY);

        Read = findViewById(R.id.Drop);
        Terms = findViewById(R.id.Terms);
        Agree = findViewById(R.id.Agree);
        Disagree = findViewById(R.id.Disagree);

        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) Terms.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                Terms.setLayoutParams(params);
                View b = findViewById(R.id.Drop);
                b.setVisibility(View.GONE);
            }
        });
    }

    public void GoToSignUp(View v){
        if(Agree.isChecked()) {
            intent = new Intent(Terms.this, SignUp_Form.class);
            startActivity(intent);
        } else {
            intent = new Intent(Terms.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // code eto para sa check box para tignan kung nacheck na siya, pag checked na clickable na din si continue
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        Continue = findViewById(R.id.Continue);
        switch(view.getId()) {
            case R.id.Agree:
                if (checked){
                    Disagree.setChecked(false);
                    Continue.setClickable(true);
                    Continue.setBackgroundColor(Color.parseColor("#1C388F"));
                }
                else{
                    Continue.setClickable(false);
                    Continue.setBackgroundColor(Color.GRAY);
                }
                break;
            case R.id.Disagree:
                if(checked){
                    Agree.setChecked(false);
                    Continue.setClickable(true);
                    Continue.setBackgroundColor(Color.parseColor("#1C388F"));
                }
                else{
                    Continue.setClickable(false);
                    Continue.setBackgroundColor(Color.GRAY);
                }
                break;

        }
    }

}
