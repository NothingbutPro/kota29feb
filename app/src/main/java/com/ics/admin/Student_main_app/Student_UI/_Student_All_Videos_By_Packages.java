package com.ics.admin.Student_main_app.Student_UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters._Student_All_Videos_Package_Adapter;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Video_Material_Adapter;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages_data;
import com.ics.admin.Student_main_app._StudentModels._Student_Demo_Videos;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model_Data;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ics.admin.Student_main_app.Student_Adapters._Student_Video_Material_Adapter.dem_vid_ids;
import static com.ics.admin.Student_main_app.Student_Adapters._Student_Video_Material_Adapter.vid_ids;

public class _Student_All_Videos_By_Packages  extends AppCompatActivity {
    RecyclerView all_vid_packeges_rec;
    LinearLayout demo_li;
    TextView dem_title,dem_des;
    String Actual_Title;
    ArrayList<_Student_All_Packages_data> studentAllPackagesDatalist = new ArrayList<>();
    LovelyProgressDialogs lovelyProgressDialogs;
    _Student_All_Videos_Package_Adapter student_video_material_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout._student_all_video_packgaes);
//        View view = inflater.inflate(R.layout._student_all_video_packgaes ,container ,false);
        all_vid_packeges_rec = findViewById(R.id.all_vid_packeges_rec);
        lovelyProgressDialogs = new LovelyProgressDialogs(this);
        demo_li = findViewById(R.id.demo_li);

        dem_title = findViewById(R.id.dem_title);
        dem_des = findViewById(R.id.dem_des);
//        Toast.makeText(, "CREATED sTUDENT STYUDY ", Toast.LENGTH_SHORT).show();
//        dem_vid_ids
        getAllVideosFromPAckages(vid_ids);
        getAllDemoFromPAckages(dem_vid_ids);
        demo_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , _Student_Video_Lecture_Activity.class);
                intent.putExtra("vid_name" ,Actual_Title);
                v.getContext().startActivity(intent);
                finish();
            }
        });


        super.onCreate(savedInstanceState);
    }

    private void getAllDemoFromPAckages(String dem_vid_ids) {
        Log.e("dem_vid_ids" , "is"+ dem_vid_ids);
//        Log.e("classId" , "is"+classId);
//        Log.e("courseId" , "is"+courseId);

        lovelyProgressDialogs.StartDialog("Getting Video Packages");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_Demo_Videos> RegisterCall = RegApi.STUDENT_ALL_DEMO_PACKAGES_DATA_CALL(dem_vid_ids);
        RegisterCall.enqueue(new Callback<_Student_Demo_Videos>() {
            @Override
            public void onResponse(Call<_Student_Demo_Videos> call, Response<_Student_Demo_Videos> response) {
                Gson gson = new Gson();
                Log.d("_Student_Demo_Videos st" , ""+response);
                if(response !=null) {
                    _Student_Demo_Videos student_demo_videos = response.body();
//                    Log.e("student_demo_videos" , ""+student_demo_videos.getData().get(0).getDescription());

                    dem_title.setText(student_demo_videos.getData().get(0).getDescription());
                    dem_des.setText(student_demo_videos.getData().get(0).getCourse());
                    Actual_Title = student_demo_videos.getData().get(0).getTitle();
                    lovelyProgressDialogs.DismissDialog();
                }
                else {
                    lovelyProgressDialogs.DismissDialog();
                    Toast.makeText(_Student_All_Videos_By_Packages.this, "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_Demo_Videos> call, Throwable t) {
                lovelyProgressDialogs.DismissDialog();
                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(_Student_All_Videos_By_Packages.this, "No Packages Found", Toast.LENGTH_SHORT).show();

            }
        });

    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout._student_all_video_packgaes ,container ,false);
//        all_vid_packeges_rec = view.findViewById(R.id.all_vid_packeges_rec);
//
////        dem_vid_ids
//        getAllVideosFromPAckages(vid_ids );
//        return  view;
//
////        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    private void getAllVideosFromPAckages( String vid_ids) {
        Log.e("schoolId" , "is"+ vid_ids);
//        Log.e("classId" , "is"+classId);
//        Log.e("courseId" , "is"+courseId);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_All_Packages> RegisterCall = RegApi.STUDENT_ALL_PACKAGES_DATA_CALL(vid_ids);
        RegisterCall.enqueue(new Callback<_Student_All_Packages>() {
            @Override
            public void onResponse(Call<_Student_All_Packages> call, Response<_Student_All_Packages> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                    studentAllPackagesDatalist = response.body().getData();
                    student_video_material_adapter = new _Student_All_Videos_Package_Adapter(_Student_All_Videos_By_Packages.this,studentAllPackagesDatalist);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_Student_All_Videos_By_Packages.this);
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    all_vid_packeges_rec.setLayoutManager(linearLayoutManager);
                    all_vid_packeges_rec.setAdapter(student_video_material_adapter);
                }
                else {
                    Toast.makeText(_Student_All_Videos_By_Packages.this, "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_All_Packages> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(_Student_All_Videos_By_Packages.this, "Network problem", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
