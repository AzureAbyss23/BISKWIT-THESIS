package com.example.biskwit;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.database.Cursor;
import android.widget.Toast;

import com.example.biskwit.Data.NetworkClient;
import com.example.biskwit.Data.NetworkService;
import com.example.biskwit.Data.RegistrationResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //DB = new DBHelper(this);

        SignUp = findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (Name_Child.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter first name", Toast.LENGTH_SHORT).show();
                } else if (Email.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter email", Toast.LENGTH_SHORT).show();
                } else if (Password.getText().toString().equals("")) {
                    Toast.makeText(SignUp_Form.this, "Enter password", Toast.LENGTH_SHORT).show();
                }/* else if (!emailValidator(inputemail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();

                } */ else {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", Email.getText().toString());
                    params.put("password", Password.getText().toString());
                    params.put("parent", Parents_Instructor.getText().toString());
                    params.put("child", Name_Child.getText().toString());
                    params.put("age", Age.getText().toString());
                    params.put("birthday", Birthdate.getText().toString());
                    params.put("severity", Severity_Level.getText().toString());
                    register(params);
                }

            }

        });

    }

    private void register(HashMap<String, String> params) {

        final ProgressDialog progressDialog = new ProgressDialog(SignUp_Form.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating your account...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<RegistrationResponseModel> registerCall = networkService.register(params);
        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
                RegistrationResponseModel responseBody = response.body();
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        Toast.makeText(SignUp_Form.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp_Form.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUp_Form.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponseModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
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
