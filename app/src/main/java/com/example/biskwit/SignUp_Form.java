package com.example.biskwit;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.database.Cursor;
import android.widget.Toast;

public class SignUp_Form extends AppCompatActivity {
    Button SignUp,Login;
    public EditText Email, Password, Confirm_Password, Parents_Instructor, Name_Child, Age, Birthdate, Severity_Level;
    Intent intent;
    DBHelper DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        // eto yung sa sign up forms natin na buttons
        //di pa nakaset yung igeget niya yung input details ng user

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Confirm_Password = findViewById(R.id.confirm_password);
        Parents_Instructor = findViewById(R.id.parent_or_instructor);
        Name_Child = findViewById(R.id.child_name);
        Age = findViewById(R.id.age);
        Birthdate = findViewById(R.id.birthday);
        Severity_Level = findViewById(R.id.level_severity);

        DB = new DBHelper(this);

        SignUp = (Button) findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String confirmpass = Confirm_Password.getText().toString();
                String parent_instructor = Parents_Instructor.getText().toString();
                String namechild = Name_Child.getText().toString();
                int age = Integer.parseInt(Age.getText().toString());
                String birthdate = Birthdate.getText().toString();
                String sever_level = Severity_Level.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(email, password, parent_instructor
                        , namechild, age, birthdate, sever_level);
                if(checkinsertdata==true)
                    Created_Account_Message();
                else
                    Toast.makeText(SignUp_Form.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();

                Go_Login();
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
