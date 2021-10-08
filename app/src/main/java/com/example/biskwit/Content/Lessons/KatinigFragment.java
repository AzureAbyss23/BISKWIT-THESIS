package com.example.biskwit.Content.Lessons;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biskwit.Content.Lessons.PatinigActivities.KatinigLesson1;
import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigChoices1;
import com.example.biskwit.Content.Lessons.PatinigActivities.PatinigLesson1;
import com.example.biskwit.databinding.FragmentKatinigBinding;
import com.example.biskwit.databinding.FragmentPatinigBinding;


public class KatinigFragment extends Fragment {

    FragmentKatinigBinding binding;
        Intent intent;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentKatinigBinding.inflate(getLayoutInflater());
            return binding.getRoot(); // for the drawer
        }

        public void onViewCreated(View view, Bundle savedInstanceState)
        {
            super.onViewCreated(view, savedInstanceState);

            binding.Katinig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getContext(), KatinigLesson1.class);
                    startActivity(intent);
                }
            });

            binding.Katinig2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getContext(), KatinigLesson1.class);
                    startActivity(intent);
                }
            });

            binding.Katinig3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getContext(), KatinigLesson1.class);
                    startActivity(intent);
                }
            });

            binding.LarAtSal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(getContext(), KatinigLesson1.class);
                    startActivity(intent);
                }
            });

        }

        // need ito somehow para di magkabuhol buhol yung navigation thingy niya
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

}