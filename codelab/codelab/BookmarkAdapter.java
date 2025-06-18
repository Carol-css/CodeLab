package com.example.codelab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.BookmarkDatabase;
import models.Bookmark;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private Context context;
    private List<Bookmark> bookmarkList;
    private List<Bookmark> fullList; // Full copy for filtering
    private BookmarkDatabase bookmarkDatabase;

    public BookmarkAdapter(Context context, List<Bookmark> bookmarkList) {
        this.context = context;
        this.bookmarkList = new ArrayList<>(bookmarkList);
        this.fullList = new ArrayList<>(bookmarkList);
        this.bookmarkDatabase = BookmarkDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);

        holder.lessonTitle.setText(bookmark.getLessonTitle());
        holder.courseName.setText(bookmark.getCourseName());
        holder.content.setText(bookmark.getContent());

        // Handle click event to open lesson content
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, JavaContentM1.class);
            intent.putExtra("MODULE_TITLE", bookmark.getLessonTitle()); // Pass lesson title
            intent.putExtra("COURSE_NAME", bookmark.getCourseName()); // Pass course name
            context.startActivity(intent);
        });

        // Delete Bookmark
        holder.btnDeleteBookmark.setOnClickListener(view -> {
            new Thread(() -> {
                bookmarkDatabase.bookmarkDao().deleteBookmark(bookmark);
                bookmarkList.remove(position);
                ((BookmarkActivity) context).runOnUiThread(() -> {
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
                });
            }).start();
        });

    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    // Filter Method (For Search Functionality)
    public void filter(String query) {
        if (query.isEmpty()) {
            bookmarkList = new ArrayList<>(fullList);
        } else {
            bookmarkList = fullList.stream()
                    .filter(bookmark -> bookmark.getLessonTitle().toLowerCase().contains(query.toLowerCase()) ||
                            bookmark.getCourseName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        notifyDataSetChanged();
    }


    // Update Bookmarks List (For Dynamic Data Changes)
    public void updateBookmarks(List<Bookmark> newBookmarks) {
        fullList.clear();
        fullList.addAll(newBookmarks);
        bookmarkList.clear();
        bookmarkList.addAll(newBookmarks);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle, courseName, content;
        ImageView btnDeleteBookmark;

        public ViewHolder(View itemView) {
            super(itemView);
            lessonTitle = itemView.findViewById(R.id.tvLessonTitle);
            courseName = itemView.findViewById(R.id.tvCourseName);
            content = itemView.findViewById(R.id.tvContent);
            btnDeleteBookmark = itemView.findViewById(R.id.btnDeleteBookmark);
        }
    }
}
