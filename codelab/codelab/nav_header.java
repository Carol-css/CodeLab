package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class nav_header extends AppCompatActivity {
    private TextView userNameTextView, userEmailTextView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        userNameTextView = findViewById(R.id.userNameView);
        userEmailTextView = findViewById(R.id.userEmailView);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();
            String userName = user.getDisplayName(); // May be null

            userEmailTextView.setText(userEmail);
            userNameTextView.setText(userName);
        }
    }
}
