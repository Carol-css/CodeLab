package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {
    private static final String ARG_HEADLINE = "headline";
    private static final String ARG_DESCRIPTION = "description";

    private String headline;
    private String description;

    public static NewsDetailFragment newInstance(String headline, String description) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HEADLINE, headline);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headline = getArguments().getString(ARG_HEADLINE);
            description = getArguments().getString(ARG_DESCRIPTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // Set news details
        TextView headlineTextView = view.findViewById(R.id.newsDetailHeadline);
        TextView descriptionTextView = view.findViewById(R.id.newsDetailDescription);

        headlineTextView.setText(headline);
        descriptionTextView.setText(description);

        return view;
    }
}
