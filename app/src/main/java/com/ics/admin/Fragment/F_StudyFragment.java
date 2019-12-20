package com.ics.admin.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ics.admin.R;

import java.util.ArrayList;
import java.util.Arrays;


public class F_StudyFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList Names = new ArrayList<>(Arrays.asList("Study Material", " Study Material", "Study Material", "Study Material","Study Material"));
    ArrayList<Integer> Images = new ArrayList<>(Arrays.asList(R.drawable.pdf,R.drawable.pdf,R.drawable.pdf,R.drawable.pdf,R.drawable.pdf));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_f_study, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
//        StudyMaterialAdapter studyMaterialAdapter = new StudyMaterialAdapter(F_StudyFragment.this, Names,Images);
//        recyclerView.setAdapter(studyMaterialAdapter);

        return view;
    }

}
