package com.example.biskwit.Content.Lessons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.biskwit.Content.EasyFragment;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Days.Days;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Opposite.OppositeAct;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Sounds.Sounds;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Synonymous.SynonymousAct;
import com.example.biskwit.Content.Lessons.AlamkoitoActivities.Years.Years;
import com.example.biskwit.Content.NormalFragment;
import com.example.biskwit.R;
import com.example.biskwit.databinding.FragmentAlamkoitoBinding;


public class AlamkoitoFragment extends Fragment {

    FragmentAlamkoitoBinding binding;
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
        binding = FragmentAlamkoitoBinding.inflate(getLayoutInflater());
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

        // eto yung code para sa Aralin na button natin
        binding.Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), Days.class);
                startActivity(intent);
            }
        });

        if (mpath.contains("DaysLocked")){
            binding.Years.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
        } else {
            binding.Years.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), Years.class);
                    startActivity(intent);
                }
            });
        }

        if (mpath.contains("DaysLocked") || mpath.contains("YearsLocked")){
            binding.Opposite.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
        } else {
            binding.Opposite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), OppositeAct.class);
                    startActivity(intent);
                }
            });
        }

        if (mpath.contains("DaysLocked") || mpath.contains("YearsLocked") || mpath.contains("OppositeLocked")){
            binding.Synonymous.setBackgroundTintList(getResources().getColorStateList(R.drawable.buttontint));
        } else {
            binding.Synonymous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), SynonymousAct.class);
                    startActivity(intent);
                }
            });
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentBack = new NormalFragment();
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