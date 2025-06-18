package com.example.codelab;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CLang extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_lang);

        // Add JavaCourseFragment to JavaLang
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new CCourseFragment())
                    .commit();
        }
    }
}
