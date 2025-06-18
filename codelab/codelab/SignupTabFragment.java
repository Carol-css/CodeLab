package com.example.codelab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupTabFragment extends Fragment {
    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = view.findViewById(R.id.signup_name);
        emailEditText = view.findViewById(R.id.signup_email);
        passwordEditText = view.findViewById(R.id.signup_password);
        confirmPasswordEditText = view.findViewById(R.id.signup_confirmpassword);
        signupButton = view.findViewById(R.id.signupButton);

        // Set password toggle icons
        setupPasswordToggle(passwordEditText, true);
        setupPasswordToggle(confirmPasswordEditText, false);

        signupButton.setOnClickListener(v -> registerUser());

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupPasswordToggle(EditText editText, boolean isPasswordField) {
        updateEyeIcon(editText, isPasswordField);

        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                final int DRAWABLE_END = 2; // Right-side drawable
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    if (isPasswordField) {
                        isPasswordVisible = !isPasswordVisible;
                        editText.setTransformationMethod(isPasswordVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    } else {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible;
                        editText.setTransformationMethod(isConfirmPasswordVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    }
                    updateEyeIcon(editText, isPasswordField);
                    editText.setSelection(editText.getText().length()); // Maintain cursor position
                    return true;
                }
            }
            return false;
        });
    }

    private void updateEyeIcon(EditText editText, boolean isPasswordField) {
        int eyeIcon = (isPasswordField ? isPasswordVisible : isConfirmPasswordVisible)
                ? R.drawable.ic_eye_open
                : R.drawable.ic_eye_closed;
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, eyeIcon, 0);
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Signup successful! Please verify your email.", Toast.LENGTH_SHORT).show();
                                            user.sendEmailVerification();

                                            new Handler(Looper.getMainLooper()).post(() -> {
                                                ViewPager2 viewPager = requireActivity().findViewById(R.id.view_pager);
                                                TabLayout tabLayout = requireActivity().findViewById(R.id.tab_layout);

                                                if (viewPager != null && tabLayout != null) {
                                                    viewPager.setCurrentItem(0, true);
                                                    tabLayout.selectTab(tabLayout.getTabAt(0));
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(getActivity(), "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
