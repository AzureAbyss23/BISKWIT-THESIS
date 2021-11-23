package com.example.biskwit.Content.Lessons;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biskwit.Content.EasyFragment;
import com.example.biskwit.Content.Lessons.KatinigActivities.KatinigChoices1;
import com.example.biskwit.Content.Lessons.AbakadaActivities.KatinigLesson1;
import com.example.biskwit.Content.Lessons.KatinigActivities.KatinigChoices2;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentKatinigBinding;


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
            binding.KatinigAralin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), KatinigLesson1.class);
                    startActivity(intent);
                }
            });

            binding.KatinigAralin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), KatinigChoices1.class);
                    startActivity(intent);
                }
            });

            binding.KatinigAralin3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), KatinigChoices2.class);
                    startActivity(intent);
                }
            });

            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragmentBack = new EasyFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentBack);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
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