package com.example.biskwit.MainDrawer;

import static com.example.biskwit.MainActivity.MainNavMenu.tapsoundon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.biskwit.Content.EasyFragment;
import com.example.biskwit.Content.NormalFragment;
import com.example.biskwit.Content.ProgressFragment;
import com.example.biskwit.MainActivity.MainNavMenu;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentStartBinding;
import com.example.biskwit.Content.HardFragment;

public class StartFragment extends Fragment {

    FragmentStartBinding binding;
    public static MediaPlayer soundbutton;
    MainNavMenu frommainnav;

    public static final String filename = "idfetch";
    public static final String UserID = "userid";

    int id;
    SharedPreferences logger, mpath;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        frommainnav = (MainNavMenu)getActivity();
        frommainnav.stopMusic();

        soundbutton = MediaPlayer.create(getActivity(), R.raw.sound_button);

        // dito iniinitialize yung layout ng home and navbar
        binding = FragmentStartBinding.inflate(getLayoutInflater());

        container.removeAllViews();

        return binding.getRoot();
    }

    //function to play tap sound
    void playTapSound(){
        if(tapsoundon) {
            soundbutton.setVolume(frommainnav.globalvolume, frommainnav.globalvolume);
            soundbutton.start();
        }
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

        // eto yung code para sa Aralin na button natin
        binding.Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTapSound();
                Fragment fragmentEasy = new EasyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentEasy);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (mpath.contains("K_Aralin1Locked") || mpath.contains("K_Aralin2Locked")){
            binding.Normal.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
            binding.Normal.setImageResource(R.drawable.buttonmagbasalock);
        } else {
            binding.Normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playTapSound();
                    Fragment fragmentNormal = new NormalFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu, fragmentNormal);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        if (mpath.contains("DaysLocked") || mpath.contains("YearsLocked") || mpath.contains("OppositeLocked") || mpath.contains("SynonymousLocked")){
            binding.Hard.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
            binding.Hard.setImageResource(R.drawable.buttonhardlock);
        } else {
            binding.Hard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playTapSound();
                    Fragment fragmentHard = new HardFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu, fragmentHard);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        binding.ProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTapSound();
                Fragment fragmentProgress = new ProgressFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentProgress);
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