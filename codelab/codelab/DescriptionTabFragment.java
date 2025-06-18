package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DescriptionTabFragment extends Fragment {

    public DescriptionTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description_tab, container, false);

        TextView descriptionText = view.findViewById(R.id.descriptionText);
        descriptionText.setText("What is Java?\n\nJava is a programming language and a platform.Java is a high-level, robust, object-oriented and secure programming language.\n\nWhat will you learn?\n\nJava Syntax:\nYou'll learn the basic structure and rules of the Java language, including how to declare variables, use operators, and control program flow.\n\nObject-Oriented Programming (OOP):\nJava is an object-oriented language, so you'll learn about key concepts like classes, objects, inheritance, polymorphism, and encapsulation.\n\nData Structures:\nYou'll learn about fundamental data structures like arrays, linked lists, stacks, queues, trees, and graphs, and how to implement them in Java.\n\nAlgorithms:\nYou'll learn about common algorithms and how to apply them to solve problems, including searching, sorting, and graph traversal.");


        return view;
    }
}
