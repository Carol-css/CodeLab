package com.example.codelab;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import utils.SessionManager;

    public class MainActivity extends AppCompatActivity {
        private SessionManager sessionManager;

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            sessionManager = new SessionManager(this);
            Button logoutButton = findViewById(R.id.logoutButton);
            TextView welcomeText = findViewById(R.id.welcomeText);

            welcomeText.setText("Welcome to the Home Page!");

            logoutButton.setOnClickListener(v -> {
                sessionManager.logout(); // Clear JWT token
                startActivity(new Intent(MainActivity.this, Login_signup.class));
                finish(); 
            });
        }
    }



