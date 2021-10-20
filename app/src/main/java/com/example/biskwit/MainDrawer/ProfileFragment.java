package com.example.biskwit.MainDrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.biskwit.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // dito iniinitialize yung layout niya
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    //wala pang logic sa profile pero pag may database na huhugutin siya don so eventually need na

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}