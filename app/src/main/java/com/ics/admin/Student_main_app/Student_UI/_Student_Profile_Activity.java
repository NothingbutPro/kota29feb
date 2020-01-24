package com.ics.admin.Student_main_app.Student_UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.StudentDashboardActivity;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Model;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_Profile_Activity extends AppCompatActivity {
    EditText profile_mob,pro_email,pro_address;
    TextView class_txt,batch_txt;
    EditText student_name,pro_father_name,pro_mother_name,pro_parent_number;
    ImageView stu_mob_edit_img,stu_email_edit_img,stu_address_edt_img,stu_parent_edt_img,stu_parent_number_img;
    ImageView std_name_edt_img;
    LovelyProgressDialogs lovelyProgressDialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___student__profile_);
        student_name  = findViewById(R.id.student_name);
        pro_address  = findViewById(R.id.pro_address);
        pro_father_name  = findViewById(R.id.pro_father_name);
        pro_mother_name  = findViewById(R.id.pro_mother_name);
        pro_parent_number  = findViewById(R.id.pro_parent_number);
        class_txt  = findViewById(R.id.class_txt);
        batch_txt  = findViewById(R.id.batch_txt);
        pro_email  = findViewById(R.id.pro_email);
        std_name_edt_img  = findViewById(R.id.std_name_edt_img);
        profile_mob  = findViewById(R.id.profile_mob);
        //+++++++++++++++++++++++++++For Image view+++++++++++++++++++
        stu_mob_edit_img  = findViewById(R.id.stu_mob_edit_img);

        stu_mob_edit_img.setVisibility(View.GONE);
        std_name_edt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!student_name.isEnabled()) {
                    student_name.setEnabled(true);
                    student_name.requestFocus();
//                pro_email.setBackgroundColor(Color.BLACK);
//                    pro_email.setBackgroundColor(R.drawable.backgraund);
                    student_name.setFocusable(true);
                    std_name_edt_img.setBackground(null);
                    std_name_edt_img.setImageResource(R.drawable.ic_save_black_24dp);

                }else {
                    std_name_edt_img.setBackground(null);
                    std_name_edt_img.setImageResource(R.drawable.ic_edit_black_24dp);
                    student_name.setEnabled(false);
                    student_name.requestFocus();
                    student_name.setFocusable(false);
                    ProgressDialog  progressDialogs = new ProgressDialog(v.getContext());
                    progressDialogs.show();
                    progressDialogs.setCancelable(false);
                    UpdateMyProfile(progressDialogs,student_name.getText().toString() , pro_email.getText().toString() ,pro_address.getText().toString());
                }
            }
        });
