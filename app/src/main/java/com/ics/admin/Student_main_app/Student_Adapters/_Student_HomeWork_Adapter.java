package com.ics.admin.Student_main_app.Student_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.R;
import com.ics.admin.Student_main_app._StudentModels._Student_Homeworks_Model_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model_Data;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _Student_HomeWork_Adapter extends RecyclerView.Adapter<_Student_HomeWork_Adapter.MyViewHolder>{
    ArrayList<_Student_Homeworks_Model_Data> materialsArrayList;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    int Gallery_view = 2;
    public static Button select_file;
    Context _context;

    public _Student_HomeWork_Adapter(Context context, ArrayList<_Student_Homeworks_Model_Data> materialsArrayList) {
        this._context = context;
        this.materialsArrayList = materialsArrayList;

    }


    @NonNull
    @Override
    public _Student_HomeWork_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._homework_student_view, viewGroup, false);

        return new _Student_HomeWork_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull _Student_HomeWork_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.study_material.setText( materialsArrayList.get(i).getHomework());
        myViewHolder.study_class.setText("In"+materialsArrayList.get(i).getDaysforwok());
        myViewHolder.home_cre_date.setText("Given on :"+materialsArrayList.get(i).getWorkDate());

    }



    //*******************************************************************

    @Override
    public int getItemCount() {

        return materialsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        //        EditText alledit;
        TextView study_material,study_class,home_cre_date;


        LinearLayout hide_li;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            study_material = (TextView) itemView.findViewById(R.id.study_material);
            study_class = (TextView) itemView.findViewById(R.id.study_class);
            home_cre_date = (TextView) itemView.findViewById(R.id.home_cre_date);
//            editstudy = itemView.findViewById(R.id.editstudy);
            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);

        }
    }

    //+++++++++++++++++++++++++++++++++++++FOR PDF+++++++++++++++++++++++++++++++++++++

}
