package com.ics.admin.Student_main_app.Student_Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.Masters.StudyMaterial.EditStudyMaterial;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.StudyMaterialActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Model.StudyMaterials;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model_Data;
import com.ics.admin.Utils.Utilities;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class _Student_Study_Material_Adapter extends RecyclerView.Adapter<_Student_Study_Material_Adapter.MyViewHolder>{
    ArrayList<_Student_PDF_Material_Model_Data> materialsArrayList;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    int Gallery_view = 2;
    public static Button select_file;
    Context _context;

    public _Student_Study_Material_Adapter(Context context, ArrayList<_Student_PDF_Material_Model_Data> materialsArrayList) {
        this._context = context;
        this.materialsArrayList = materialsArrayList;

    }


    @NonNull
    @Override
    public _Student_Study_Material_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._studymaterial_pdf, viewGroup, false);

        return new _Student_Study_Material_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull _Student_Study_Material_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.study_material.setText("" + materialsArrayList.get(i).getTitle());
        myViewHolder.study_class.setText("Class :" + materialsArrayList.get(i).getClass_());
//        select_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if( checkAndRequestPermissions())
//                {
//                    Toast.makeText(_context, "Done", Toast.LENGTH_SHORT).show();
//                    OPENPDFCHOOSER();
//                }
//            }
//        });



    }



    //*******************************************************************

    @Override
    public int getItemCount() {

        return materialsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        //        EditText alledit;
        TextView study_material,study_class;


        LinearLayout hide_li;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            study_material = (TextView) itemView.findViewById(R.id.study_material);
            study_class = (TextView) itemView.findViewById(R.id.study_class);
//            editstudy = itemView.findViewById(R.id.editstudy);
            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);

        }
    }

    //+++++++++++++++++++++++++++++++++++++FOR PDF+++++++++++++++++++++++++++++++++++++

}
