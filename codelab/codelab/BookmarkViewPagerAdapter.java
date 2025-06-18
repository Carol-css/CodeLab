package com.example.codelab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookmarkViewPagerAdapter extends FragmentStateAdapter {

    private final String[] categories = {"All", "Java", "Python", "C++", "Javascript"};

    public BookmarkViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return BookmarkListFragment.newInstance(categories[position]); 
    }


    @Override
    public int getItemCount() {
        return categories.length;
    }
}
