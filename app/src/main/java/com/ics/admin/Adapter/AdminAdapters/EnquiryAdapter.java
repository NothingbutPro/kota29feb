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
import com.ics.admin.CommonJavaClass.DeleteDialog;
import com.ics.admin.Model.Enqiries;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;

import java.util.ArrayList;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.MyViewHolder>{
    ArrayList<Enqiries> classArrayList = new ArrayList<>();
    Activity activity;

    public EnquiryAdapter(Activity activity, ArrayList<Enqiries> classArrayList) {
        this.activity=activity;
        this.classArrayList = classArrayList;

    }

    @NonNull
    @Override
    public EnquiryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.enquiryadapter, viewGroup, false);

        return new EnquiryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.enq_nummber.setText(""+classArrayList.get(i).getMobile());
        myViewHolder.classname.setText(classArrayList.get(i).getClass_());
        myViewHolder.batchname.setText(classArrayList.get(i).getCourse());
        myViewHolder.homedate.setText(classArrayList.get(i).getFollowupDate());
        myViewHolder.daysofwork.setText(classArrayList.get(i).getFollowupType());
        myViewHolder.inq_type.setText(classArrayList.get(i).getEnquiryType());
        myViewHolder.enq_remark.setText(classArrayList.get(i).getRemark());
        myViewHolder.enq_name.setText(classArrayList.get(i).getName());
        myViewHolder.enq_by_txt.setText(new Shared_Preference().getFactName(activity));
        myViewHolder.inid.setText("by");
        myViewHolder.delhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteDialog().DeleteDialog("enquiry_id",activity,classArrayList.get(i).getEnquiryId() , "http://ihisaab.in/school_lms/api/delete_enquiry");
//                new CommunityFragment.DELETStuff("enquiry_id",activity,classArrayList.get(i).getEnquiryId() , "http://ihisaab.in/school_lms/api/delete_enquiry").execute();
            }
        });
        myViewHolder.source_id.setText(classArrayList.get(i).getEnquiryType());
        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enquiry_by = myViewHolder.newenquiry_by.getText().toString();
                String mobile = myViewHolder.newmobile.getText().toString();
                String enquiry_type = myViewHolder.newenquiry_type.getText().toString();
                String followup_type = myViewHolder.newfollowup_type.getText().toString();
                String followup_date = myViewHolder.newfollowup_date.getText().toString();
                String remark = myViewHolder.newremark.getText().toString();
                String url = "http://ihisaab.in/school_lms/api/update_enquiry";
                new EditStuffs("update_enquiry", activity, classArrayList.get(i).getClassx(),classArrayList.get(i).getEnquiryId(),enquiry_by,mobile,enquiry_type, followup_type,classArrayList.get(i).getCourse(),followup_date,remark,url,"byenquiry").execute();
            }
        });
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView enq_nummber,factname,homedate,daysofwork,classname,batchname,inid,enq_name,inq_type,enq_remark,source_id,enq_by_txt;
        Button delhome,edit_btn_save,edit_btn;
        EditText newenquiry_by,newmobile,newenquiry_type,newfollowup_type,newfollowup_date,newremark;
        LinearLayout hideli;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            enq_nummber = (TextView) itemView.findViewById(R.id.enq_nummber);
            delhome =  itemView.findViewById(R.id.delhome);
            enq_name =  itemView.findViewById(R.id.enq_name);
            batchname = (TextView) itemView.findViewById(R.id.batchname);
            inid = (TextView) itemView.findViewById(R.id.inid);
            factname = (TextView) itemView.findViewById(R.id.factname);
            homedate = (TextView) itemView.findViewById(R.id.homedate);
            daysofwork = (TextView) itemView.findViewById(R.id.daysofwork);
            classname = (TextView) itemView.findViewById(R.id.classname);
            inq_type = (TextView) itemView.findViewById(R.id.inq_type);
            enq_remark = (TextView) itemView.findViewById(R.id.enq_remark);
            source_id = (TextView) itemView.findViewById(R.id.source_id);
            enq_by_txt = (TextView) itemView.findViewById(R.id.enq_by_txt);

            // For edit
            newenquiry_by = itemView.findViewById(R.id.newenquiry_by);
            newmobile = itemView.findViewById(R.id.newmobile);
            newenquiry_type = itemView.findViewById(R.id.newenquiry_type);
            newfollowup_type = itemView.findViewById(R.id.newfollowup_type);
            newfollowup_date = itemView.findViewById(R.id.newfollowup_date);
            newremark = itemView.findViewById(R.id.newremark);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            hideli = itemView.findViewById(R.id.hideli);
        }

    }
}
