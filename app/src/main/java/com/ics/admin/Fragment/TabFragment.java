package com.ics.admin.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.ics.admin.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TabFragment extends Fragment {
    TextView save_Date;
    RecyclerView recyclerView;

    ArrayList Names = new ArrayList<>(Arrays.asList("Student Name", "Student Name", "Student Name", "Student Name", "Student Name","Student Name", "Student Name", "Student Name", "Student Name", "Student Name"));
     ArrayList<Integer> Images = new ArrayList<>(Arrays.asList(R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework,R.drawable.homework));
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        HorizontalPicker picker = (HorizontalPicker) view.findViewById(R.id.datePicker);
        save_Date = (TextView) view.findViewById(R.id.save_Date);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        StudentListAdapter studentListAdapter = new StudentListAdapter(TabFragment.this, Names,Images);
        recyclerView.setAdapter(studentListAdapter);

        picker
                .setDays(20)
                .setOffset(10)
                .setDateSelectedColor(Color.RED)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.RED)
               // .setTodayButtonTextColor(getColor(R.color.colorPrimary))
               // .setTodayDateTextColor(getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(Color.RED)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY)
              //  .setUnselectedDayTextColor(getColor(R.color.textColor))
                .showTodayButton(false)
                .init();
return view;


    }


}






