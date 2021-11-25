package com.example.biskwit.Content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.biskwit.Content.Story.PabulaFragment;
import com.example.biskwit.Content.Story.TulaFragment;
import com.example.biskwit.MainDrawer.StartFragment;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentHardBinding;

public class HardFragment extends Fragment {

    FragmentHardBinding binding;

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id;
    SharedPreferences logger, mpath;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHardBinding.inflate(getLayoutInflater());

        container.removeAllViews();

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        logger = getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
        id = logger.getInt(UserID,0);

        mpath = getContext().getSharedPreferences("Mastery" + id, Context.MODE_PRIVATE);
        editor = mpath.edit();

        binding.Tula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentTula = new TulaFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentTula);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if(mpath.contains("MagdasalLocked") || mpath.contains("MaglaroLocked") || mpath.contains("ParuparoRosasLocked")){
            binding.Pabula.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
            binding.Pabula.setImageResource(R.drawable.buttonpabulalock);
        } else {
            binding.Pabula.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragmentPabula = new PabulaFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu, fragmentPabula);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

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