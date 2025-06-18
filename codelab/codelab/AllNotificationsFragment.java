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

public class AllNotificationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adding dummy data
        notificationList = new ArrayList<>();
        notificationList.add(new Notification("New Course Added!", "Check out our new Python course!", "update", "March 23, 2025"));
        notificationList.add(new Notification("Reminder", "Don't forget to complete your daily coding challenge!", "reminder", "March 23, 2025"));
        notificationList.add(new Notification("New Java Course!", "A new Java course is now available.", "update", "March 22, 2025"));
        notificationList.add(new Notification("App Update", "Version 2.0 released with new features!", "update", "March 20, 2025"));
        notificationList.add(new Notification("Course Completion", "Finish your React course to earn a badge!", "reminder", "March 21, 2025"));

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
