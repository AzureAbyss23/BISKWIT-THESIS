package com.example.biskwit.MainDrawer;

import static com.example.biskwit.MainNavMenu.tapsoundon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.biskwit.Content.LessonFragment;
import com.example.biskwit.MainNavMenu;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentStartBinding;
import com.example.biskwit.Content.storyFragment;

public class StartFragment extends Fragment {

    FragmentStartBinding binding;
    public static MediaPlayer soundbutton;

    MainNavMenu frommainnav;

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

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // eto yung code para sa Aralin na button natin
        binding.LessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTapSound();

                Fragment fragmentLesson = new LessonFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentLesson);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // eto yung code para sa maikling kwento na button
        binding.StoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTapSound();

                Fragment fragmentStory = new storyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentStory);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.ProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTapSound();

                Fragment fragmentStory = new storyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main_nav_menu,fragmentStory);
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