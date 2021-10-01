package com.example.biskwit.Content.Lessons;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

            // eto yung code para sa Aralin na button natin
        /*binding.Patinig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentPatinig = new PatinigFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentPatinig);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // eto yung code para sa maikling kwento na button
        binding.Katinig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentStory = new storyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentStory);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        }

        // need ito somehow para di magkabuhol buhol yung navigation thingy niya
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

}