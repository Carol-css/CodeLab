package com.example.codelab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView newsRecyclerView, languageRecyclerView;
    private NewsAdapter newsAdapter;
    private LanguageAdapter languageAdapter;
    private List<NewsHeadline> newsList;
    private List<Language> languageList;
    private Handler autoScrollHandler = new Handler();
    private Runnable languageScrollRunnable, newsScrollRunnable;
    private TextView userNameTextView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Enable options menu in fragment
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Toolbar
        toolbar = view.findViewById(R.id.toolbar);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout); // Ensure drawer is in Activity
        navigationView = getActivity().findViewById(R.id.nav_view);

        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        // Open navigation drawer on menu click
        toolbar.setNavigationOnClickListener(v -> {
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        userNameTextView = view.findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            //String userName = user.getDisplayName(); // May be null
            userNameTextView.setText(extractNameOnly(user.getDisplayName()));
        }

        // Initialize RecyclerViews
        setupRecyclerViews(view);

        return view;
    }
    private String extractNameOnly(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return "User"; // Default name if empty
        }
        String[] parts = displayName.split("%%", 2);
        return parts[0]; // Return only the name before "%%"
    }

    private void setupRecyclerViews(View view) {
        // Initialize Language RecyclerView
        languageRecyclerView = view.findViewById(R.id.languageRecyclerView);
        LinearLayoutManager languageLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        languageRecyclerView.setLayoutManager(languageLayoutManager);

        // Load Language Data
        languageList = new ArrayList<>();
        loadLanguageData();
        languageAdapter = new LanguageAdapter(getContext(), languageList);
        languageRecyclerView.setAdapter(languageAdapter);

        // Auto-scroll Language RecyclerView
        autoScrollLanguageRecyclerView();

        // Initialize News RecyclerView
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        LinearLayoutManager newsLayoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        // Load News Data
        newsList = new ArrayList<>();
        loadNewsData();
        newsAdapter = new NewsAdapter(getContext(), newsList, this::openNewsFragment);
        newsRecyclerView.setAdapter(newsAdapter);

        // Auto-scroll News RecyclerView
        autoScrollNewsRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("MENU_DEBUG", "onCreateOptionsMenu is called!"); // Debugging log
        menu.clear(); // Prevent duplicate items
        inflater.inflate(R.menu.home_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notifications) {
            Intent intent = new Intent(getContext(), NotificationsActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Animation
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.appBarLayout);
        if (appBarLayout != null) {
            appBarLayout.setBackgroundColor(Color.TRANSPARENT);
            appBarLayout.setElevation(0f);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AppBarLayout appBarLayout = getActivity().findViewById(R.id.appBarLayout);
        if (appBarLayout != null) {
            appBarLayout.setBackgroundColor(getResources().getColor(R.color.default_toolbar_color));
            appBarLayout.setElevation(4f);
        }
    }

    // Auto-scroll for Language RecyclerView
    private void autoScrollLanguageRecyclerView() {
        languageScrollRunnable = new Runnable() {
            int position = 0;
            boolean scrollRight = true;

            @Override
            public void run() {
                if (scrollRight) {
                    if (position < languageList.size() - 1) position++;
                    else scrollRight = false;
                } else {
                    if (position > 0) position--;
                    else scrollRight = true;
                }
                languageRecyclerView.smoothScrollToPosition(position);
                autoScrollHandler.postDelayed(this, 2000);
            }
        };
        autoScrollHandler.postDelayed(languageScrollRunnable, 2000);
    }

    // Auto-scroll for News RecyclerView
    private void autoScrollNewsRecyclerView() {
        newsScrollRunnable = new Runnable() {
            int position = 0;
            boolean scrollDown = true;

            @Override
            public void run() {
                if (scrollDown) {
                    if (position < newsList.size() - 1) position++;
                    else scrollDown = false;
                } else {
                    if (position > 0) position--;
                    else scrollDown = true;
                }
                newsRecyclerView.smoothScrollToPosition(position);
                autoScrollHandler.postDelayed(this, 3000);
            }
        };
        autoScrollHandler.postDelayed(newsScrollRunnable, 3000);
    }

    // Load Language Data
    private void loadLanguageData() {
        languageList.add(new Language("Java", R.drawable.java_icon));
        languageList.add(new Language("Python", R.drawable.python_icon));
        languageList.add(new Language("C", R.drawable.c_icon));
        languageList.add(new Language("Kotlin", R.drawable.kotlin_icon));
        languageList.add(new Language("JavaScript", R.drawable.javascript_icon));
        languageList.add(new Language("Ruby", R.drawable.c_icon));
        languageList.add(new Language("Swift", R.drawable.kotlin_icon));
        languageList.add(new Language("C#", R.drawable.python_icon));
        languageList.add(new Language("Dart", R.drawable.javascript_icon));
    }

    // Load News Data
    private void loadNewsData() {
        newsList.add(new NewsHeadline("Breaking: AI is taking over tech industry!", "Artificial Intelligence is transforming industries with automation and deep learning."));
        newsList.add(new NewsHeadline("Google launches new AI-powered search engine.", "Google's latest search engine provides contextual results using AI."));
        newsList.add(new NewsHeadline("Apple announces iPhone 15 with groundbreaking features.", "Apple's iPhone 15 introduces an A17 Bionic chip, better battery, and a 120Hz OLED display."));
        newsList.add(new NewsHeadline("NASA confirms water on Mars, new exploration missions planned.", "NASA scientists confirm liquid water on Mars, opening new exploration possibilities."));
        newsList.add(new NewsHeadline("Google launches new AI-powered search engine.", "Google's latest search engine provides contextual results using AI."));
        newsList.add(new NewsHeadline("Apple announces iPhone 15 with groundbreaking features.", "Apple's iPhone 15 introduces an A17 Bionic chip, better battery, and a 120Hz OLED display."));
        newsList.add(new NewsHeadline("Breaking: AI is taking over tech industry!", "Artificial Intelligence is transforming industries with automation and deep learning."));
        newsList.add(new NewsHeadline("Google launches new AI-powered search engine.", "Google's latest search engine provides contextual results using AI."));
        newsList.add(new NewsHeadline("Breaking: AI is taking over tech industry!", "Artificial Intelligence is transforming industries with automation and deep learning."));
        newsList.add(new NewsHeadline("Google launches new AI-powered search engine.", "Google's latest search engine provides contextual results using AI."));
    }

    private void openNewsFragment(NewsHeadline news) {
        Bundle bundle = new Bundle();
        bundle.putString("headline", news.getHeadline());
        bundle.putString("content", news.getDescription());

        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, newsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
