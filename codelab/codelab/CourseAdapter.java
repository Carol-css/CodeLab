package com.example.codelab;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final List<Course> courseList;
    private int flippedPosition = -1; // To track the flipped card position

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course = courseList.get(position);
        holder.titleTextView.setText(course.getTitle());  // ✅ Use the getter method

        holder.descriptionTextView.setText(course.getDescription());

        // Ensure proper text orientation
        holder.descriptionTextView.setScaleX(-1f);
        holder.enrollButton.setScaleX(-1f);

        // Reset card state when binding
        if (flippedPosition == position) {
            holder.cardView.setRotationY(180);
            holder.titleTextView.setVisibility(View.GONE);
            holder.descriptionTextView.setVisibility(View.VISIBLE);
            holder.enrollButton.setVisibility(View.VISIBLE);
            holder.isFlipped = true;
        } else {
            holder.cardView.setRotationY(0);
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.descriptionTextView.setVisibility(View.GONE);
            holder.enrollButton.setVisibility(View.GONE);
            holder.isFlipped = false;
        }

        holder.cardView.setOnClickListener(v -> {
            if (flippedPosition == position) {
                // If the clicked card is already flipped, flip it back
                flipCardBack(holder);
                flippedPosition = -1; // Reset flipped position
            } else {
                // Reset the previously flipped card
                int previousPosition = flippedPosition;
                flippedPosition = position;
                notifyItemChanged(previousPosition); // Rebind the previous flipped card

                // Flip the new card
                flipCard(holder);
            }
        });

        // Button Click Listener - Open JavaLang Activity only for "Java"
        holder.enrollButton.setOnClickListener(v -> {
            if (course.getTitle().equalsIgnoreCase("Java")) {  // ✅ Use getter method
                Context context = v.getContext();
                Intent intent = new Intent(context, JavaLang.class);
                intent.putExtra("courseTitle", course.getTitle());  // ✅ Use getter method
                context.startActivity(intent);
            } else if (course.getTitle().equalsIgnoreCase("C")) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CLang.class);
                intent.putExtra("courseTitle", course.getTitle());  // ✅ Use getter method
                context.startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Enrolled in " + course.getTitle(), Toast.LENGTH_SHORT).show();  // ✅ FIXED
            }
        });
    }


    private void flipCard(ViewHolder holder) {
        ObjectAnimator flip = ObjectAnimator.ofFloat(holder.cardView, "rotationY", 0f, 180f);
        flip.setDuration(500);
        flip.start();

        holder.titleTextView.setVisibility(View.GONE);
        holder.descriptionTextView.setVisibility(View.VISIBLE);
        holder.enrollButton.setVisibility(View.VISIBLE);
    }

    private void flipCardBack(ViewHolder holder) {
        ObjectAnimator flipBack = ObjectAnimator.ofFloat(holder.cardView, "rotationY", 180f, 0f);
        flipBack.setDuration(500);
        flipBack.start();

        holder.titleTextView.setVisibility(View.VISIBLE);
        holder.descriptionTextView.setVisibility(View.GONE);
        holder.enrollButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button enrollButton;
        TextView titleTextView, descriptionTextView;
        CardView cardView;
        boolean isFlipped = false;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            enrollButton = itemView.findViewById(R.id.enrollButton);
        }
    }
}
