package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CLessonTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private CModuleAdapter adapter;
    private List<CModule> moduleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_lesson_tab, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadModules(); // Load module data

        return view;
    }

    private void loadModules() {
        moduleList = new ArrayList<>();
        moduleList.add(new CModule("Module 1: INTRODUCTION TO C", "~ What is C?\n~ Features\n~ Writing your first <Hello, World!> program", false));
        moduleList.add(new CModule("Module 2: C BASICS (SYNTAX &amp DATA TYPES)", "~ C Syntax\n~ Comments in C\n~ Data Types (int, float, double, char, boolean, String)\n~ Variables & Constants\n~ Operators (Arithmetic, Logical, Relational)", false));
        moduleList.add(new CModule("Module 3: CONTROL FLOW STATEMENTS", "~ Conditional Statements\n~ Looping Statements \n~ Jump Statements)", false));
        moduleList.add(new CModule("Module 4: FUNCTIONS IN C", "~ Function Declaration and Definition\n~ Call by Value vs Call by Reference \n~ Recursion", false));
        moduleList.add(new CModule("Module 5: ARRAYS AND STRINGS", "~ One-Dimensional and Multi-dimensional Arrays\n~ String Handling Functions \n~ Pointers with Arrays and Strings", false));
        moduleList.add(new CModule("Module 6: POINTERS IN C", "~ Introduction to Pointers \n~ Pointers Arithmetic \n~ Pointers and Functions ", false));
        moduleList.add(new CModule("Module 7: STRUCTURE AND UNIONS", "~ Nested Structured \n~ Arrays of Structures \n~ Pointers to Structures", false));
        moduleList.add(new CModule("Module 8: FILE HANDLING IN C", "~ File Operations \n~ File Modes \n~ Reading and Writing to Files", false));
        moduleList.add(new CModule("Module 9: ADVANCED TOPICS", "~ Command Line Arguments\n~Memory Management\n~ Linked Lists", false));
        moduleList.add(new CModule("Module 10:C LIBRARIES AND STANDARD FUNCTIONS", "~ Stdio.h\n~ Stdlib.h\n~ String.h", false));

        adapter = new CModuleAdapter(requireContext(), moduleList);
        recyclerView.setAdapter(adapter);
    }

}
