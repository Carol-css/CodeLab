package com.example.codelab;

import android.annotation.SuppressLint;
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

public class JavaLessonTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private ModuleAdapter adapter;
    private List<Module> moduleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java_lesson_tab, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadModules(); // Load module data

        return view;
    }

    private void loadModules() {
        moduleList = new ArrayList<>();
        moduleList.add(new Module("Module 1: INTRODUCTION TO JAVA", "~ What is Java?\n~ Features\n~ Writing your first <Hello, World!> program", false));
        moduleList.add(new Module("Module 2: JAVA BASICS (SYNTAX &amp DATA TYPES)", "~ Java Syntax\n~ Comments in Java\n~ Data Types (int, float, double, char, boolean, String)\n~ Variables & Constants\n~ Operators (Arithmetic, Logical, Relational)", false));
        moduleList.add(new Module("Module 3: CONTROL STATEMENTS", "~ If-Else Statements\n~ Switch Case\n~ Loops (for, while, do-while)", false));
        moduleList.add(new Module("Module 4: Functions (Methods)", "~ What are Functions?\n~ Creating and Using Functions\n~ Function Parameters & Return Values", false));
        moduleList.add(new Module("Module 5: OBJECT-ORIENTED PROGRAMMING (OOP)", "~ What is OOP?\n~ Classes and Objects\n~ Constructors\n~ Encapsulation, Inheritance, Polymorphism", false));
        moduleList.add(new Module("Module 6: ARRAYS AND STRINGS", "~ Arrays (1D & 2D)\n~ String Manipulation", false));
        moduleList.add(new Module("Module 7: EXCEPTION HANDLING", "~ Try-Catch Blocks\n~ Finally Block\n~ Throw & Throws", false));
        moduleList.add(new Module("Module 8: FILE HANDLING & INPUT/OUTPUT", "~ Reading and Writing Files\n~ Scanner for User Input", false));
        moduleList.add(new Module("Module 9: JAVA COLLECTIONS FRAMEWORK (JCF)", "~ ArrayList, LinkedList\n~ HashMap, HashSet", false));
        moduleList.add(new Module("Module 10: MULTI-THREADING & ADVANCED TOPICS", "~ Threads\n~ Synchronization\n~ Lambda Expressions", false));

        adapter = new ModuleAdapter(requireContext(), moduleList);
        recyclerView.setAdapter(adapter);
    }

}
