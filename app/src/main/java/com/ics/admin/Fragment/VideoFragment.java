package com.ics.admin.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ics.admin.Adapter.VideolAdapter;
import com.ics.admin.R;

import java.util.ArrayList;
import java.util.Arrays;

public class VideoFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList Names = new ArrayList<>(Arrays.asList("Video Material", " Video Material", "Video Material", "Video Material","Video Material"));
    ArrayList<Integer> Images = new ArrayList<>(Arrays.asList(R.drawable.video,R.drawable.video,R.drawable.video,R.drawable.video,R.drawable.video));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        VideolAdapter videolAdapter = new VideolAdapter(VideoFragment.this, Names,Images);
        recyclerView.setAdapter(videolAdapter);

        return view;
    }

}
