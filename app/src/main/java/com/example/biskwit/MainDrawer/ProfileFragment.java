package com.example.biskwit.MainDrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.biskwit.Data.Constants;
import com.example.biskwit.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // dito iniinitialize yung layout niya
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*binding.Child.setText(child);
        binding.Age.setText(age);
        binding.Bday.setText(bday);
        binding.Severity.setText(severity);
        binding.Parent.setText(parent);*/

        return root;
    }

    //wala pang logic sa profile pero pag may database na huhugutin siya don so eventually need na


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}