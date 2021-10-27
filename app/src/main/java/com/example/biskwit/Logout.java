package com.example.biskwit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Logout extends Fragment {

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // dito iniinitialize yung layout ng home and navbar

        container.removeAllViews();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getContext());
        // babalik lang siya sa Login screen
        logout();

        return rootView;
    }

    public void logout(){
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Logging you out...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        progressDialog.dismiss();
    }

}