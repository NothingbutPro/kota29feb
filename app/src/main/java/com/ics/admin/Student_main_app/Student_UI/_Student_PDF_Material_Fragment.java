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
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model_Data;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _Student_PDF_Material_Fragment extends Fragment {
    Shared_Preference shared_preference;
    View view;
    RecyclerView _st_studymaterial_rec;
    _Student_Study_Material_Adapter student_study_material_adapter;
    ArrayList<_Student_PDF_Material_Model_Data> student_pdf_material_data = new ArrayList<>();
    LovelyProgressDialogs lovelyProgressDialogs;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        super.onViewCreated(view, savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout._student_pdf_material ,container , false);
        _st_studymaterial_rec = view.findViewById(R.id._st_studymaterial_rec);
        lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());

        shared_preference = new Shared_Preference();
        try {

            student_pdf_material_data.clear();
            student_study_material_adapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            lovelyProgressDialogs.StartDialog("Getting your profile");
            GetAllStudyMAterial(shared_preference.getSchoolId(view.getContext()), shared_preference.getClassId(getActivity()));
        }
        return view;
    }
    private void GetAllStudyMAterial(String id, String class_id) {
        Toast.makeText(getActivity(), "on Create PDF", Toast.LENGTH_SHORT).show();
        Log.e("id" , "is"+id);
        Log.e("courseId" , "is"+class_id);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , class_id);
        RegisterCall.enqueue(new Callback<_Student_PDF_Material_Model>() {
            @Override
            public void onResponse(Call<_Student_PDF_Material_Model> call, Response<_Student_PDF_Material_Model> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                    student_pdf_material_data = response.body().getData();
                    student_study_material_adapter = new _Student_Study_Material_Adapter(getActivity(),student_pdf_material_data);
                    LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    _st_studymaterial_rec.setLayoutManager(linearLayoutManager);
                    _st_studymaterial_rec.setAdapter(student_study_material_adapter);
                    lovelyProgressDialogs.DismissDialog();
                }else {
                    lovelyProgressDialogs.DismissDialog();
                    Toast.makeText(getActivity(), "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_PDF_Material_Model> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(getActivity(), "No PDF Found", Toast.LENGTH_SHORT).show();
                lovelyProgressDialogs.DismissDialog();
            }
        });

    }

//    @Override
//    public void onStart() {
//        Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
//        shared_preference = new Shared_Preference();
//        try {
//
//            student_pdf_material_data.clear();
//            student_study_material_adapter.notifyDataSetChanged();
//        }catch (Exception e)
//        {
//            GetAllStudyMAterial(shared_preference.getSchoolId(view.getContext()), shared_preference.getClassId(getActivity()));
//        }
//        super.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
//        shared_preference = new Shared_Preference();
//        try {
//
//            student_pdf_material_data.clear();
//            student_study_material_adapter.notifyDataSetChanged();
//        }catch (Exception e)
//        {
//            GetAllStudyMAterial(shared_preference.getSchoolId(view.getContext()), shared_preference.getClassId(getActivity()));
//        }
//        super.onResume();
//    }
}
