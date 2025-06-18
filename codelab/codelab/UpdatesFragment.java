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

public class UpdatesFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> updateList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for updates
        updateList = new ArrayList<>();
        updateList.add(new Notification("New Java Course!", "A new Java course is now available.", "update", "March 22, 2025"));
        updateList.add(new Notification("App Update", "Version 2.0 released with new features!", "update", "March 20, 2025"));

        adapter = new NotificationAdapter(updateList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
