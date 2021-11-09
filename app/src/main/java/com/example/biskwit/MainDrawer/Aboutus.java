package com.example.biskwit.MainDrawer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.biskwit.MainActivity.MainNavMenu;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentAboutusBinding;

public class Aboutus extends Fragment {

    FragmentAboutusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainNavMenu frommainnav = (MainNavMenu)getActivity();
        frommainnav.stopMusic();

        // dito iniinitialize yung layout ng home and navbar

        container.removeAllViews();

        View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);

        //video introduction
        MediaController mc= new MediaController(getActivity());

        VideoView view = (VideoView)rootView.findViewById(R.id.Intro);
        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro;
        view.setVideoURI(Uri.parse(path));
        view.setMediaController(mc);
        view.start();

        return rootView;

    }



    // need ito somehow para di magkabuhol buhol yung navigation thingy niya
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}