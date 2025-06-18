package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import database.BookmarkDatabase;
import models.Bookmark;

public class BookmarkActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BookmarkViewPagerAdapter adapter;
    private TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        tabLayout = findViewById(R.id.bookmarktabLayout);
        viewPager = findViewById(R.id.bookmarkviewPager);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());


        // ✅ Set up ViewPager2 with BookmarkViewPagerAdapter
        adapter = new BookmarkViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String[] tabTitles = {"All", "Java", "Python", "C++", "Javascript"};
            tab.setText(tabTitles[position]);
        }).attach();

        // Handle SearchView from Toolbar
        SearchView searchView = findViewById(R.id.searchViewBookmarks);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCurrentFragment(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCurrentFragment(newText);
                return true;
            }
        });
    }

    private void filterCurrentFragment(String query) {
        int currentTab = viewPager.getCurrentItem();
        BookmarkListFragment fragment = (BookmarkListFragment) getSupportFragmentManager()
                .findFragmentByTag("f" + currentTab);

        if (fragment != null) {
            fragment.filterBookmarks(query);
        }
    }
}