package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community_Data;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Public_Community_Admin_Chat_Adapter extends RecyclerView.Adapter<Public_Community_Admin_Chat_Adapter.MyViewHolder>{

    ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models ;
    Activity activity;
//    private ArrayList<_Student_Announcements_Model_Datas> studentsfeeList;

    public Public_Community_Admin_Chat_Adapter(Activity activity, ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models) {
        this.activity = activity;
        this.student_pay_fee_models = student_pay_fee_models;

    }

    @NonNull
    @Override
    public Public_Community_Admin_Chat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_comm_chat_layout, viewGroup, false);

        return new Public_Community_Admin_Chat_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Public_Community_Admin_Chat_Adapter.MyViewHolder myViewHolder, int i) {
//        myViewHolder.mymesstxt.setText("" +  student_pay_fee_models.get(i).getMessage());
//        myViewHolder.yourmesstxt.setText("");
//        myViewHolder.yourmesstxt.setVisibility(View.GONE);
        if(student_pay_fee_models.get(i).getType().equals("1")) {

            myViewHolder.left_mess.setText("" + student_pay_fee_models.get(i).getMessage());
//            myViewHolder.yourmesstxt.setText("");
            myViewHolder.right_name.setText(student_pay_fee_models.get(i).getStudent_name());
            myViewHolder.left_time.setText(student_pay_fee_models.get(i).getDateTime());
            myViewHolder.right_mess_li.setVisibility(View.GONE);
//            myViewHolder.left_name.setText();
        }else {
            myViewHolder.right_mess.setText("" + student_pay_fee_models.get(i).getMessage());
//            myViewHolder.yourmesstxt.setText("");
            myViewHolder.right_name.setText(student_pay_fee_models.get(i).getAdmin_name());
            if(!new Shared_Preference().getId(activity).equals(student_pay_fee_models.get(i).getUserId())) {
                myViewHolder.right_name.setText(student_pay_fee_models.get(i).getAdmin_name());
            }
            myViewHolder.right_time.setText(student_pay_fee_models.get(i).getDateTime());
            myViewHolder.left_mess_li.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount()
    {
        return student_pay_fee_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        TextView left_name,left_mess ,left_time,right_time,right_name,right_mess;
        LinearLayout left_mess_li,right_mess_li;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


//            left_name = (TextView) itemView.findViewById(R.id.left_name);
            left_mess = (TextView) itemView.findViewById(R.id.left_mess);
            left_name = (TextView) itemView.findViewById(R.id.left_name);
            left_time = (TextView) itemView.findViewById(R.id.left_time);
            right_time = (TextView) itemView.findViewById(R.id.right_mess_time);
            right_name = (TextView) itemView.findViewById(R.id.right_name);
            right_mess = (TextView) itemView.findViewById(R.id.right_message);
            left_mess_li = (LinearLayout) itemView.findViewById(R.id.left_mess_li);
            right_mess_li = (LinearLayout) itemView.findViewById(R.id.right_mess_li);


        }
    }

}
