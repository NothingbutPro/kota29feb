package com.ics.admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ics.admin.BasicAdmin.Attendence.ViewAttendenceActivity;
import com.ics.admin.BasicAdmin.FeesStructure.AllStudentFeeActivity;
import com.ics.admin.BasicAdmin.FeesStructure.ViewAllFeesActivity;
import com.ics.admin.BasicAdmin.HomeWork.ViewAllAnnouncements;
import com.ics.admin.BasicAdmin.Masters.Batch.AddBatchActivity;
import com.ics.admin.BasicAdmin.Masters.Courses.AddCourseActivity;
import com.ics.admin.BasicAdmin.Attendence.Addattendactivity;
import com.ics.admin.BasicAdmin.Masters.Forclass.ClassViewActivity;
import com.ics.admin.BasicAdmin.Enquiry.AddEnquiryActivity;
import com.ics.admin.BasicAdmin.Enquiry.ViewEnquiryActivity;
import com.ics.admin.BasicAdmin.FeesStructure.AddFeesActivity;
import com.ics.admin.BasicAdmin.FeesStructure.PayFeesActivity;
import com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity;
import com.ics.admin.BasicAdmin.StudentDetails.AllStudentListActivity;
import com.ics.admin.BasicAdmin.StudentDetails.StudentAssinviewActivity;
import com.ics.admin.BasicAdmin.StudentDetails.Studentadnviewactivty;
import com.ics.admin.BasicAdmin.TeachersDetails.AddTeachersActivity;
import com.ics.admin.BasicAdmin.TeachersDetails.AnnouncementActivity;
import com.ics.admin.BasicAdmin.TeachersDetails.ViewTeachersActivity;
import com.ics.admin.BasicAdmin.UsersPermission.AddUserPermission;
import com.ics.admin.BasicAdmin.VideoPermission.AddVideoActivity;

