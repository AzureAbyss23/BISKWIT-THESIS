package com.example.biskwit.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.biskwit.Lesson;
import com.example.biskwit.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button LessonButton;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View rootView = inflater.inflate(R.layout.fragment_home,
                container, false);
        LessonButton = (Button) rootView.findViewById(R.id.LessonButton);
        LessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLesson();
            }
        });
        return rootView;
    }
    public void GoToLesson()
    {
        intent = new Intent(getActivity(), Lesson.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}