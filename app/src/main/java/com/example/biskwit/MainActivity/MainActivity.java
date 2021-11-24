package com.example.biskwit.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biskwit.Data.Constants;
import com.example.biskwit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
    Button LoginButton,CreateAccButton;
    public EditText User, Pass;
    CheckBox remember;
    Intent intent;
    String Email = "";
    String Password = "";
    ProgressDialog progressDialog;
    SharedPreferences logger, IDFetch, mpath;
    SharedPreferences.Editor editor,editor2, editor3;

    AssetFileDescriptor asd;
    public static final String filename = "logger";
    public static final String filename2 = "idfetch";
    public static final String UserID = "userid";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = getSharedPreferences(filename, Context.MODE_PRIVATE);
        IDFetch = getSharedPreferences(filename2, Context.MODE_PRIVATE);

        if(logger.contains(UserID)){
            Intent intent = new Intent(MainActivity.this, MainNavMenu.class);
            startActivity(intent);
            finish();
        }

        LoginButton = (Button) findViewById(R.id.login);

        CreateAccButton = (Button) findViewById(R.id.create_account);

        User = findViewById(R.id.email);
        Pass = findViewById(R.id.password);
        remember = findViewById(R.id.Remember);
        progressDialog = new ProgressDialog(MainActivity.this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = User.getText().toString().trim();
                Password = Pass.getText().toString().trim();

                if (Email.equals("")) {
                    Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(Email)) {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (Password.equals("")) {
                    Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Logging you in...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    getData();
                }
            }
        });

    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private void getData() {

        String url = "https://biskwitteamdelete.000webhostapp.com/fetchdata.php?email="+Email+"&pass="+Password;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
        int id = 0;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            id = collegeData.getInt("id");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(id>0){
            mpath = getSharedPreferences("Mastery" + id, Context.MODE_PRIVATE);
            if(remember.isChecked()) {
                editor = logger.edit();
                editor.putInt(UserID, id);
                editor.commit();
            }
            editor2 = IDFetch.edit();
            editor2.putInt(UserID, id);
            editor2.commit();
            if(mpath.contains("Locked")){
                System.out.println("Go");
            } else MPath(id);
            progressDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, MainNavMenu.class);
            startActivity(intent);
            finish();
        } else {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Wrong email or Password", Toast.LENGTH_LONG).show();
        }
    }

    public void MPath(int id) {
        mpath = getSharedPreferences("Mastery" + id, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = mpath.edit();
        editor3.putInt("Locked",1);
        editor3.putInt("Abakada1Locked", 1);
        editor3.putInt("Abakada2Locked", 1);
        editor3.putInt("Abakada3Locked", 1);
        editor3.putInt("PatinigLocked", 1);
        editor3.putInt("KatinigLocked", 1);
        editor3.putInt("HiramLocked", 1);
        editor3.putInt("PhonemicLocked", 1);
        editor3.putInt("SightLocked", 1);
        editor3.putInt("BlendingLocked", 1);
        editor3.putInt("PagbabaybayLocked", 1);
        editor3.putInt("DaysLocked", 1);
        editor3.putInt("YearsLocked", 1);
        editor3.putInt("OppositeLocked", 1);
        editor3.putInt("SynonymousLocked", 1);
        editor3.putInt("SoundsLocked", 1);
        editor3.apply();
    }

    //Papuntang Terms.java ito;
    public void Create_Account(View v)
    {
        intent = new Intent(MainActivity.this, Terms.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Leaving already?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }).create().show();
    }
}
