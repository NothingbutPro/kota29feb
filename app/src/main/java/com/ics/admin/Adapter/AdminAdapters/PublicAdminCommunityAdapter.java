package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.Student_Adapters.Public_Community_Chat_Adapter;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community_Data;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicAdminCommunityAdapter extends RecyclerView.Adapter<PublicAdminCommunityAdapter.MyViewHolder>{

    ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models ;
    Activity activity;
//    private ArrayList<_Student_Announcements_Model_Datas> studentsfeeList;

    public PublicAdminCommunityAdapter(Activity activity, ArrayList<_Student_Chat_Public_Community_Data> student_pay_fee_models) {
        this.activity = activity;
        this.student_pay_fee_models = student_pay_fee_models;

    }

    @NonNull
    @Override
    public PublicAdminCommunityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_comm_chat_layout, viewGroup, false);

        return new PublicAdminCommunityAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicAdminCommunityAdapter.MyViewHolder myViewHolder, int i) {

        if(student_pay_fee_models.get(i).getType().equals("1")) {
            myViewHolder.left_mess.setText("" + student_pay_fee_models.get(i).getMessage());
//            myViewHolder.yourmesstxt.setText("");
//            myViewHolder.mymesstxt.setVisibility(View.GONE);
        }else {
            myViewHolder.right_message.setText("" + student_pay_fee_models.get(i).getMessage());
//            myViewHolder.yourmesstxt.setText("");
//            myViewHolder.yourmesstxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return student_pay_fee_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        TextView left_mess,right_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            left_mess = (TextView) itemView.findViewById(R.id.left_mess);
            right_message = (TextView) itemView.findViewById(R.id.right_message);



        }
    }

}
