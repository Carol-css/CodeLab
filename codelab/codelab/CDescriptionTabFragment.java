package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CDescriptionTabFragment extends Fragment {

    public CDescriptionTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_description_tab, container, false);

        TextView descriptionText = view.findViewById(R.id.descriptionText);
        descriptionText.setText(" Introduction & History\n" +
                "\n" +
                "C is a general-purpose, procedural programming language.\n" +
                "\n" +
                "Developed by Dennis Ritchie in 1972 at Bell Labs.\n" +
                "\n" +
                "Originally designed for system programming and writing the UNIX operating system.\n" +
                "\n" +
                "Became one of the most widely used programming languages.\n" +
                "\n" +
                "Forms the foundation for modern languages like C++, Java, and Python.\n" +
                "\n" +
                "Follows a structured programming approach for better organization and reusability.\n" +
                "\n" +
                "Key Features & Strengths\n" +
                "\n" +
                "Low-level access to memory and direct hardware interaction.\n" +
                "\n" +
                "Ideal for system-level applications, embedded systems, and OS development.\n" +
                "\n" +
                "Supports pointers, dynamic memory allocation, bitwise operations, and file handling.\n" +
                "\n" +
                "Fast execution speed due to direct compilation into machine code.\n" +
                "\n" +
                "Used in performance-critical applications like gaming engines, real-time systems, and database management systems.\n" +
                "\n" +
                "Portability & Influence\n" +
                "\n" +
                "Despite being high-level, retains many assembly-like characteristics.\n" +
                "\n" +
                "Highly portable, allowing programs to run on multiple platforms with minimal modifications.\n" +
                "\n" +
                "Offers a rich standard library for input/output operations, math functions, and memory management.\n" +
                "\n" +
                "Remains a fundamental language in systems programming, embedded development, and software engineering.");

        return view;
    }
}
