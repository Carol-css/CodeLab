package com.example.codelab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CViewPagerAdapter extends FragmentStateAdapter {

    public CViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CLessonTabFragment();
        } else {
            return new CCompilerTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Two fragments
    }
}
