package com.example.codelab;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressModuleAdapter moduleAdapter;
    private List<ProgressModuleModel> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewModules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load Modules Data
        moduleList = new ArrayList<>();
        moduleList.add(new ProgressModuleModel("Module 1: Introduction", 10, true));
        moduleList.add(new ProgressModuleModel("Module 2: Java Basics", 0, false));
        moduleList.add(new ProgressModuleModel("Module 3: Control Statements", 0, false));
        moduleList.add(new ProgressModuleModel("Module 4: Functions", 0, false));
        moduleList.add(new ProgressModuleModel("Module 5: OOP Concepts", 0, false));
        moduleList.add(new ProgressModuleModel("Module 6: Arrays & Strings", 0, false));
        moduleList.add(new ProgressModuleModel("Module 7: Exception Handling", 0, false));
        moduleList.add(new ProgressModuleModel("Module 8: File Handling & I/O", 0, false));
        moduleList.add(new ProgressModuleModel("Module 9: JCF", 0, false));
        moduleList.add(new ProgressModuleModel("Module 10: Multi-threading", 0, false));

        // Set Adapter
        moduleAdapter = new ProgressModuleAdapter(moduleList);
        recyclerView.setAdapter(moduleAdapter);
    }
}

