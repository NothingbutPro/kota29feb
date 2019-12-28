package com.ics.admin.Student_main_app.Student_UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters._MY_Attendence_Adapter;
import com.ics.admin.Student_main_app.Student_Adapters._Student_HomeWork_Adapter;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Homeworks_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Homeworks_Model_Data;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_HomeWork_Fragment extends Fragment {
    RecyclerView homeworkrec;
    Shared_Preference shared_preference;
    ArrayList<_Student_Homeworks_Model_Data> student_homeworks_modelsList = new ArrayList<>();
    _Student_HomeWork_Adapter  student_homeWork_adapter;
    private LovelyProgressDialogs lovelyProgressDialogs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_d, container, false);
        homeworkrec = view.findViewById(R.id.homeworkrec);
        lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());
        shared_preference = new Shared_Preference();
        GetAllMyHomeWorks(shared_preference.getClassId(getActivity() ) , shared_preference.getBatchId(getActivity()) );
        return view;

    }

    private void GetAllMyHomeWorks(String classId, String batchId) {
        Log.e("Student_id" , "is"+classId);
        Log.e("attdate" , "is"+batchId);
        lovelyProgressDialogs.StartDialog("Getting Your Homework");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
        Call<_Student_Homeworks_Model> RegisterCall = RegApi.STUDENT_HOMEWORKS_MODEL_CALL(classId , batchId);
        RegisterCall.enqueue(new Callback<_Student_Homeworks_Model>() {
            @Override
            public void onResponse(Call<_Student_Homeworks_Model> call, Response<_Student_Homeworks_Model> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                    student_homeworks_modelsList = response.body().getData();
                    student_homeWork_adapter = new _Student_HomeWork_Adapter(getActivity(), student_homeworks_modelsList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    homeworkrec.setLayoutManager(linearLayoutManager);
                    homeworkrec.setAdapter(student_homeWork_adapter);

//                            Log.d("string" , ""+response.body().getData().getEmail());
                    if (response != null) {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(getActivity(), "Either Email is duplicate or Password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    lovelyProgressDialogs.DismissDialog();
                    Toast.makeText(getActivity(), "No attendance found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_Homeworks_Model> call, Throwable t) {
                lovelyProgressDialogs.DismissDialog();
                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(getActivity(), "No Attendance Found", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
