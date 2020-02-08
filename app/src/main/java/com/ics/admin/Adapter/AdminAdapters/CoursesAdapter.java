package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.CommonJavaClass.DeleteDialog;
import com.ics.admin.Model.ABBCoursemodel;
import com.ics.admin.R;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    ArrayList<ABBCoursemodel> batchArrayList = new ArrayList<>();
    Activity activity;
    private View view;

    public CoursesAdapter(Activity activity, ArrayList<ABBCoursemodel> batchArrayList) {
        this.activity = activity;
        this.batchArrayList = batchArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public CoursesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_adapter_xml, viewGroup, false);

        return new CoursesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.MyViewHolder myViewHolder, int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);

        myViewHolder.name.setText(""+batchArrayList.get(i).getCourse());
        myViewHolder.add_class.setText(""+batchArrayList.get(i).getClass_id());
        myViewHolder.mainname.setText(""+batchArrayList.get(i).getMainname());
        myViewHolder.mainsubname.setText(""+batchArrayList.get(i).getSubmainname());
        myViewHolder.delbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ihisaab.in/school_lms/Adminapi/deleteCourselist";
                new DeleteDialog().DeleteDialog("course_id", activity, batchArrayList.get(i).getUserId(), url);
//                new CommunityFragment.DELETStuff("Course_id",activity,batchArrayList.get(i).getUserId(), url).execute();
            }
        });
        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myedit = myViewHolder.editall.getText().toString();
                String url = "http://ihisaab.in/school_lms/Adminapi/update_Course";
                new EditStuffs("update_Course", activity, batchArrayList.get(i).getActual_class_id(),batchArrayList.get(i).getUserId(),myedit, url).execute();
            }
        });
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent()
                View vx = view;
                try {
                    view = v.getRootView().findViewById(R.id.hide_li);
                    ((ViewGroup) myViewHolder.hide_li.getParent()).removeView(myViewHolder.hide_li);
                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(view.getContext())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setCornerRadius(25)
                            .setFooterView(view)
                            .setHeaderView(R.layout.edit_layout_header);
//                dialog.dismiss();
                    CFAlertDialog cfAlertDialog = builder.create();
                    cfAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    cfAlertDialog.show();
                }catch (Exception e)
                {
                    view =vx;
                    if(view !=null) {
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(view.getContext())
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setCornerRadius(25)
                                .setFooterView(view)
                                .setHeaderView(R.layout.edit_layout_header);
//                dialog.dismiss();
                        CFAlertDialog cfAlertDialog = builder.create();
                        cfAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        cfAlertDialog.show();
                        e.printStackTrace();
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return batchArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView name,add_class ,mainname,mainsubname;
        ImageView delbatch,edit_btn;
        EditText editall;
        LinearLayout hide_li;
        Button edit_btn_save;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            add_class=(TextView)itemView.findViewById(R.id.add_class);
            mainname=(TextView)itemView.findViewById(R.id.mainname);
            mainsubname=(TextView)itemView.findViewById(R.id.mainsubname);
//            add_student = (ImageView) itemView.findViewById(R.id.add_student);
            delbatch = itemView.findViewById(R.id.delbatch);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            hide_li = itemView.findViewById(R.id.hide_li);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            editall = itemView.findViewById(R.id.editall);

//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(activity, StudentActivity.class);
//                    activity.startActivity(intent1);
//                }
//            });
//            add_student.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(activity, AddStudentActivity.class);
//                    activity.startActivity(intent);
//                    activity.finish();
//                }
//            });


        }
    }
}