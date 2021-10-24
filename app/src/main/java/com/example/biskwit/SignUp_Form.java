package com.example.biskwit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.example.biskwit.Data.Database;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Form extends AppCompatActivity {
    Button SignUp,Login;
    public EditText Email, Password, Confirm_Password, Parents_Instructor, Name_Child, Age, Birthdate, Severity_Level;
    Intent intent;
    private static final String REGISTER_URL = "https://biskwitteamdelete.000webhostapp.com/insertdata.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Confirm_Password = findViewById(R.id.confirm_password);
        Parents_Instructor = findViewById(R.id.parent_or_instructor);
        Name_Child = findViewById(R.id.child_name);
        Age = findViewById(R.id.age);
        Birthdate = findViewById(R.id.birthday);
        Severity_Level = findViewById(R.id.level_severity);

        SignUp = findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (Email.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(Email.getText().toString())) {
                    Toast.makeText(SignUp_Form.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (Password.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!Password.getText().toString().equals(Confirm_Password.getText().toString())) {
                    Toast.makeText(SignUp_Form.this, "Password Does not Match", Toast.LENGTH_SHORT).show();
                }  else if (Parents_Instructor.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Parent/Instructor Name", Toast.LENGTH_SHORT).show();
                } else if (Name_Child.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Child Name", Toast.LENGTH_SHORT).show();
                } else if (Age.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Child Age", Toast.LENGTH_SHORT).show();
                } else if (Birthdate.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Birthday", Toast.LENGTH_SHORT).show();
                } else if (Severity_Level.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter Severity Level", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser();
                }

            }

        });

    }

    private void registerUser() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String parent = Parents_Instructor.getText().toString();
        String child = Name_Child.getText().toString();
        String age = Age.getText().toString();
        String birthday = Birthdate.getText().toString();
        String severity = Severity_Level.getText().toString();

        register(email,password,parent,child,age,birthday,severity);
    }

    private void register(String email,String password,String parent,String child,String age,String birthday,String severity) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Database ruc = new Database();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUp_Form.this, "Creating your account...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    intent = new Intent(SignUp_Form.this, MainActivity.class);
                    startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("email", params[0]);
                data.put("password", params[1]);
                data.put("parent", params[2]);
                data.put("child", params[3]);
                data.put("age", params[4]);
                data.put("birthday", params[5]);
                data.put("severity", params[6]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(email,password,parent,child,age,birthday,severity);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public void Go_Login(View v)
    {
        intent = new Intent(SignUp_Form.this, MainActivity.class);
        startActivity(intent);
    }
}
