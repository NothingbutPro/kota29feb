package com.ics.admin.Student_main_app.Student_UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Announcement_Adapter;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_Announcement_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Announcements_Model_Datas;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_Announcement_Fragment  extends Fragment {
    RecyclerView _st_annoucens_rec;
    _Student_Announcement_Adapter student_announcement_adapter;
    LovelyProgressDialogs lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());
    ArrayList<_Student_Announcements_Model_Datas> my_student_attendence_dataList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._student_announcement , container ,false);
        _st_annoucens_rec = view.findViewById(R.id._st_annoucens_rec );
        Toast.makeText(getActivity(), "_Student_Announcement_Fragment created", Toast.LENGTH_SHORT).show();
        GETALLANNOUNCEMENTS(new Shared_Preference().getClassId(getActivity()) ,new Shared_Preference().getBatchId(getActivity()));
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void GETALLANNOUNCEMENTS(String classId, String batchId) {
        {
        lovelyProgressDialogs.StartDialog("Getting Your Announcements");
            Log.e("classId" , "is"+classId);
            Log.e("batchId" , "is"+batchId);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
            Retrofit RetroLogin = new Retrofit.Builder().client(client)
                    .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                    .build();
            StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
            Call<_Student_Announcement_Model> RegisterCall = RegApi.STUDENT_ANNOUNCEMENTS_MODEL(classId , batchId);
            RegisterCall.enqueue(new Callback<_Student_Announcement_Model>() {
                @Override
                public void onResponse(Call<_Student_Announcement_Model> call, Response<_Student_Announcement_Model> response) {
                    Gson gson = new Gson();
                    Log.d("attendence string" , ""+gson.toJson(response.body()) );
                    if(response.body().getResponce()) {
                        my_student_attendence_dataList = response.body().getData();
                        student_announcement_adapter = new _Student_Announcement_Adapter(getActivity(),my_student_attendence_dataList);
                        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        _st_annoucens_rec.setLayoutManager(linearLayoutManager);
                        _st_annoucens_rec.setAdapter(student_announcement_adapter);
                    }else {
                        Toast.makeText(getActivity(), "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<_Student_Announcement_Model> call, Throwable t) {

                    Log.d("local cause" , ""+t.getLocalizedMessage());
                    Log.d("local cause" , ""+t.getMessage());
                    Toast.makeText(getActivity(), "Network problem", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
