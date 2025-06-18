package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RemindersFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> reminderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for reminders
        reminderList = new ArrayList<>();
        reminderList.add(new Notification("Daily Streak!", "Don't forget to complete your daily challenge.", "reminder", "March 23, 2025"));
        reminderList.add(new Notification("Course Completion", "Finish your React course to earn a badge!", "reminder", "March 21, 2025"));

        adapter = new NotificationAdapter(reminderList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
