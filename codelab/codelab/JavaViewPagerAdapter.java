package com.example.codelab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class JavaViewPagerAdapter extends FragmentStateAdapter {

    public JavaViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new JavaLessonTabFragment();
        } else {
            return new JavaCompilerTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Two fragments
    }
}
