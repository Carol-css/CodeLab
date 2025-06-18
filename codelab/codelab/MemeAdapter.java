package com.example.codelab;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {
    private List<meme> memeList;
    private int expandedPosition = -1;
    private RecyclerView recyclerView;

    public MemeAdapter(List<meme> memeList, RecyclerView recyclerView) {
        this.memeList = memeList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meme, parent, false);
        return new MemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemeViewHolder holder, int position) {
        meme meme = memeList.get(position);
        holder.title.setText(meme.getTitle());
        holder.image.setImageResource(meme.getImageRes());

        boolean isExpanded = position == expandedPosition;
        holder.expandedLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Click to expand/collapse the meme
        holder.itemView.setOnClickListener(v -> {
            int previousExpanded = expandedPosition;
            expandedPosition = isExpanded ? -1 : position;

            notifyItemChanged(previousExpanded);
            notifyItemChanged(expandedPosition);

            // Smoothly scroll RecyclerView to the expanded position
            if (expandedPosition != -1) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .smoothScrollToPosition(recyclerView, new RecyclerView.State(), expandedPosition);
                }, 200);
            }
        });

        holder.expandedImage.setImageResource(meme.getSecondImageRes());

        // Allow touch gestures inside ScrollView
        holder.scrollView.setOnTouchListener((v, event) -> {
            holder.scrollView.requestDisallowInterceptTouchEvent(true);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return memeList.size();
    }

    static class MemeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image, expandedImage;
        LinearLayout expandedLayout;
        ScrollView scrollView;

        public MemeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.memeTitle);
            image = itemView.findViewById(R.id.memeImage);
            expandedImage = itemView.findViewById(R.id.expandedImage);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            scrollView = itemView.findViewById(R.id.scrollView);
        }
    }
}
