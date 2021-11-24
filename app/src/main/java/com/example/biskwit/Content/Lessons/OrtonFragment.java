package com.example.biskwit.Content.Lessons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biskwit.Content.EasyFragment;
import com.example.biskwit.Content.Lessons.OrtonActivities.Blending;
import com.example.biskwit.Content.Lessons.OrtonActivities.Pagbabaybay;
import com.example.biskwit.Content.Lessons.OrtonActivities.Phonemic.PhonemicAct;
import com.example.biskwit.Content.Lessons.OrtonActivities.Sight;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentOrtonBinding;


public class OrtonFragment extends Fragment {

    FragmentOrtonBinding binding;
    Intent intent;

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id;
    SharedPreferences logger, mpath;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrtonBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        logger = getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        mpath = getContext().getSharedPreferences("Mastery" + id, Context.MODE_PRIVATE);
        editor = mpath.edit();

        binding.Phoenimic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), PhonemicAct.class);
                startActivity(intent);
            }
        });

        if (mpath.contains("PhonemicLocked")){
            //set to grayscale
            //magtoast
        } else {
            binding.Sight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), Sight.class);
                    startActivity(intent);
                }
            });
        }

        if (mpath.contains("PhonemicLocked") || mpath.contains("SightLocked")){
            //set to grayscale
            //magtoast
        } else {
            binding.Blending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), Blending.class);
                    startActivity(intent);
                }
            });
        }

        if (mpath.contains("PhonemicLocked") || mpath.contains("SightLocked") || mpath.contains("BlendingLocked")){
            //set to grayscale
            //magtoast
        } else {
            binding.Pagbabaybay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), Pagbabaybay.class);
                    startActivity(intent);
                }
            });
        }

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