//        stu_mob_edit_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                profile_mob.setEnabled(true);
//                profile_mob.setFocusable(true);
//            }
//        });
        stu_email_edit_img  = findViewById(R.id.stu_email_edit_img);
        stu_email_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pro_email.isEnabled()) {
                    pro_email.setEnabled(true);
                    pro_email.requestFocus();
//                pro_email.setBackgroundColor(Color.BLACK);
//                    pro_email.setBackgroundColor(R.drawable.backgraund);
                    pro_email.setFocusable(true);
                    stu_email_edit_img.setBackground(null);
                    stu_email_edit_img.setImageResource(R.drawable.ic_save_black_24dp);
//                    UpdateMyProfile(student_name.getText().toString() , pro_email.getText().toString() ,pro_address.getText().toString());
                }else {
                    stu_email_edit_img.setBackground(null);
                    stu_email_edit_img.setImageResource(R.drawable.ic_edit_black_24dp);
                    pro_email.setEnabled(false);
                    pro_email.requestFocus();
                    pro_email.setFocusable(false);
                    ProgressDialog progressDialogs  = new ProgressDialog(v.getContext());
                    progressDialogs.setCancelable(false);
                    progressDialogs.show();
                    UpdateMyProfile(progressDialogs, student_name.getText().toString() , pro_email.getText().toString() ,pro_address.getText().toString());
                }
            }
        });
        stu_address_edt_img  = findViewById(R.id.stu_address_edt_img);
        stu_address_edt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pro_address.isEnabled()) {
                    pro_address.setEnabled(true);
                    pro_address.requestFocus();
//                pro_address.setBackgroundColor(Color.BLACK);
                    pro_address.setFocusable(true);
//                    stu_address_edt_img.setBackground(null);
//                    UpdateMyProfile(student_name.getText().toString() , pro_email.getText().toString() ,pro_address.getText().toString());
                    stu_address_edt_img.setImageResource(R.drawable.ic_save_black_24dp);
                }else {
                    pro_address.setEnabled(true);
                    pro_address.requestFocus();
//                pro_address.setBackgroundColor(Color.BLACK);
                    pro_address.setFocusable(true);
                    stu_address_edt_img.setBackground(null);
                    stu_address_edt_img.setImageResource(R.drawable.ic_edit_black_24dp);
                    ProgressDialog progressDialogs  =new ProgressDialog(v.getContext());
                    progressDialogs.show();
                    progressDialogs.setCancelable(false);
                    UpdateMyProfile(progressDialogs, student_name.getText().toString() , pro_email.getText().toString() ,pro_address.getText().toString());
                }
            }
        });
        //++++++++++++++++++++++++++++++
        lovelyProgressDialogs = new LovelyProgressDialogs(this);
        lovelyProgressDialogs.StartDialog("Gettting Your Profile Data");
        GETMYSTUDENTPROFILE(new Shared_Preference().getStudent_id(this));
    }

    private void UpdateMyProfile(ProgressDialog progressDialogs, String student_names, String pro_emails, String pro_addresss) {

        Log.e("student_id" , "is"+new Shared_Preference().getStudent_id(_Student_Profile_Activity.this ));
            Log.e("student_names" , "is"+student_names);
            Log.e("pro_emails" , "is"+pro_emails);
            Log.e("pro_addresss" , "is"+pro_addresss);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_Profile_Data> RegisterCall = RegApi.STUDENT_PROFILE_DATA_CALL(new Shared_Preference().getStudent_id(_Student_Profile_Activity.this ) ,student_names,pro_emails , pro_addresss,pro_father_name.getText().toString() ,pro_mother_name.getText().toString() , pro_parent_number.getText().toString());
        RegisterCall.enqueue(new Callback<_Student_Profile_Data>() {
            @Override
            public void onResponse(Call<_Student_Profile_Data> call, Response<_Student_Profile_Data> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                    student_name.setText(response.body().getName());
//                    class_txt.setText(response.body().getClass_());
//                    batch_txt.setText(response.body().getData().getBatch());
//                    profile_mob.setText(response.body().get());
                    pro_email.setText(response.body().getEmail());
                    pro_address.setText(response.body().getAddress());


                    progressDialogs.dismiss();
//                    lovelyProgressDialogs.DismissDialog();
                }else {
//                    lovelyProgressDialogs.DismissDialog();
                    progressDialogs.dismiss();
                    Toast.makeText(_Student_Profile_Activity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_Profile_Data> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(_Student_Profile_Activity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                lovelyProgressDialogs.DismissDialog();
                progressDialogs.dismiss();
            }
        });

    }

    private void GETMYSTUDENTPROFILE(String student_id) {
        {

            Log.e("student_id" , "is"+student_id);
//            Log.e("courseId" , "is"+class_id);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
            Retrofit RetroLogin = new Retrofit.Builder().client(client)
                    .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                    .build();
            StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
            Call<_Student_Profile_Model> RegisterCall = RegApi.STUDENT_PROFILE_MODEL_CALL(student_id);
            RegisterCall.enqueue(new Callback<_Student_Profile_Model>() {
                @Override
                public void onResponse(Call<_Student_Profile_Model> call, Response<_Student_Profile_Model> response) {
                    Gson gson = new Gson();
                    Log.d("attendence string" , ""+gson.toJson(response.body()) );
                    if(response.body().getResponce()) {
                        student_name.setText(response.body().getData().getName());
//                        class_txt.setText(response.body().getData().getClass_());
                        pro_address.setText(response.body().getData().getAddress());
                        batch_txt.setText(response.body().getData().getBatchId());
                        profile_mob.setText(response.body().getData().getMobile());
                        pro_email.setText(response.body().getData().getEmail());
                        pro_father_name.setText(response.body().getData().getFatherName());
                        pro_mother_name.setText(response.body().getData().getMotherName());
                        pro_parent_number.setText(response.body().getData().getParentMobile());
                        lovelyProgressDialogs.DismissDialog();
                    }else {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(_Student_Profile_Activity.this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<_Student_Profile_Model> call, Throwable t) {

                    Log.d("local cause" , ""+t.getLocalizedMessage());
                    Log.d("local cause" , ""+t.getMessage());
                    Toast.makeText(_Student_Profile_Activity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                    lovelyProgressDialogs.DismissDialog();
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , StudentDashboardActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
