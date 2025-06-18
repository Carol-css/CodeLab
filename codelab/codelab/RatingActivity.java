package com.example.codelab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText etFeedback;
    private Button btnSubmit;

    @SuppressLint("MissingInflatedId") // Suppress warnings for missing ID checks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize Views
        TextView titleTextView = findViewById(R.id.title);
        ratingBar = findViewById(R.id.ratingBar);
        etFeedback = findViewById(R.id.etFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Load and apply animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        titleTextView.startAnimation(fadeIn);
        ratingBar.startAnimation(fadeIn);

        // Submit Button Click Listener
        btnSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = etFeedback.getText().toString().trim();

            if (rating == 0) {
                Toast.makeText(RatingActivity.this, "Please select a rating", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RatingActivity.this, "Thanks for your feedback!", Toast.LENGTH_LONG).show();
                etFeedback.setText(""); // Clear feedback after submission
            }
        });
    }

    // Hide keyboard when user taps outside the EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
