package com.ics.admin.Student_main_app.Student_UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.StudentDashboardActivity;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Model;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_Profile_Activity extends AppCompatActivity {
    EditText profile_mob,pro_email;
    TextView student_name,class_txt,batch_txt;
    LovelyProgressDialogs lovelyProgressDialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___student__profile_);

        student_name  = findViewById(R.id.student_name);
        class_txt  = findViewById(R.id.class_txt);
        batch_txt  = findViewById(R.id.batch_txt);
        pro_email  = findViewById(R.id.pro_email);
        profile_mob  = findViewById(R.id.profile_mob);
        lovelyProgressDialogs = new LovelyProgressDialogs(this);
        lovelyProgressDialogs.StartDialog("Gettting Your Profile Data");
        GETMYSTUDENTPROFILE(new Shared_Preference().getStudent_id(this));
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
                        class_txt.setText(response.body().getData().getClass_());
                        batch_txt.setText(response.body().getData().getBatch());
                        profile_mob.setText(response.body().getData().getMobile());
                        pro_email.setText(response.body().getData().getEmail());
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
