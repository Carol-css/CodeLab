package com.example.codelab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IndexTabFragment extends Fragment {

    public IndexTabFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_tab, container, false);

        TextView indexText = view.findViewById(R.id.indexText);
        indexText.setText("1. Introduction to Java\n\n2. Java Basics\n\n3. Control Statements\n\n4. Functions\n\n5. OOP\n\n6. Arrays & Strings\n\n7. Exception Handling\n\n8. File Handling & I/0\n\n9. JCF\n\n10. Multi-threading & Advanced Topics");

        return view;
    }
}
