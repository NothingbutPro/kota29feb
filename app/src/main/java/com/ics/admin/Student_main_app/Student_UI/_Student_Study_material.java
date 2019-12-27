package com.ics.admin.Student_main_app.Student_UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.ics.admin.R;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.StudentDashboardActivity;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.tompee.funtablayout.FunTabLayout;

public class _Student_Study_material  extends Fragment{
    private ViewPager viewPager;
    FunTabLayout tabLayout;
//    TextView studymattx,vodmattxt;
    _Student_Study_Material_Adapter student_study_material_adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._student_study_material, container, false);
        Toast.makeText(getActivity(), "CREATED sTUDENT STYUDY ", Toast.LENGTH_SHORT).show();
//        studymattx = view.findViewById(R.id.studymattx);
//        vodmattxt = view.findViewById(R.id.vodmattxt);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("Study Material", _Student_PDF_Material_Fragment.class)
                .add("Videos Packages", _Student_Video_Material_Fragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getActivity(), "Page selected"+position, Toast.LENGTH_SHORT).show();
//                Fragment page = adapter.getPage(position);
//                if(position==0) {
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_PDF_Material_Fragment()).commit();
//                }else if(position==1){
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_Video_Material_Fragment()).commit();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("Study Material", _Student_PDF_Material_Fragment.class)
                .add("Videos Packages", _Student_Video_Material_Fragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getActivity(), "Page selected"+position, Toast.LENGTH_SHORT).show();
//                if(position==0) {
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_PDF_Material_Fragment()).commit();
//                }else if(position==1){
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_Video_Material_Fragment()).commit();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

//    @Override
//    public void onAttachFragment(Fragment childFragment) {
//        Toast.makeText(getActivity(), "SAfdgdfg", Toast.LENGTH_SHORT).show();
//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
//                .add("Study Material", _Student_PDF_Material_Fragment.class)
//                .add("Videos Packages", _Student_Video_Material_Fragment.class)
//                .create());
//
//        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
//        viewPager.setAdapter(adapter);
//
//        SmartTabLayout viewPagerTab = (SmartTabLayout) getView().findViewById(R.id.viewpagertab);
//        viewPagerTab.setViewPager(viewPager);
//        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(getActivity(), "Page selected"+position, Toast.LENGTH_SHORT).show();
////                if(position==0) {
////                    FragmentManager fragmentManager = getChildFragmentManager();
////                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_PDF_Material_Fragment()).commit();
////                }else if(position==1){
////                    FragmentManager fragmentManager = getChildFragmentManager();
////                    fragmentManager.beginTransaction().replace(R.id.student_frame_container,new _Student_Video_Material_Fragment()).commit();
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        super.onAttachFragment(childFragment);
//    }

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
//                    _Student_Video_Material_Fragment sportFragment = new _Student_Video_Material_Fragment();
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
////                    return  new _Student_Video_Material_Fragment();
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