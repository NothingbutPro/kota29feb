package com.ics.admin.Student_main_app.Student_Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.R;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community_Data;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Public_Community_Chat_Adapter extends RecyclerView.Adapter<Public_Community_Chat_Adapter.MyViewHolder>{

    ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models ;
    Activity activity;
//    private ArrayList<_Student_Announcements_Model_Datas> studentsfeeList;

    public Public_Community_Chat_Adapter(Activity activity, ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models) {
        this.activity = activity;
        this.student_pay_fee_models = student_pay_fee_models;

    }

    @NonNull
    @Override
    public Public_Community_Chat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_comm_chat_layout, viewGroup, false);

        return new Public_Community_Chat_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Public_Community_Chat_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mymesstxt.setText("" +  student_pay_fee_models.get(i).getMessage());
        myViewHolder.yourmesstxt.setText("");
        myViewHolder.yourmesstxt.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount()
    {
        return student_pay_fee_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        TextView mymesstxt,yourmesstxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mymesstxt = (TextView) itemView.findViewById(R.id.mymesstxt);
            yourmesstxt = (TextView) itemView.findViewById(R.id.yourmesstxt);



        }
    }

}
