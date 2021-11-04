package com.example.biskwit.MainDrawer;

import static com.example.biskwit.MainNavMenu.tapsoundon;
import static com.example.biskwit.MainNavMenu.globalvolume;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


import com.example.biskwit.Contact_Info;
import com.example.biskwit.MainNavMenu;
import com.example.biskwit.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());

        MainNavMenu frommainnav = (MainNavMenu)getActivity();

        binding.seekBar.setProgress((int) (globalvolume*100));

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //galing sa main para makapag change volume
                float volumeScalar = progress/100.0f;
                frommainnav.changeVolume(volumeScalar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(tapsoundon){
            binding.switch1.setChecked(true);
            binding.switch1.setText("ON");
        }
        else{
            binding.switch1.setText("OFF");
            binding.switch1.setChecked(false);
        }


        binding.switch1.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                compoundButton.setText("ON");
                tapsoundon = true;
            }
            else  {
                compoundButton.setText("OFF");
                tapsoundon = false;
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), Contact_Info.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}