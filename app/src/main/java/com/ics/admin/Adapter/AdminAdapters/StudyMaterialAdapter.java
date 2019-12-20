package com.ics.admin.Adapter.AdminAdapters;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.EditStudyMaterial;
import com.ics.admin.BasicAdmin.Masters.StudyMaterial.StudyMaterialActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Utils.Utilities;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class StudyMaterialAdapter extends RecyclerView.Adapter<StudyMaterialAdapter.MyViewHolder>{
    ArrayList<com.ics.admin.Model.StudyMaterials> materialsArrayList;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    int Gallery_view = 2;
    public static Button select_file;
    Context _context;

    public StudyMaterialAdapter(Context context, ArrayList materialsArrayList) {
        this._context = context;
        this.materialsArrayList = materialsArrayList;

    }


    @NonNull
    @Override
    public StudyMaterialAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studymaterial, viewGroup, false);

        return new StudyMaterialAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyMaterialAdapter.MyViewHolder myViewHolder, int i) {
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
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , EditStudyMaterial.class);
                intent.putExtra("id",  materialsArrayList.get(i).getClass_id());
                intent.putExtra("class_id",  materialsArrayList.get(i).getId());
                view.getContext().startActivity(intent);
                ( (Activity) _context).finish();
//                String myedit = myViewHolder.alledit.getText().toString();
//                String url = "http://ihisaab.in/school_lms/Adminapi/update_subject";
//                new EditStuffsForFile( materialsArrayList.get(i).getClass_id(),materialsArrayList.get(i).getId(),myedit,MYDOC).execute();
            }
        });
//        myViewHolder.editstudy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext() , EditStudyMaterial.class);
//                view.getContext().startActivity(intent);
////                if(myViewHolder.hide_li.getVisibility() == View.VISIBLE)
////                {
////                    myViewHolder.hide_li.setVisibility(View.GONE);
////                }else {
////                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
////                }
//            }
//        });

        myViewHolder.delbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ihisaab.in/school_lms/Adminapi/delete_studymaterial";
                new DeleteDialog().DeleteDialog("id" ,(Activity) _context, materialsArrayList.get(i).getId(), url);
//                new CommunityFragment.DELETStuff( "id" ,(Activity) _context, materialsArrayList.get(i).getId(), url).execute();
            }
        });
//        notifyDataSetChanged();
//        notifyItemChanged(i);
//        myViewHolder.image_study.setImageResource(Images.get(i));
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
        Button delbatch,editstudy,edit_btn;

        LinearLayout hide_li;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            study_material = (TextView) itemView.findViewById(R.id.study_material);
            study_class = (TextView) itemView.findViewById(R.id.study_class);
//            editstudy = itemView.findViewById(R.id.editstudy);
            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);
            delbatch =  itemView.findViewById(R.id.delbatch);
//            alledit =  itemView.findViewById(R.id.alledit);
//            hide_li =  itemView.findViewById(R.id.hide_li);
//            select_file =  itemView.findViewById(R.id.select_file);
            edit_btn =  itemView.findViewById(R.id.edit_btn);
        }
    }

    private class EditStuffsForFile extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        String id,class_id;
        String Title;
        String SpinnerId;
        File mydoc;
        String result = "";

        public EditStuffsForFile(String class_id, String id, String myedit, File MYDOC) {
        this.id = id;
        this.Title = myedit;
        this.class_id = class_id;
        this.mydoc = MYDOC;
        }

//        public EditStuffsForFile(String update_studymaterial, Activity context, String class_id, String id, String myedit, String url) {
//            this.id = id;
//            this.class_id = class_id;
//        }


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(_context);
            dialog.setMessage("Processing");

            dialog.setCancelable(true);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                entity.addPart("title", new StringBody("" + Title));
                entity.addPart("class_id", new StringBody("" + class_id));
                entity.addPart("id", new StringBody("" + new Shared_Preference().getId(_context)));
                entity.addPart("pdf_file", new FileBody(mydoc));

//                    entity.addPart("ret_mobile", new StringBody("" + r_mob));
//                    entity.addPart("reference_referal_code", new StringBody(""+ref_co));

                result = Utilities.postEntityAndFindJson("http://ihisaab.in/school_lms/Adminapi/update_studymaterial", entity);

                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            String result1 = result;
            if (result1 != null) {
                dialog.dismiss();
                Log.e("result1", result1);
                try {
                    JSONObject jsonObject1  = new JSONObject(result1);
                    if(jsonObject1.getBoolean("responce")){
                        Toast.makeText((Activity)_context, ""+jsonObject1.getString("data").toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent((Activity)_context , StudyMaterialActivity.class);
                        _context.startActivity(intent);
                       ((Activity) _context).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(Single_user_act_TRD.this, "Documents Uploaded Successfully ", Toast.LENGTH_LONG).show();

                //   Intent in=new Intent(MainActivity.this,NextActivity.class);
                //  in.putExtra("doc",doc);
                //     startActivity(in);

            } else {
                dialog.dismiss();
//                Toast.makeText(Single_user_act_TRD.this, "Some Problem", Toast.LENGTH_LONG).show();
            }

        }
    }
    //+++++++++++++++++++++++++++++++++++++FOR PDF+++++++++++++++++++++++++++++++++++++

}
