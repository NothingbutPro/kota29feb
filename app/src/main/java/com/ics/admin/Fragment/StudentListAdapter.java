package com.ics.admin.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ics.admin.R;

import java.util.ArrayList;

class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {
    ArrayList Names;
    ArrayList Images;
    TabFragment activity;


    public StudentListAdapter(TabFragment activity, ArrayList Names,ArrayList Images) {
        this.activity = activity;
        this.Names = Names;
        this.Images = Images;

    }
    @NonNull
    @Override
    public StudentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentlist, viewGroup, false);

        return new StudentListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText("" + Names.get(i));


    }


    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
