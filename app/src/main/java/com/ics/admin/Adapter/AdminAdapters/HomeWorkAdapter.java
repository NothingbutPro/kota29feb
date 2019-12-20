package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.Model.HomeWorks;
import com.ics.admin.R;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyViewHolder>{
    ArrayList<HomeWorks> classArrayList = new ArrayList<>();
    Activity activity;

    public HomeWorkAdapter(Activity activity, ArrayList<HomeWorks> classArrayList) {
        this.activity=activity;
        this.classArrayList = classArrayList;

    }

    @NonNull
    @Override
    public HomeWorkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeworkview, viewGroup, false);

        return new HomeWorkAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWorkAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.homework.setText(""+classArrayList.get(i).getHomework());
        myViewHolder.factname.setText(""+classArrayList.get(i).getTeacher());
        myViewHolder.homedate.setText(""+classArrayList.get(i).getWorkDate());
        myViewHolder.daysofwork.setText(""+classArrayList.get(i).getDaysforwok());
        myViewHolder.classname.setText(""+classArrayList.get(i).getClass_());
        myViewHolder.batchname.setText(""+classArrayList.get(i).getBatch());
        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newdaysforwok = myViewHolder.newdaysforwok.getText().toString();
                String newwork_type = myViewHolder.newwork_type.getText().toString();
                String newhomeworkdate = myViewHolder.newhomeworkdate.getText().toString();
                String new_homework = myViewHolder.new_homework.getText().toString();
                String url = "http://ihisaab.in/school_lms/api/update_homework";
                new EditStuffs("update_homework", activity, classArrayList.get(i).getClassId(),classArrayList.get(i).getId(),newdaysforwok,newwork_type,newhomeworkdate, new_homework,url,classArrayList.get(i).getBatchId(),classArrayList.get(i).getTeacherId()).execute();
            }
        });
        myViewHolder.edithome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myViewHolder.hideli.getVisibility() == View.VISIBLE)
                {
                    myViewHolder.hideli.setVisibility(View.GONE);
                }else {
                    myViewHolder.hideli.setVisibility(View.VISIBLE);
                }
            }
        });

        myViewHolder.delhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeleteDialog().DeleteDialog("id",activity , classArrayList.get(i).getId() , "http://ihisaab.in/school_lms/api/delete_homework");
//                new CommunityFragment.DELETStuff("id",activity , classArrayList.get(i).getId() , "http://ihisaab.in/school_lms/api/delete_homework").execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView homework,factname,homedate,daysofwork,classname,batchname;
        Button delhome,edithome,edit_btn_save;
        LinearLayout hideli;
        EditText newhomeworkdate,newwork_type,newdaysforwok,new_homework;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            homework = (TextView) itemView.findViewById(R.id.homework);
            delhome = itemView.findViewById(R.id.delhome);
            batchname = (TextView) itemView.findViewById(R.id.batchname);
            batchname = (TextView) itemView.findViewById(R.id.batchname);
            factname = (TextView) itemView.findViewById(R.id.factname);
            homedate = (TextView) itemView.findViewById(R.id.homedate);
            daysofwork = (TextView) itemView.findViewById(R.id.daysofwork);
            classname = (TextView) itemView.findViewById(R.id.classname);
            ///+++++++++++++++++++++++++++++++++++For Edit++++++++++++++++
            hideli = itemView.findViewById(R.id.hideli);
            newhomeworkdate = itemView.findViewById(R.id.newhomeworkdate);
            newwork_type = itemView.findViewById(R.id.newwork_type);
            newdaysforwok = itemView.findViewById(R.id.newdaysforwok);
            new_homework = itemView.findViewById(R.id.new_homework);
            edithome = itemView.findViewById(R.id.edithome);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            //++++++++++++++++

        }

    }
}