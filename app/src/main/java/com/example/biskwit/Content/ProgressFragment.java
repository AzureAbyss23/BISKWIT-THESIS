package com.example.biskwit.Content;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.biskwit.Content.Progress.Progress_Easy;
import com.example.biskwit.Content.Progress.Progress_Hard;
import com.example.biskwit.MainDrawer.StartFragment;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentProgressBinding;


public class ProgressFragment extends Fragment {

    FragmentProgressBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProgressBinding.inflate(getLayoutInflater());

        container.removeAllViews();

        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        binding.Lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Progress_Easy.class);
                startActivity(intent);
            }
        });

        binding.Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Progress_Hard.class);
                startActivity(intent);
            }
        });

        binding.Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Progress_Hard.class);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentBack = new StartFragment();
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