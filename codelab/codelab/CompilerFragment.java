package com.example.codelab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.tiagohm.codeview.CodeView;
import br.tiagohm.codeview.Language;
import br.tiagohm.codeview.Theme;

public class CompilerFragment extends Fragment {

    private CodeView codeView;
    private EditText inputText;
    private TextView outputText;
    private Button runButton;
    private Switch themeSwitch;
    private Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_DARK_MODE = "isDarkMode";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compiler, container, false);


        // Initialize Views
        toolbar = view.findViewById(R.id.toolbar);
        codeView = view.findViewById(R.id.codeView);
        inputText = view.findViewById(R.id.inputText);
        outputText = view.findViewById(R.id.outputText);
        runButton = view.findViewById(R.id.runButton);
        themeSwitch = view.findViewById(R.id.themeSwitch);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        // Setup UI components
        setupToolbar();
        setupEditor();
        setupThemeSwitch();

        // Run Button Click Listener
        runButton.setOnClickListener(v -> executePythonCode());

        return view;
    }

    private void setupToolbar() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                });
            }
        }
    }

    private boolean isEditorInitialized = false;

    private void setupEditor() {
        if (isEditorInitialized) {
            Log.d("CompilerFragment", "⚠️ CodeView already initialized, skipping...");
            return;
        }
        isEditorInitialized = true;

        boolean isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        applyTheme(isDarkMode);

        if (codeView == null) {
            Log.e("CompilerFragment", "❌ CodeView is NULL!");
            return;
        }

        Log.d("CompilerFragment", "⚡ Setting up CodeView...");

        // ✅ Ensure CodeView is visible
        codeView.setVisibility(View.VISIBLE);
        codeView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // ✅ Set proper background color based on theme
        if (isDarkMode) {
            codeView.setTheme(Theme.MONOKAI);  // Dark Theme
            codeView.setBackgroundColor(Color.parseColor("#1E1E1E")); // Dark Gray
        } else {
            codeView.setTheme(Theme.GITHUB);  // Light Theme
            codeView.setBackgroundColor(Color.parseColor("#FFFFFF")); // White
        }

        // ✅ Set default code & language
        codeView.setCode("print('Hello, World!')");
        codeView.setLanguage(Language.PYTHON);
        codeView.setZoomEnabled(true);

        // 🔄 Force Layout Update
        codeView.requestLayout();
        codeView.postInvalidate();
        codeView.post(() -> {
            codeView.reload();
            Log.d("CompilerFragment", "🔄 CodeView reloaded!");
        });

        Log.d("CompilerFragment", "✅ CodeView setup complete!");
    }



    private void setupThemeSwitch() {
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        themeSwitch.setChecked(isDarkMode);

        themeSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            requireActivity().recreate(); // Restart activity to apply theme
        });
    }

    private void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            codeView.setTheme(Theme.MONOKAI);
            codeView.setBackgroundColor(Color.parseColor("#1E1E1E"));
            themeSwitch.setText("Dark Mode ON");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            codeView.setTheme(Theme.GITHUB);
            codeView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            themeSwitch.setText("Dark Mode OFF");
        }

        // 🔄 Reload CodeView without restarting activity
        codeView.invalidate();
        codeView.post(() -> codeView.reload());
    }



    private void executePythonCode() {
        if (codeView == null) {
            Toast.makeText(getContext(), "Editor not loaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        String sourceCode = codeView.getCode();
        if (sourceCode.trim().isEmpty()) {
            Toast.makeText(getContext(), "No code to run!", Toast.LENGTH_SHORT).show();
            Log.e("PythonExecution", "❌ Execution Blocked: No code provided.");
            return;
        }

        Log.d("PythonExecution", "⚡ Running Code: " + sourceCode);

        executorService.execute(() -> {
            String result = runPythonCode(sourceCode);
            handler.post(() -> outputText.setText(result));
        });
    }


    private String runPythonCode(String code) {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(requireContext()));
            Log.d("PythonExecution", "🔵 Chaquopy initialized!");
        }

        Python py = Python.getInstance();
        try {
            String result = py.getModule("script").callAttr("run_code", code).toString();
            Log.d("PythonExecution", "✅ Execution Result: " + result);
            return result;
        } catch (Exception e) {
            Log.e("PythonExecution", "❌ Execution Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

}
