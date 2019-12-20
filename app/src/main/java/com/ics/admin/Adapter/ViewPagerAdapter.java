package com.ics.admin.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ics.admin.Fragment.F_StudyFragment;
import com.ics.admin.Fragment.TabFragment;
import com.ics.admin.Fragment.TimeTableFragment;
import com.ics.admin.Fragment.VideoFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager manager) {

        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new TabFragment();
        } else if (position == 1) {
            fragment = new F_StudyFragment();
        } else if (position == 2) {
            fragment = new VideoFragment();
        } else if (position == 3) {
            fragment = new TimeTableFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {

        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Attendance";
        } else if (position == 1) {
            title = "Study";
        } else if (position == 2) {
            title = "Video";
        }
     else if (position == 3) {
        title = "Time Table";
    }
        return title;
    }
}
