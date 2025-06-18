package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dao.BookmarkDao;
import database.BookmarkDatabase;
import models.Bookmark;

public class BookmarkListFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    private BookmarkDao bookmarkDao;
    private TextView tvEmptyMessage;
    private Button btnSort;

    private boolean isNewestFirst = true;
    private String category;

    private static final String ARG_CATEGORY = "category";

    public static BookmarkListFragment newInstance(String category) {
        BookmarkListFragment fragment = new BookmarkListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBookmarks);
        tvEmptyMessage = view.findViewById(R.id.tvEmptyMessage);
        btnSort = view.findViewById(R.id.btnSort);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkDao = BookmarkDatabase.getInstance(getContext()).bookmarkDao();
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }

        loadBookmarks();

        btnSort.setOnClickListener(v -> {
            isNewestFirst = !isNewestFirst;
            loadBookmarks();
        });

        return view;
    }

    public void filterBookmarks(String query) {
        if (adapter != null) {
            adapter.filter(query);
        }
    }

    private void loadBookmarks() {
        List<Bookmark> bookmarkList = category.equals("All")
                ? isNewestFirst ? bookmarkDao.getBookmarksNewestFirst() : bookmarkDao.getBookmarksOldestFirst()
                : bookmarkDao.getBookmarksByCategory(category);

        btnSort.setText(isNewestFirst ? "Sort: Newest First" : "Sort: Oldest First");

        if (bookmarkList.isEmpty()) {
            tvEmptyMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new BookmarkAdapter(getContext(), bookmarkList);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateBookmarks(bookmarkList);
            }
        }
    }
}