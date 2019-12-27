package com.ics.admin.Student_main_app.Student_UI;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Video_Material_Adapter;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages;
import com.ics.admin.Student_main_app._StudentModels._Student_All_Packages_data;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model_Data;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_Video_Material_Fragment extends Fragment {
    RecyclerView _st_video_rec;
    SearchView searchloc;
    ArrayList<_Student_Video_Materials_Model_Data> student_video_material_adapters_list = new ArrayList<>();
    _Student_Video_Material_Adapter student_video_material_adapters;
    LovelyProgressDialogs lovelyProgressDialogs;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._student_study_video ,container,false);
        _st_video_rec = view.findViewById(R.id._st_video_rec);
        searchloc = view.findViewById(R.id.searchloc);
        Toast.makeText(getActivity(), "_Student_Video_Material_Fragment created", Toast.LENGTH_SHORT).show();
        lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());
        lovelyProgressDialogs.StartDialog("Getting Your Packages");
        GETALLVIDEOS("py", new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
        searchloc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() ==0) {
                    GETALLVIDEOS(newText, new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
                }else {
                    GETALLVIDEOS("py", new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
                }
                return false;
            }
        });
        return  view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void GETALLVIDEOS(String newText, String schoolId, String classId, String courseId) {
        Log.e("schoolId" , "is"+schoolId);
        Log.e("classId" , "is"+classId);
        Log.e("courseId" , "is"+courseId);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_Video_Materials_Model> RegisterCall = RegApi.STUDENT_VIDEO_MATERIALS_MODEL_CALL(schoolId);
        RegisterCall.enqueue(new Callback<_Student_Video_Materials_Model>() {
            @Override
            public void onResponse(Call<_Student_Video_Materials_Model> call, Response<_Student_Video_Materials_Model> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {


                    student_video_material_adapters_list = response.body().getData();
                    student_video_material_adapters = new _Student_Video_Material_Adapter(getActivity(),getActivity(),student_video_material_adapters_list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    _st_video_rec.setLayoutManager(linearLayoutManager);
                    _st_video_rec.setAdapter(student_video_material_adapters);
                    lovelyProgressDialogs.DismissDialog();
                    if(!newText.equals("py")) {

                        for (int i = 0; i < student_video_material_adapters_list.size(); i++) {
                            if ( !student_video_material_adapters_list.get(i).getLocation().contains(newText) ) {

                                student_video_material_adapters_list.remove(i);
                                student_video_material_adapters.notifyItemRemoved(i);
                            } else {
                                Log.e("match", "failed");
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                    lovelyProgressDialogs.DismissDialog();
                }

            }

            @Override
            public void onFailure(Call<_Student_Video_Materials_Model> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                lovelyProgressDialogs.DismissDialog();
            }
        });

    }

//    @Override
//    public void onStart() {
//        GETALLVIDEOS("py"  , new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()) ,new Shared_Preference().getCourseId(getActivity()));
//        searchloc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText.length() ==0) {
//                    GETALLVIDEOS(newText, new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
//                }else {
//                    GETALLVIDEOS("py", new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
//                }
//                return false;
//            }
//        });
//        super.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        GETALLVIDEOS("py"  , new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()) ,new Shared_Preference().getCourseId(getActivity()));
//        searchloc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText.length() ==0) {
//                    GETALLVIDEOS(newText, new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
//                }else {
//                    GETALLVIDEOS("py", new Shared_Preference().getSchoolId(getActivity()), new Shared_Preference().getClassId(getActivity()), new Shared_Preference().getCourseId(getActivity()));
//                }
//                return false;
//            }
//        });
//        super.onResume();
//    }
}
