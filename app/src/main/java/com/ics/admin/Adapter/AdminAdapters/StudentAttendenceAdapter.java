package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.Model.Students;
import com.ics.admin.R;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.ArrayList;

import static com.ics.admin.BasicAdmin.Attendence.Addattendactivity.attendedstudentsList;

public class StudentAttendenceAdapter extends RecyclerView.Adapter<StudentAttendenceAdapter.MyViewHolder> {

    ArrayList<Students> studentsArrayList = new ArrayList<>();
    StudentAttendenceAdapter studentAttendenceAdapter;
    Activity activity;

    public StudentAttendenceAdapter(Activity activity, ArrayList<Students> studentsArrayList) {
        this.activity = activity;
        this.studentsArrayList = studentsArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public StudentAttendenceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onlyattendence, viewGroup, false);

        return new StudentAttendenceAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAttendenceAdapter.MyViewHolder myViewHolder, final int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);

        myViewHolder.student_pre_name.setText(""+studentsArrayList.get(i).getName());
//        myViewHolder.preaptext.setText(studentsArrayList.get(i).getPresent_or_not());
//        myViewHolder.preapcheck.setChecked(true);
        myViewHolder.preapcheck.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
//                    if (lastToast != null) lastToast.cancel();

                    studentsArrayList.get(i).setPresent_or_not("Absent");
                    attendedstudentsList.get(i).setPresent_or_not("Absent");
                    Toast.makeText(activity, studentsArrayList.get(i).getName() +" is "+ studentsArrayList.get(i).getPresent_or_not(), Toast.LENGTH_SHORT).show();
                }
                if (state.equals(State.RIGHT)) {
//                    if (lastToast != null) lastToast.cancel();
                    studentsArrayList.get(i).setPresent_or_not("Present");
                    attendedstudentsList.get(i).setPresent_or_not("Present");
                    Toast.makeText(activity, studentsArrayList.get(i).getName() +" is "+ studentsArrayList.get(i).getPresent_or_not(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(activity, ""+ studentsArrayList.get(i).getPresent_or_not(), Toast.LENGTH_SHORT).show();

                }
            }
        });
//        myViewHolder.preapcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    studentsArrayList.get(i).setPresent_or_not("Present");
//                    attendedstudentsList.get(i).setPresent_or_not("Present");
//                    Toast.makeText(activity, ""+ studentsArrayList.get(i).getPresent_or_not(), Toast.LENGTH_SHORT).show();
////                    myViewHolder.preaptext.setText(studentsArrayList.get(i).getPresent_or_not());
////                    studentAttendenceAdapter.notifyDataSetChanged();
////                    notifyDataSetChanged();
//                }else {
//                    studentsArrayList.get(i).setPresent_or_not("Absent");
//                    attendedstudentsList.get(i).setPresent_or_not("Absent");
//                    Toast.makeText(activity, ""+ studentsArrayList.get(i).getPresent_or_not(), Toast.LENGTH_SHORT).show();
////                    myViewHolder.preaptext.setText(studentsArrayList.get(i).getPresent_or_not());
////                    studentAttendenceAdapter.notifyDataSetChanged();
//
//                }
//            }
//        }
//        );


//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext() , AssignStudentActivity.class);
//                intent.putExtra("student_name",studentsArrayList.get(i).getName());
//                intent.putExtra("student_id",studentsArrayList.get(i).getId());
//                v.getContext().startActivity(intent);
//            }
//        });
//        myViewHolder.mainname.setText(""+studentsArrayList.get(i).getMainName() +": ");
//        myViewHolder.mainsubname.setText(""+studentsArrayList.get(i).get);

    }

    @Override
    public int getItemCount() {

        return studentsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView student_pre_name,preaptext,mainsubname;
        com.nightonke.jellytogglebutton.JellyToggleButton preapcheck;
        ImageView add_student;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            student_pre_name = (TextView) itemView.findViewById(R.id.student_pre_name);
            preapcheck =  itemView.findViewById(R.id.preapcheck);
//            preaptext =  itemView.findViewById(R.id.preaptext);
//            add_class=(TextView)itemView.findViewById(R.id.add_class);
//            add_student = (ImageView) itemView.findViewById(R.id.add_student);
//            mainname =  itemView.findViewById(R.id.mainname);
//            mainsubname = itemView.findViewById(R.id.mainsubname);

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
