package com.ics.admin;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ics.admin.Activities_by_Parag.ui.Faculty_Dashoard;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.ShareRefrance.Shared_Preference;

public class MainActivity extends AppCompatActivity {
    Shared_Preference shared_preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared_preference=new Shared_Preference();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                try{
//                    Log.d("login is",""+shared_preference.isLoggedIn());
                    Log.d("login is",""+shared_preference.getNamee(MainActivity.this));
                    if( shared_preference.getNamee(MainActivity.this).equals("Admin")){
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if (shared_preference.getNamee(MainActivity.this).equals("Faculty")){
                        Intent intent = new Intent(MainActivity.this, Faculty_Dashoard.class);
                        startActivity(intent);
                        finish();

                    }
                    else {

                        Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 5000);
    }
}