import com.ics.admin.BasicAdmin.VideoPermission.VideoPackagessActivity;
import com.ics.admin.BasicAdmin.VideoPermission.VideosViewActivity;
import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.R;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.StudyMaterialActivity;
import com.ics.admin.BasicAdmin.Masters.Subjects.SubjectViewActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.MyViewHolder> {
    public static ArrayList<String> Myproducttiles = new ArrayList<>();
    public List<SubMenuPermissions> menuPermisssionList;
    //    public List<SubMenuPermissions> subMenuPermisssionsList = new ArrayList<>();
    SubPermissionAdapter subPermissionAdapter;
    int pos_try;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    private Context mContext;
    private SubMenuPermissions menuPermisssion;
    private RecyclerView subrec;

    public SubMenuAdapter(Context context, ArrayList<SubMenuPermissions> menuPermisssion) {
        mContext = context;
        this.menuPermisssionList = menuPermisssion;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subpermissionexpand, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        menuPermisssion = menuPermisssionList.get(position);
        this.pos_try = position;

        if(menuPermisssionList.get(position).getSubmenu().equals("Add Teacher's"))
        {
            Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
            holder.namestff.setText("Add Staff");
            menuPermisssionList.get(position).setSubmenu("Add Staff");
        }else {
            Toast.makeText(mContext, "False", Toast.LENGTH_SHORT).show();
            holder.namestff.setText(menuPermisssionList.get(position).getSubmenu());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext() , AddSubjectActivity.class);
//                intent.putExtra("mymenuid","a"+position);
//                v.getContext().startActivity(intent);
  //              Intent intent=new Intent(mContext, AddBatchActivity.class);
//                intent.putExtra("mymenuid","a"+position);
//                mContext.startActivity(intent);

                SubMenuPermissions namestff = menuPermisssionList.get(position);
                int getindex = menuPermisssionList.indexOf(namestff);
                if(namestff.getSubmenu().contains("Add Batch"))
                {
                    Intent intent= new Intent(v.getContext(), AddBatchActivity.class);
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                } else  if(namestff.getSubmenu().contains("Add Subject"))
                {
                    Intent intent0= new Intent(v.getContext(), SubjectViewActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();

                }else  if(namestff.getSubmenu().contains("Add Class"))
                {
                    Intent intent1= new Intent(v.getContext(), ClassViewActivity.class);
                        v.getContext().startActivity(intent1);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();

                }else  if(namestff.getSubmenu().contains("Study Material"))
                {
                    Intent intent0= new Intent(v.getContext(), StudyMaterialActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();

                }else  if(namestff.getSubmenu().contains("Add Course")) {
                    Intent intent0 = new Intent(v.getContext(), AddCourseActivity.class);
                    v.getContext().startActivity(intent0);
                    Toast.makeText(v.getContext(), "" + namestff, Toast.LENGTH_SHORT).show();
                }
            //++++++++++++++++++++++++For students details+++++++++++++++++++++++++
                else  if(namestff.getSubmenu().contains("Add Student"))
                {
                    Intent intent0= new Intent(v.getContext(), Studentadnviewactivty.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                }
                else  if(namestff.getSubmenu().contains("Assign Student"))
                {
                    Intent intent0= new Intent(v.getContext(), StudentAssinviewActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                }  else  if(namestff.getSubmenu().contains("Student List"))
                {
                    Intent intent0= new Intent(v.getContext(), AllStudentListActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                }

                //++++++++++++++++++++++++++++++++++++++++++++++dfg++++++++++++++++++++
                else
                    if(namestff.getSubmenu().contains("Add Work"))
                {
                    Intent intent0= new Intent(v.getContext(), HomeWorkActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                } else
                    if(namestff.getSubmenu().contains("Add Attendance"))
                {
                    Intent intent0= new Intent(v.getContext(), Addattendactivity.class);

                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                }else
                    if(namestff.getSubmenu().contains("Attendance List"))
                {
                    Intent intent0= new Intent(v.getContext(), ViewAttendenceActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
                }
                    else
                    if(namestff.getSubmenu().contains("Add Video"))
                {
                    Intent intent0= new Intent(v.getContext(), AddVideoActivity.class);
                        v.getContext().startActivity(intent0);
                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();

                }    else
                    if(namestff.getSubmenu().contains("Video Package"))
                {
                    Intent intent0= new Intent(v.getContext(), VideoPackagessActivity.class);
                        v.getContext().startActivity(intent0);
                }
                    else
                    if(namestff.getSubmenu().contains("Video Details"))
                {
                    Intent intent0= new Intent(v.getContext(), VideosViewActivity.class);
                        v.getContext().startActivity(intent0);
                }

                    else
                    if(namestff.getSubmenu().contains("Add Permission"))
                {
                    Intent intent0= new Intent(v.getContext(), AddUserPermission.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Add Enquiry"))
                {
                    Intent intent0= new Intent(v.getContext(), AddEnquiryActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Enquiry Details"))
                {
                    Intent intent0= new Intent(v.getContext(), ViewEnquiryActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Add Fee"))
                {
                    Intent intent0= new Intent(v.getContext(), AddFeesActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Pay Fee"))
                {
                    Intent intent0= new Intent(v.getContext(), PayFeesActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Student Fee Details"))
                {
                    Intent intent0= new Intent(v.getContext(), AllStudentFeeActivity.class);
                        v.getContext().startActivity(intent0);
                }
                    else
                    if(namestff.getSubmenu().contains("View Fees"))
                {
                    Intent intent0= new Intent(v.getContext(), ViewAllFeesActivity.class);
                        v.getContext().startActivity(intent0);
                }
                    else
                    if(namestff.getSubmenu().contains("Add Staff"))
                {
                    Intent intent0= new Intent(v.getContext(), AddTeachersActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().contains("Teacher List"))
                {
                    Intent intent0= new Intent(v.getContext(), ViewTeachersActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().equals("Announcement"))
                {
                    Intent intent0= new Intent(v.getContext(), AnnouncementActivity.class);
                        v.getContext().startActivity(intent0);
                }else
                    if(namestff.getSubmenu().equals("View Announcements"))
                {
                    Intent intent0= new Intent(v.getContext(), ViewAllAnnouncements.class);
                        v.getContext().startActivity(intent0);
                }

//                else  if()
//                switch (getindex)
//                {
//                    case 0:
//                        Intent intent= new Intent(v.getContext(), AddBatchActivity.class);
//                        v.getContext().startActivity(intent);
//                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 1:
//                        Intent intent0= new Intent(v.getContext(), SubjectViewActivity.class);
//                        v.getContext().startActivity(intent0);
//                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 2:
//
//                        Intent intent1= new Intent(v.getContext(), ClassViewActivity.class);
//                        v.getContext().startActivity(intent1);
//                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 3:
//                        Intent intent2= new Intent(v.getContext(), StudyMaterialActivity.class);
//                        v.getContext().startActivity(intent2);
//                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 4:
//                        Intent intent3= new Intent(v.getContext(), AddCourseActivity.class);
//                        v.getContext().startActivity(intent3);
//                        Toast.makeText(v.getContext(), ""+namestff, Toast.LENGTH_SHORT).show();
//                        break;
//
//
//                }
            }
        });
        StrictMode.setVmPolicy(builder.build());

    }

    @Override
    public int getItemCount() {
        return menuPermisssionList.size();
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView stffimg;
        TextView namestff, emailstff, stffid;
        ToggleButton maintoggel;

        public MyViewHolder(View itemView) {
            super(itemView);
            //   mname = (TextView) itemView.findViewById(R.id.mname);
            // manu_img =  itemView.findViewById(R.id.manu_img);
            namestff = itemView.findViewById(R.id.names);
            maintoggel = itemView.findViewById(R.id.maintoggel);

            //     adddes =  itemView.findViewById(R.id.adddes);


        }
    }
}



  
