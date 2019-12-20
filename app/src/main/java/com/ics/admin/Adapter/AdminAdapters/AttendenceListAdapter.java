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
import com.ics.admin.Model.AttendenceList;
import com.ics.admin.Model.Enqiries;
import com.ics.admin.R;

import java.util.ArrayList;

public class AttendenceListAdapter extends RecyclerView.Adapter<AttendenceListAdapter.MyViewHolder>{
    ArrayList<AttendenceList> classArrayList = new ArrayList<>();
    Activity activity;

    public AttendenceListAdapter(Activity activity, ArrayList<AttendenceList> classArrayList) {
        this.activity=activity;
        this.classArrayList = classArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allattendencelist, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.attendstudent.setText(""+classArrayList.get(i).getStudent_name());
        myViewHolder.attendclass.setText(""+classArrayList.get(i).getClass_());
        myViewHolder.attendencetxt.setText(""+classArrayList.get(i).getAttendance());
        myViewHolder.attendbatch.setText(""+classArrayList.get(i).getBatch());
        myViewHolder.attenddate.setText(""+classArrayList.get(i).getDate());


    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView attendstudent,attendclass,attendencetxt,attendbatch,attenddate;
        Button delhome,edit_btn_save,edit_btn;
        LinearLayout hideli;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            attendstudent = (TextView) itemView.findViewById(R.id.attendstudent);
            attendclass = (TextView) itemView.findViewById(R.id.attendclass);
            attendencetxt = (TextView) itemView.findViewById(R.id.attendencetxt);
            attendbatch = (TextView) itemView.findViewById(R.id.attendbatch);
            attenddate = (TextView) itemView.findViewById(R.id.attenddate);

        }

    }
}