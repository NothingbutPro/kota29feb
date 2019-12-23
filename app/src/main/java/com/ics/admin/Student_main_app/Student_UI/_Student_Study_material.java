package com.ics.admin.Student_main_app.Student_UI;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ics.admin.Adapter.ViewPagerAdapter;
import com.ics.admin.R;
import com.tompee.funtablayout.FlipTabAdapter;
import com.tompee.funtablayout.FunTabLayout;
import com.tompee.funtablayout.PopTabAdapter;

public class _Student_Study_material  extends Fragment{
    private ViewPager viewPager;
    FunTabLayout tabLayout;
    TextView studymattx,vodmattxt;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getActivity().setupViewPager(viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._student_study_material, container, false);
        studymattx = view.findViewById(R.id.studymattx);
        vodmattxt = view.findViewById(R.id.vodmattxt);

//        FunTabLayout tabLayout = (FunTabLayout) view.findViewById(R.id.tabLayout);
//        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
//        viewPager = view.findViewById(R.id.viewpager);
////        tabLayout.setupViewPager(viewPager);
////        tabLayout.addTab(tabLayout.newTab().setText("Study Material"));
////        tabLayout.addTab(tabLayout.newTab().setText("Video Material"));
////        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
////        final MyAdapter adapter = new MyAdapter(getActivity(),getChildFragmentManager(), tabLayout.getTabCount());
////        viewPager.setAdapter(adapter);
////        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
////        setupViewPager(viewPager);
////        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
//        FlipTabAdapter.Builder builder = new FlipTabAdapter.Builder(getActivity()).
//                setViewPager(viewPager).
//                setTabPadding(24, 24, 24, 24).
//                setTabTextAppearance(R.style.FlipTabText).
//                setTabBackgroundResId(R.drawable.ripple).
//                setTabIndicatorColor(Color.YELLOW).
//                setIconFetcher(this).
//                setIconDimension(80).
//                setDefaultIconColor(Color.WHITE);
//
////        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
////            @Override
////            public Fragment getItem(int position) {
////                // return fragment
////                if(position==0) {
////                    return new _Student_Study_material();
////                }else {
////                    return  new _Student_Video_Material();
////                }
////            }
////
////            @Override
////            public int getCount() {
////                // return fragment count
////                return 2;
////            }
////
////            @Override
////            public CharSequence getPageTitle(int position) {
////                // return fragment title
////                return "Study Material";
////            }
////        });
////        tabLayout.setUpWithAdapter(builder.build());
////        tabLayout.addTa
////        tabLayout.setTabVisibleCount(2);
//        tabLayout.setUpWithAdapter(builder.build());
        return view;
    }

//    private class MyAdapter extends FragmentPagerAdapter {
//
//        private Context myContext;
//        int totalTabs;
//
//        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
//            super(fm);
//            myContext = context;
//            this.totalTabs = totalTabs;
//        }
//
//        // this is for fragment tabs
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    _Student_Study_material homeFragment = new _Student_Study_material();
//                    return homeFragment;
//                case 1:
//                    _Student_Video_Material sportFragment = new _Student_Video_Material();
//                    return sportFragment;
//                default:
//                    return null;
//            }
//        }
//        // this counts total number of tabs
//        @Override
//        public int getCount() {
//            return totalTabs;
//        }
//    }


//    private void setupViewPager(ViewPager viewPager) {
//        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
////                return new _Student_Study_material();
//                return new _Student_Study_material();
////                if(position==0) {
////                    return new _Student_Study_material();
////                }else {
////                    return  new _Student_Video_Material();
////                }
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                String title = "";
//                switch (position) {
//                    case 0:
//                        title = "Study Material";
//                        break;
//                    case 1:
//                        title = "Video Material";
//                        break;
//                }
//                return title;
//            }
//        });
//    }


//    @Override
//    public int getIcon(int position) {
//        int resource = R.mipmap.ic_launcher;
//        switch (position) {
//            case 0:
//                resource = R.drawable.ic_video_library;
//                break;
//            case 1:
//                resource = R.drawable.ic_add;
//                break;
//
//        }
//        return resource;
//    }
//}
}