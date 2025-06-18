package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MemesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MemeAdapter adapter;
    private List<meme> memeList;


    private Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memes, container, false);

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
                            .replace(R.id.frame_layout, new HomeFragment()) 
                            .addToBackStack(null) // Optional: allows user to go back
                            .commit();
                });
            }
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        memeList = new ArrayList<>();
        memeList.add(new meme("java meme", R.drawable.java_meme, R.drawable.meme_java));
        memeList.add(new meme("python meme", R.drawable.python_meme, R.drawable.meme_python));
        memeList.add(new meme("c meme", R.drawable.c_meme, R.drawable.meme_c));

        adapter = new MemeAdapter(memeList, recyclerView);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
