package com.ics.admin.Student_main_app.Student_Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.R;
import com.ics.admin.Student_main_app.Student_UI._Student_All_Videos_By_Packages;
import com.ics.admin.Student_main_app.Student_UI._Student_Video_Lecture_Activity;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages_data;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model_Data;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _Student_All_Videos_Package_Adapter extends RecyclerView.Adapter<_Student_All_Videos_Package_Adapter.MyViewHolder>{
    ArrayList<_Student_All_Packages_data> materialsArrayList;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    int Gallery_view = 2;

    Context _context;
    Activity activity;

    public _Student_All_Videos_Package_Adapter(Context context, ArrayList<_Student_All_Packages_data> materialsArrayList) {
        this._context = context;

        this.materialsArrayList = materialsArrayList;

    }


    @NonNull
    @Override
    public _Student_All_Videos_Package_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._student_video_material, viewGroup, false);

        return new _Student_All_Videos_Package_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull _Student_All_Videos_Package_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.vid_name.setText("" + materialsArrayList.get(i).getTitle());
        myViewHolder.vid_class.setText("" + materialsArrayList.get(i).getDescription());
        myViewHolder.vid_batch.setText("" + materialsArrayList.get(i).getCourse());
//        myViewHolder.vid_insti.setText("" + materialsArrayList.get(i).getDiscountAmount());
        Log.e("getVideoId are" , ""+materialsArrayList.get(i).getTitle());
        Log.e("getDemoVideoId are" , ""+materialsArrayList.get(i).getDescription());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , _Student_Video_Lecture_Activity.class);
                Log.e("video title" , ""+materialsArrayList.get(i).getVideo());
                Log.e("video title" , ""+materialsArrayList.get(i).getDescription());
                intent.putExtra("vid_name" ,materialsArrayList.get(i).getVideo());
                v.getContext().startActivity(intent);
                ( (Activity)_context ).finish();

            }
        });
    }



    //*******************************************************************

    @Override
    public int getItemCount() {

        return materialsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        //        EditText alledit;
        TextView vid_name,vid_class,vid_batch,vid_insti;


        LinearLayout hide_li;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vid_name = (TextView) itemView.findViewById(R.id.vid_name);
            vid_class = (TextView) itemView.findViewById(R.id.vid_class);
            vid_batch = (TextView) itemView.findViewById(R.id.vid_batch);
            vid_insti = (TextView) itemView.findViewById(R.id.vid_insti);
//            editstudy = itemView.findViewById(R.id.editstudy);
            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);

        }
    }

    //+++++++++++++++++++++++++++++++++++++FOR PDF+++++++++++++++++++++++++++++++++++++

}
