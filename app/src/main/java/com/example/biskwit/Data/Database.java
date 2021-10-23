package com.example.biskwit.Data;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database {
    public boolean login(String email, String pass) {

        final boolean[] log = new boolean[1];
        Context context = null;

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<LoginResponseModel> login = networkService.login(email,pass);
        login.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                LoginResponseModel responseBody = response.body();
                if (responseBody != null) {
                    if (responseBody.getSuccess().equals("1")) {
                        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(Constants.KEY_ISE_LOGGED_IN, true);
                        //editor.putString(Constants.KEY_USERNAME, responseBody.getUserDetailObject().getUserDetails().get(0).getFirstName() + " " + responseBody.getUserDetailObject().getUserDetails().get(0).getLastName());
                        /*editor.putString(Constants.KEY_LASTNAME, responseBody.getUserDetailObject().getUserDetails().get(0).getLastName());*/
                        editor.putString(Constants.KEY_EMAIL, responseBody.getUserDetailObject().getUserDetails().get(0).getEmail());
                        editor.apply();
                        //Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        //finish();
                        log[0] = true;
                    } else {
                        log[0] = false;
                        //Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                log[0] = false;
            }
        });

        return log[0];
    }
}
