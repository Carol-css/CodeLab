package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize Views
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);

        // Load All Courses Initially
        loadCourses("All");

        // Tab Selection Listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadCourses(Objects.requireNonNull(tab.getText()).toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadCourses(String category) {
        courseList.clear();
        if (category.equals("All")) {
            courseList.add(new Course("HTML", "Learn HTML programming", "All"));
            courseList.add(new Course("C", "Learn C programming", "Ongoing"));
            courseList.add(new Course("C++", "Advanced C++", "Completed"));
            courseList.add(new Course("C#", "Learn C# programming", "All"));
            courseList.add(new Course("JAVA", "Master Java", "Ongoing"));
            courseList.add(new Course("Python", "Master Python", "Ongoing"));
            courseList.add(new Course("JavaScript", "Learn JavaScript programming", "Ongoing"));
            courseList.add(new Course("Swift", "Swift for iOS", "Completed"));
        }
        courseAdapter.notifyDataSetChanged();
    }
}