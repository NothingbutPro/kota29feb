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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _MY_Attendence_Adapter extends RecyclerView.Adapter<_MY_Attendence_Adapter.MyViewHolder>{

    ArrayList<_My_Student_Attendence_Data> student_pay_fee_models ;
    Activity activity;
//    private ArrayList<_Student_Announcements_Model_Datas> studentsfeeList;

    public _MY_Attendence_Adapter(Activity activity, ArrayList<_My_Student_Attendence_Data> student_pay_fee_models) {
        this.activity = activity;
        this.student_pay_fee_models = student_pay_fee_models;

    }

    @NonNull
    @Override
    public _MY_Attendence_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._student_get_fee, viewGroup, false);

        return new _MY_Attendence_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull _MY_Attendence_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder._st_total_amount.setText("" +  student_pay_fee_models.get(i).getAttendance());
        myViewHolder._st_payby.setText("" +  student_pay_fee_models.get(i).getBatch());
        myViewHolder._st_create_date.setText("" +  student_pay_fee_models.get(i).getDate());
        myViewHolder._st_total_payamount.setText("" +  student_pay_fee_models.get(i).getName());

    }

    @Override
    public int getItemCount()
    {
        return student_pay_fee_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        TextView _st_total_amount,_st_payby,_st_create_date,_st_total_payamount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _st_total_amount = (TextView) itemView.findViewById(R.id._st_total_amount);
            _st_payby = (TextView) itemView.findViewById(R.id._st_payby);
            _st_create_date = (TextView) itemView.findViewById(R.id._st_create_date);
            _st_total_payamount = (TextView) itemView.findViewById(R.id._st_total_payamount);
//            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);


        }
    }

}
