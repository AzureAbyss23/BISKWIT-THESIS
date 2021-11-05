package com.example.biskwit.MainDrawer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.biskwit.MainActivity;
import com.example.biskwit.MainNavMenu;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    MainNavMenu frommainnav;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.println("HOME ACTIVITY CREATE");

        // dito iniinitialize yung layout ng home and navbar

        //ito ung galing sa mainnavmenu tapos itawag lang si function para mag play sya
        frommainnav = (MainNavMenu)getActivity();
        frommainnav.startMusic();

        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        return rootView;
    }

    // need ito somehow para di magkabuhol buhol yung navigation thingy niya
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        frommainnav.stopMusic();
        super.onPause();
    }

    @Override
    public void onResume() {
        frommainnav.startMusic();
        super.onResume();
    }
}