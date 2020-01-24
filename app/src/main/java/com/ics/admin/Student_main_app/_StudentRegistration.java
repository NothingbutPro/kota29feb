package com.ics.admin.Student_main_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app._StudentModels._StudentRegistrationModel;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _StudentRegistration extends AppCompatActivity {
    TextView _st_regist;
    EditText _st_nameed ,_st_emailed,_st_passworded,_st_mobileed,_st_added,_st_father_name,_st_mothers_name,_st_parents_mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._student_registrationactivity);
        _st_nameed =findViewById(R.id._st_nameed);
        _st_emailed =findViewById(R.id._st_emailed);
        _st_passworded =findViewById(R.id._st_passworded);
        _st_mobileed =findViewById(R.id.st_mobileed);
        _st_added =findViewById(R.id._st_added);
        _st_regist =findViewById(R.id._st_regist);
        _st_father_name =findViewById(R.id._st_father_name);
        _st_mothers_name =findViewById(R.id._st_mothers_name);
        _st_parents_mobile =findViewById(R.id._st_parents_mobile);
        _st_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    RegisterUSerNow();
                    Toast.makeText(_StudentRegistration.this, "Successful validate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RegisterUSerNow() {
        LovelyProgressDialog lovelyProgressDialog =  new LovelyProgressDialog(this)
                .setIcon(R.drawable.asr_logo)
                .setTitle("Registering Please wait")
                .setTopColorRes(R.color.red);
        lovelyProgressDialog.setCancelable(false);
        lovelyProgressDialog.show();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
        Call<_StudentRegistrationModel> RegisterCall = RegApi.Register_to_Student(_st_nameed.getText().toString(),_st_emailed.getText().toString(),_st_passworded.getText().toString(),_st_mobileed.getText().toString(),_st_added.getText().toString()
        ,_st_father_name.getText().toString() , _st_father_name.getText().toString() ,_st_parents_mobile.getText().toString());
        RegisterCall.enqueue(new Callback<_StudentRegistrationModel>() {
            @Override
            public void onResponse(Call<_StudentRegistrationModel> call, Response<_StudentRegistrationModel> response) {
                Gson gson = new Gson();
                Log.d("string" , ""+gson.toJson(response.body()) );
//                            Log.d("string" , ""+response.body().getData().getEmail());
                if(response !=null)
                {
                    lovelyProgressDialog.dismiss();
                   Shared_Preference shared_preference = new Shared_Preference();
                    Toast.makeText(_StudentRegistration.this, "Registration Success ", Toast.LENGTH_SHORT).show();
                    shared_preference.setschool_id(_StudentRegistration.this , String.valueOf(response.body().getData()));
                    shared_preference.setStudent_info(_StudentRegistration.this , String.valueOf(response.body().getData()),"","","");
                    Intent intent = new Intent(_StudentRegistration.this , StudentDashboardActivity.class);
//                    new Shared_Preference().setId(_StudentRegistration.this , String.valueOf(Integer.valueOf(response.body().getData())),"Student" ,true );
                    startActivity(intent);
                    finish();
                }else{
                    lovelyProgressDialog.dismiss();
                    Toast.makeText(_StudentRegistration.this, "Either Email is duplicate or Password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_StudentRegistrationModel> call, Throwable t) {
                lovelyProgressDialog.dismiss();
                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(_StudentRegistration.this, "Data Not found", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validate() {
        if(! ( _st_emailed.getText().toString().contains("com") || _st_emailed.getText().toString().contains(".")
       || _st_emailed.getText().toString().contains("@") ) )
        {
           return false;
        }else {
            return  true;
        }
    }
}
