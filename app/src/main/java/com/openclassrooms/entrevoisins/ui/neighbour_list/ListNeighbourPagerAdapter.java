package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Pager adapter for displaying NeighbourFragment and FavoriteFragment in the ViewPager.
 */
public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    /**
     * Constructor for ListNeighbourPagerAdapter.
     * @param fm The FragmentManager instance.
     */
    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NeighbourFragment.newInstance();
        } else if (position == 1) {
            return FavoriteFragment.newInstance();
        }
        return null;
    }

    /**
     * @return the number of pages
     */
    @Override
    public int getCount() {
        return 2;
    }
}