package com.example.codelab;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VideoFragment extends Fragment {

    private static final String ARG_VIDEO_RES_ID = "videoResId";
    private static final String ARG_THEORY_TEXT = "theory_text";
    private static final String PREFS_NAME = "VideoPrefs";
    private static final String KEY_VIDEO_POSITION = "video_position";

    private int videoResId;
    private String theoryText;
    private VideoView videoView;
    private int lastPlaybackPosition = 0;
    private MediaController mediaController;

    public static VideoFragment newInstance(int videoResId, String theoryText) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIDEO_RES_ID, videoResId);
        args.putString(ARG_THEORY_TEXT, theoryText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoResId = getArguments().getInt(ARG_VIDEO_RES_ID);
            theoryText = getArguments().getString(ARG_THEORY_TEXT);
        }

        // Retrieve saved playback position
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        lastPlaybackPosition = sharedPreferences.getInt(KEY_VIDEO_POSITION, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        // Initialize VideoView
        videoView = view.findViewById(R.id.videoView3);
        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + videoResId;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Retrieve sound preference
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
        boolean isSoundOn = sharedPreferences.getBoolean("sound", true);

        // Initialize MediaController and attach it inside VideoView
        mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set OnPreparedListener to handle volume and playback position
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(isSoundOn ? 1.0f : 0.0f, isSoundOn ? 1.0f : 0.0f);
            if (lastPlaybackPosition > 0) {
                videoView.seekTo(lastPlaybackPosition);
            }
            videoView.start();
        });

        // Show MediaController when the user interacts with the video
        videoView.setOnTouchListener((v, event) -> {
            mediaController.show();
            return false;
        });

        // Initialize and set Theory Content
        TextView theoryTextView = view.findViewById(R.id.theoryContent);
        theoryTextView.setText(theoryText);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            lastPlaybackPosition = videoView.getCurrentPosition(); // Save playback position
            videoView.pause();

            // Save playback position to SharedPreferences
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_VIDEO_POSITION, lastPlaybackPosition);
            editor.apply();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.seekTo(lastPlaybackPosition);
            videoView.start();
        }
    }
}
