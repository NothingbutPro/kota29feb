package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.Model.VideoPackages;
import com.ics.admin.R;

import java.util.ArrayList;


public class VideoPackagesAdapter extends RecyclerView.Adapter<VideoPackagesAdapter.MyViewHolder> {

    ArrayList<VideoPackages> batchArrayList = new ArrayList<>();
    Activity activity;

    public VideoPackagesAdapter(Activity activity, ArrayList<VideoPackages> batchArrayList) {
        this.activity = activity;
        this.batchArrayList = batchArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public VideoPackagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videosonlyxml, viewGroup, false);

        return new VideoPackagesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPackagesAdapter.MyViewHolder myViewHolder, int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);
        VideoPackages videoPackages = batchArrayList.get(i);

        myViewHolder.videotits.setText(""+batchArrayList.get(i).getTitle());
        myViewHolder.batchvid.setText(""+batchArrayList.get(i).getCourse());
//        myViewHolder.selvids.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String title = myViewHolder.newtitle.getText().toString();
//                String description = myViewHolder.new_description.getText().toString();
//                String create_date = myViewHolder.new_create_date.getText().toString();
//                String expire_date = myViewHolder.new_expire_date.getText().toString();
//                String amount = myViewHolder.new_amount.getText().toString();
//                String discount_amount = myViewHolder.new_discount_amount.getText().toString();
//                String video_time = myViewHolder.new_video_time.getText().toString();
//                String url = " http://ihisaab.in/school_lms/api/updatevideoPackage";
//                new EditStuffs("updatevideoPackage", activity,batchArrayList.get(i).getClassId(),batchArrayList.get(i).getId(),title,description,create_date, expire_date,amount,discount_amount,video_time,url).execute();
//            }
//        });
        myViewHolder.checvidpackag.setChecked(true);
//        myViewHolder.editvidepckas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(myViewHolder.hide_li.getVisibility() ==View.VISIBLE)
//                {
//                    myViewHolder.hide_li.setVisibility(View.GONE);
//                }else {
//                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        myViewHolder.checvidpackag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    videoPackages.setPackage_select("Checked");
//                    if(Selected_packages !=null)
//                    {
//
////                        Selected_packages = batchArrayList.get(i).getId();
//                        allselecctedvid.setText(batchArrayList.get(i).getTitle());
//                    }else {
//                        Selected_packages = Selected_packages +","+ batchArrayList.get(i).getId();
//                        allselecctedvid.setText(batchArrayList.get(i).getTitle() +","+batchArrayList.get(i).getTitle());
//                    }
                }else {
                    videoPackages.setPackage_select("Not Checked");
                }

            }
        });


//        myViewHolder.add_class.setText(""+batchArrayList.get(i).getClass_id());
//        myViewHolder.mainname.setText(""+batchArrayList.get(i).getMainname());
//        myViewHolder.mainsubname.setText(""+batchArrayList.get(i).getSubmainname());

    }

    @Override
    public int getItemCount() {

        return batchArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView videotits,batchvid;
        CheckBox checvidpackag;
        Button editvidepckas,edit_btn_save,selvids;
        LinearLayout hide_li;
        EditText newtitle , new_description, new_create_date,new_expire_date,new_amount,new_discount_amount,new_video_time;
        ImageView add_student;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videotits = (TextView) itemView.findViewById(R.id.videotits);
            batchvid = (TextView) itemView.findViewById(R.id.batchvid);
            checvidpackag = itemView.findViewById(R.id.checvidpackag);
            //For Videopackage upload
//            newtitle = itemView.findViewById(R.id.new_title);
//            editvidepckas = itemView.findViewById(R.id.editvidepckas);
//            new_description = itemView.findViewById(R.id.new_description);
//            new_create_date = itemView.findViewById(R.id.new_create_date);
//            new_expire_date = itemView.findViewById(R.id.new_expire_date);
//            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
//            new_amount = itemView.findViewById(R.id.new_amount);
//            new_discount_amount = itemView.findViewById(R.id.newnydiscount_amount);
//            new_video_time = itemView.findViewById(R.id.newnynewvideo_time);
//            hide_li = itemView.findViewById(R.id.hide_li);
//            selvids = itemView.findViewById(R.id.selvids);


//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(activity, StudentActivity.class);
//                    activity.startActivity(intent1);
//                }
//            });



        }
    }
}
