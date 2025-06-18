package com.example.codelab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText etName, etAbout;
    private TextView etEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Switch switchSound, switchTheme, switchReminder;
    private TextView tvChangePassword, tvSetGoal, tvReminderTime;
    private TextView tvRateUs, tvFollowX, tvLikeFacebook, tvHelp, tvTerms, tvPrivacy, tvDeleteAccount;
    private Button btnLogout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextInputLayout nameInputLayout, aboutInputLayout;

    private boolean isEditingName = false;
    private boolean isEditingAbout = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etAbout = findViewById(R.id.etAbout);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        aboutInputLayout = findViewById(R.id.aboutInputLayout);
        etEmail = findViewById(R.id.etEmail);
        switchSound = findViewById(R.id.switchSound);
        switchTheme = findViewById(R.id.switchTheme);
        switchReminder = findViewById(R.id.switchReminder);
        tvChangePassword = findViewById(R.id.tvChangePassword);
        tvSetGoal = findViewById(R.id.tvSetGoal);
        tvReminderTime = findViewById(R.id.tvReminderTime);
        tvRateUs = findViewById(R.id.tvRateUs);
        tvFollowX = findViewById(R.id.tvFollowX);
        tvLikeFacebook = findViewById(R.id.tvLikeFacebook);
        tvHelp = findViewById(R.id.tvHelp);
        tvTerms = findViewById(R.id.tvTerms);
        tvPrivacy = findViewById(R.id.tvPrivacy);
        tvDeleteAccount = findViewById(R.id.tvDeleteAccount);
        btnLogout = findViewById(R.id.btnLogout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        // Load saved preferences
        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);
        loadPreferences();
        editor = sharedPreferences.edit();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();
            loadUserData(user); // Load name & about

            etEmail.setText(userEmail);
        }

        // Handle Name Edit/Save toggle
        nameInputLayout.setEndIconOnClickListener(v -> {
            if (!isEditingName) {
                enableEditing(etName, nameInputLayout);
                isEditingName = true;
            } else {
                saveNameToFirebase(etName, nameInputLayout);
                isEditingName = false;
            }
        });

        // Handle About You Edit/Save toggle
        aboutInputLayout.setEndIconOnClickListener(v -> {
            if (!isEditingAbout) {
                enableEditing(etAbout, aboutInputLayout);
                isEditingAbout = true;
            } else {
                saveAboutToFirebase(etAbout, aboutInputLayout);
                isEditingAbout = false;
            }
        });

        // Listen for real-time user updates
        mAuth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser updatedUser = firebaseAuth.getCurrentUser();
            if (updatedUser != null) {
                loadUserData(updatedUser);
            }
        });

       // Load sound setting
        boolean isSoundOn = sharedPreferences.getBoolean("sound", true);
        switchSound.setChecked(isSoundOn);

        // Toggle sound preference
        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference("sound", isChecked);
        });

        // Set theme switch state
        switchTheme.setChecked(sharedPreferences.getBoolean("DarkMode", true));

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("DarkMode", true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("DarkMode", false);
            }
            editor.apply();
        });

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> savePreference("reminder", isChecked));

        tvChangePassword.setOnClickListener(v -> showChangePasswordDialog());
        tvSetGoal.setOnClickListener(v -> setGoal());
        tvReminderTime.setOnClickListener(v -> showTimePickerDialog());

        tvRateUs.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, RatingActivity.class);
            startActivity(intent);
        });
        tvFollowX.setOnClickListener(v -> openURL("https://developer.android.com/"));
        tvLikeFacebook.setOnClickListener(v -> openURL("https://developer.android.com/"));

        tvHelp.setOnClickListener(v -> openSupport());
        tvTerms.setOnClickListener(v -> openURL("https://policies.google.com/u/1/privacy"));
        tvPrivacy.setOnClickListener(v -> openURL("https://policies.google.com/u/1/privacy"));

        tvDeleteAccount.setOnClickListener(v -> confirmDeleteAccount());
        btnLogout.setOnClickListener(v -> logout());
        //btnSave.setOnClickListener(v -> saveUserData());
    }

    private void enableEditing(TextInputEditText editText, TextInputLayout layout) {
        editText.setEnabled(true);
        editText.requestFocus();
        layout.setEndIconDrawable(R.drawable.ic_save); // Change icon to Save (💾)

        // Show keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void saveNameToFirebase(TextInputEditText editText, TextInputLayout layout) {
        String newName = editText.getText().toString().trim();
        String aboutText = etAbout.getText().toString().trim();

        if (user != null) {
            // Store About You only in Firebase, but Dashboard should see only the name
            String displayName = aboutText.isEmpty() ? newName : newName + "%%" + aboutText;

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadUserData(user);
                            Toast.makeText(SettingsActivity.this, "Name updated!", Toast.LENGTH_SHORT).show();
                            editText.setEnabled(false);
                            layout.setEndIconDrawable(R.drawable.ic_edit); // Switch back to Edit (✏️)
                        } else {
                            Toast.makeText(SettingsActivity.this, "Failed to update name", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveAboutToFirebase(TextInputEditText editText, TextInputLayout layout) {
        String aboutText = editText.getText().toString().trim();
        String currentName = etName.getText().toString().trim();

        if (user != null) {
            // Only Settings page should display About You
            String displayName = aboutText.isEmpty() ? currentName : currentName + "%%" + aboutText;

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            loadUserData(user);
                            Toast.makeText(SettingsActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
                            editText.setEnabled(false);
                            layout.setEndIconDrawable(R.drawable.ic_edit); // Switch back to Edit (✏️)
                        } else {
                            Toast.makeText(SettingsActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void loadUserData(FirebaseUser user) {
        if (user.getDisplayName() != null) {
            String[] parts = user.getDisplayName().split("%%", 2);
            etName.setText(parts[0]); // Set name

            if (parts.length > 1) {
                etAbout.setText(parts[1]); // Set "About You" in Settings
            } else {
                etAbout.setText(""); // Prevent "name%%" issue
            }
        }
    }


    // Hide keyboard when user taps outside the EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void saveUserData() {
        editor.putString("name", etName.getText().toString().trim());
        editor.putString("about", etAbout.getText().toString().trim());
        editor.apply();
        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
    }

    private void loadPreferences() {
        etName.setText(sharedPreferences.getString("name", ""));
        etAbout.setText(sharedPreferences.getString("about", ""));
        etEmail.setText(sharedPreferences.getString("email", ""));
        switchSound.setChecked(sharedPreferences.getBoolean("sound", true));
        switchTheme.setChecked(sharedPreferences.getBoolean("darkMode", false));
        switchReminder.setChecked(sharedPreferences.getBoolean("reminder", true));
    }

    private void savePreference(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void savePreference(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    private void setGoal() {
        // Add logic for setting a learning goal
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            Calendar reminderTime = Calendar.getInstance();
            reminderTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            reminderTime.set(Calendar.MINUTE, minuteOfHour);
            reminderTime.set(Calendar.SECOND, 0);
            reminderTime.set(Calendar.MILLISECOND, 0);

            // If the selected time is in the past, schedule it for the next day
            if (reminderTime.getTimeInMillis() < System.currentTimeMillis()) {
                reminderTime.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Format and display the selected time
            String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            tvReminderTime.setText(time);
            savePreference("reminderTime", time);

            // Schedule the notification
            scheduleReminder(reminderTime);

        }, hour, minute, true).show();
    }

    private void scheduleReminder(Calendar reminderTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            // Ensure the reminder time is in the future
            if (reminderTime.before(Calendar.getInstance())) {
                reminderTime.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Set the alarm to repeat every day at the same time
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    reminderTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );

            Toast.makeText(this, "Daily Reminder Set!", Toast.LENGTH_SHORT).show();
        }
    }



    private void openSupport() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@codelab.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void confirmDeleteAccount() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> deleteAccount())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logout() {
        // Clear login session and redirect to login screen
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(SettingsActivity.this, Login_signup.class));
        finish();
    }
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password");

        final EditText input = new EditText(this);
        input.setHint("Enter your email");
        builder.setView(input);

        builder.setPositiveButton("Reset", (dialog, which) -> {
            String email = input.getText().toString().trim();
            if (!email.isEmpty()) {
                resetPassword(email);
            } else {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteAccount() {
        if (user != null) {
            // Get user's email and password for reauthentication
            String email = user.getEmail();
            String password = "USER_PASSWORD"; // You need to get this from the user (e.g., prompt for password)

            if (email != null && password != null) {
                AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                // Re-authenticate the user
                user.reauthenticate(credential)
                        .addOnCompleteListener(reauthTask -> {
                            if (reauthTask.isSuccessful()) {
                                user.delete()
                                        .addOnCompleteListener(deleteTask -> {
                                            if (deleteTask.isSuccessful()) {
                                                Toast.makeText(SettingsActivity.this, "Account deleted successfully", Toast.LENGTH_LONG).show();

                                                // Clear stored preferences
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.clear();
                                                editor.apply();

                                                // Redirect to Login screen
                                                Intent intent = new Intent(SettingsActivity.this, Login_signup.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(SettingsActivity.this, "Failed to delete account: " + deleteTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(SettingsActivity.this, "Re-authentication failed. Please log in again.", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Re-authentication failed: No email found", Toast.LENGTH_LONG).show();
            }
        }
    }



}
