package com.ics.admin.Fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.R;

import java.util.ArrayList;
import java.util.Arrays;

public class BatchFragment extends Fragment {
   RecyclerView recyclerView1;
    com.github.clans.fab.FloatingActionButton fab;

    ArrayList Names = new ArrayList<>(Arrays.asList("Class 10th A", "Class 10th B", "Class 10th C", "Class 10th D"));
   // ArrayList<Integer> Images = new ArrayList<>(Arrays.asList(R.drawable.girl,R.drawable.ic_community));




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_batch, container, false);

        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        recyclerView1=(RecyclerView)view.findViewById(R.id.recyclerView1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), AddStudentActivity.class);
//                startActivity(intent);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

//           BatchAdapter batchAdapter = new BatchAdapter(getActivity(),Names);


//          recyclerView1.setAdapter(batchAdapter); // set the Adapter to RecyclerView



        return  view;


    }
}

