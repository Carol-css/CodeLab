package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CIndexTabFragment extends Fragment {

    public CIndexTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_index_tab, container, false);

        TextView indexText = view.findViewById(R.id.indexText);
        indexText.setText("1. Introduction to C\n\n2. C Basics\n\n3. Control Statements\n\n4. Functions of C\n\n5. Arrays & Strings\n\n6.Pointers in C\n\n7. Structures and Unions\n\n8. File Handling in C\n\n9. Advanced Topics\n\n10. C Libraries and Standard Functions");

        return view;
    }
}
