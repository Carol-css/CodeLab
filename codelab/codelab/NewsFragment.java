package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsHeadline> newsList;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NewsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Initialize Toolbar
        toolbar = view.findViewById(R.id.toolbar);

        // Setup Toolbar as ActionBar
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> {
                    // Navigate to HomeFragment when Home button is clicked
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new HomeFragment()) // Replace with HomeFragment
                            .addToBackStack(null) // Optional: allows user to go back
                            .commit();
                });
            }
        }


        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        emptyTextView = view.findViewById(R.id.emptyTextView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), newsList, this::openNewsDetail);
        recyclerView.setAdapter(newsAdapter);

        // Load news
        loadNews();

        // Swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(this::loadNews);

        return view;
    }

    private void loadNews() {
        progressBar.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.postDelayed(() -> {
            newsList.clear();
            newsList.add(new NewsHeadline("Breaking: AI is transforming industries!",
                    "AI is being rapidly adopted in multiple industries..."));
            newsList.add(new NewsHeadline("Google announces new AI-powered search features.",
                    "Google is introducing AI-powered enhancements..."));
            newsList.add(new NewsHeadline("Apple releases iPhone 16 with enhanced AI capabilities.",
                    "The latest iPhone 16 features new AI-driven..."));
            newsList.add(new NewsHeadline("NASA discovers new exoplanet similar to Earth.",
                    "NASA scientists have found an Earth-like planet..."));

            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (newsList.isEmpty()) {
                emptyTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyTextView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                newsAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }


    private void openNewsDetail(NewsHeadline headline) {
        NewsDetailFragment newsDetailFragment = NewsDetailFragment.newInstance(
                headline.getHeadline(),
                headline.getDescription()
        );

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, newsDetailFragment) // Replace with your container ID
                .addToBackStack(null)
                .commit();
    }

}