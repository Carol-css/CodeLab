package com.example.codelab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CCourseFragment extends Fragment {

    private VideoView videoView;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    public CCourseFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_course, container, false);

        // Initialize Views
        videoView = view.findViewById(R.id.videoView);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        Button startButton = view.findViewById(R.id.startButton);

        // Initialize Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        // Retrieve sound preference
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
        boolean isSoundOn = sharedPreferences.getBoolean("sound", true);

        // Set Video Path
        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + R.raw.cintro;
        videoView.setVideoURI(Uri.parse(videoPath));

        // Initialize MediaController
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set sound based on preference
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(isSoundOn ? 1.0f : 0.0f, isSoundOn ? 1.0f : 0.0f);
            videoView.start();
        });


        // Set up ViewPager2 with TabLayout
        setupViewPager();

        // Set button click to start new activity
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CLessonCompiler.class);
            startActivity(intent);
        });
        return view;
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CDescriptionTabFragment()); // Description Tab
        fragments.add(new CIndexTabFragment());         // Index Tab

        CCoursePagerAdapter adapter = new CCoursePagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        // Ensure TabLayoutMediator correctly attaches
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Description");
                    break;
                case 1:
                    tab.setText("Index");
                    break;
            }
        }).attach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.stopPlayback();  // Stop the current playback
            videoView.setVideoURI(Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.cintro)); // Reassign video source
            videoView.start(); // Start again
        }
    }

}
