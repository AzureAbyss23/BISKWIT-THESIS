package com.example.biskwit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Logout extends Fragment {

    public static final String filename = "logger";
    public static final String filename2 = "idfetch";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        new AlertDialog.Builder(getContext())
                .setTitle("Logging you out...")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences logger = getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
                        SharedPreferences IDFetch = getContext().getSharedPreferences(filename2, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = logger.edit();
                        SharedPreferences.Editor editor2 = IDFetch.edit();
                        editor.clear(); editor.apply();
                        editor2.clear(); editor2.apply();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).create().show();

        return rootView;
    }

}