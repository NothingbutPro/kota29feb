package com.ics.admin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Adapter.AdminAdapters.Public_Community_Admin_Chat_Adapter;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters.Public_Community_Chat_Adapter;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Study_Material_Adapter;
import com.ics.admin.Student_main_app.Student_UI._Student_Video_Lecture_Activity;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community;
import com.ics.admin.Student_main_app._StudentModels._Student_Chat_Public_Community_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_PDF_Material_Model_Data;
import com.ics.admin.Student_main_app._StudentModels._Student_Public_Message;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminEFragment extends Fragment {
    RecyclerView all_admin_chatfrags;
    EditText ansqueryed;
    ImageView ansthequeryimg;
    View view;
    Public_Community_Admin_Chat_Adapter public_community_admin_chat_adapter;
    ArrayList<_Student_Chat_Public_Community_Data> student_chat_public_community_data_list = new ArrayList<_Student_Chat_Public_Community_Data>();
    _Student_Video_Lecture_Activity student_chat_public_community_data ;
    LovelyProgressDialogs lovelyProgressDialogs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_admin_e , container ,false);
        all_admin_chatfrags = view.findViewById(R.id.all_admin_chatfrags);
        ansqueryed = view.findViewById(R.id.ansqueryed);
        ansthequeryimg = view.findViewById(R.id.ansthequeryimg);
        lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());
        getallCommunityMesages(new Shared_Preference().getId(getActivity()));
        ansthequeryimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ansqueryed.getText().toString().length() !=0) {
                    lovelyProgressDialogs.StartDialog("Sending Your Query Please wait");
                    SendMyQuery(ansqueryed.getText().toString() , new Shared_Preference().getId(getActivity()),new Shared_Preference().getStudent_id(getActivity()));
                }else {
                    Toast.makeText(getActivity(), "You Must specify your query", Toast.LENGTH_SHORT).show();
                }
            }

            private void SendMyQuery(String Message , String school_id , String user_id) {
                Log.e("messages" , ""+Message);
                Log.e("school_id" , ""+school_id);
                Log.e("user_id" , ""+user_id);

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
                Retrofit RetroLogin = new Retrofit.Builder().client(client)
                        .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                        .build();
                StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
                Call<_Student_Public_Message> RegisterCall = RegApi.STUDENT_CHAT_PUBLIC_COMMUNITY_CALL(Message,school_id ,"2",school_id);
                RegisterCall.enqueue(new Callback<_Student_Public_Message>() {
                    @Override
                    public void onResponse(Call<_Student_Public_Message> call, Response<_Student_Public_Message> response) {
                        Gson gson = new Gson();
                        Log.d("attendence string" , ""+gson.toJson(response.body()) );
                        if(response.body().getResponce()) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            try {
                                student_chat_public_community_data_list.clear();
                                public_community_admin_chat_adapter.notifyDataSetChanged();
                                getallCommunityMesages(school_id);
                            }catch (Exception e)
                            {
                                getallCommunityMesages(school_id);
                                e.printStackTrace();
                            }
                            lovelyProgressDialogs.DismissDialog();

                        }else {
                            Toast.makeText(getActivity(), "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<_Student_Public_Message> call, Throwable t) {

                        Log.d("local cause" , ""+t.getLocalizedMessage());
                        Log.d("local cause" , ""+t.getMessage());
                        Toast.makeText(getActivity(), "Failed to publish the Query", Toast.LENGTH_SHORT).show();
                        lovelyProgressDialogs.DismissDialog();
                    }
                });



            }
        });
        return  view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getallCommunityMesages(String id) {
        Log.e("id" , "is"+id);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
//        Call<_Student_PDF_Material_Model> RegisterCall = RegApi.STUDENT_PDF_MATERIAL_MODEL_CALL(id , courseId);
        Call<_Student_Chat_Public_Community> RegisterCall = RegApi.STUDENT_CHAT_PUBLIC_COMMUNITY_CALL(id ,"2");
        RegisterCall.enqueue(new Callback<_Student_Chat_Public_Community>() {
            @Override
            public void onResponse(Call<_Student_Chat_Public_Community> call, Response<_Student_Chat_Public_Community> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                    student_chat_public_community_data_list = response.body().getMassage();

                    public_community_admin_chat_adapter = new Public_Community_Admin_Chat_Adapter (getActivity(),student_chat_public_community_data_list);
                    LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    all_admin_chatfrags.setLayoutManager(linearLayoutManager);
                    all_admin_chatfrags.setAdapter(public_community_admin_chat_adapter);
                }else {
                    Toast.makeText(getActivity(), "No StudyMaterial found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_Student_Chat_Public_Community> call, Throwable t) {

                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(getActivity(), "Network problem", Toast.LENGTH_SHORT).show();

            }
        });


    }
}