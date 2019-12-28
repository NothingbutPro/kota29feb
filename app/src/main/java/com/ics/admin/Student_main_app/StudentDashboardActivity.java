package com.ics.admin.Student_main_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.Student_Adapters._Student_Video_Material_Adapter;
import com.ics.admin.Student_main_app.Student_UI._My_Student_Attendence_Activity;
import com.ics.admin.Student_main_app.Student_UI._Student_Announcement_Fragment;
import com.ics.admin.Student_main_app.Student_UI._Student_Community;
import com.ics.admin.Student_main_app.Student_UI._Student_HomeWork_Fragment;
import com.ics.admin.Student_main_app.Student_UI._Student_Pay_Fee;
import com.ics.admin.Student_main_app.Student_UI._Student_Profile_Activity;
import com.ics.admin.Student_main_app.Student_UI._Student_Study_material;
import com.ics.admin.Student_main_app._StudentModels._Student_Profile_Model;
import com.ics.admin.Student_main_app._StudentModels._Student_Video_Materials_Model;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentDashboardActivity extends AppCompatActivity {
    MorphBottomNavigationView morphBottomNavigationView;
    private LovelyProgressDialogs lovelyProgressDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        morphBottomNavigationView = findViewById(R.id.student_bottomnavview);
        NavigationView navigationView = findViewById(R.id.student_nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.studenttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SR");
        lovelyProgressDialogs = new LovelyProgressDialogs(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        getmyprofileforreal(new Shared_Preference().getStudent_id(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getTitle().equals("My Attendance"))
                {
                    Intent intent  =new Intent(StudentDashboardActivity.this , _My_Student_Attendence_Activity.class);
                    startActivity(intent);
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.student_frame_container ,new _My_Student_Attendence_Activity()).commit();
//                    fragmentTransaction.addToBackStack(_My_Student_Attendence_Activity.class.getName());
//                    drawer.closeDrawer(GravityCompat.START);
                }else if(menuItem.getTitle().equals("Logout"))
                {
                    Shared_Preference shared_preference = new Shared_Preference();
                    shared_preference.setStudent_info(StudentDashboardActivity.this, "", ""," ","");
                    Intent intent = new Intent(StudentDashboardActivity.this , OTPActivity.class);
                    startActivity(intent);
                    finish();
                }else if(menuItem.getTitle().equals("Profile"))
                {
                   Intent intent = new Intent(StudentDashboardActivity.this , _Student_Profile_Activity.class);
                   startActivity(intent);
//                   finish();
                }
                return false;
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Study_material()).commit();
        fragmentTransaction.addToBackStack(_Student_Study_material.class.getName());
//        fragmentTransaction.add
        morphBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(StudentDashboardActivity.this, "You have selecetasdf "+menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if(menuItem.getTitle().equals("Study Material"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Study_material()).commit();
                    fragmentTransaction.addToBackStack(_Student_Study_material.class.getName());
//                    fragmentTransaction.addToBackStack(null);
                }else if((menuItem.getTitle().toString().contains("Fee") )) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Pay_Fee()).commit();
//                    fragmentTransaction.addToBackStack(_Student_Pay_Fee.class.getName());
//                    fragmentTransaction.addToBackStack(null);

                    Toast.makeText(StudentDashboardActivity.this, "wait working", Toast.LENGTH_SHORT).show();
                }else if(menuItem.getTitle().equals("Community"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Community()).commit();
//                    fragmentTransaction.addToBackStack(_Student_Community.class.getName());
//                    fragmentTransaction.addToBackStack(null);
                    drawer.closeDrawer(GravityCompat.START);
                }else if(menuItem.getTitle().equals("Homework"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_HomeWork_Fragment()).commit();
                    fragmentTransaction.addToBackStack(_My_Student_Attendence_Activity.class.getName());
//                    fragmentTransaction.addToBackStack(null);
//                    drawer.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });

    }

    private void getmyprofileforreal(String student_id) {
        {
            lovelyProgressDialogs.StartDialog("Getting Your Profile Data");
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
                        NavigationView navigationView = (NavigationView) findViewById(R.id.student_nav_view);
                        View hView =  navigationView.getHeaderView(0);
                        TextView nav_student_name = (TextView)hView.findViewById(R.id.nav_student_name);
                        TextView nav_student_email = (TextView)hView.findViewById(R.id.nav_student_email);
                        nav_student_name.setText(response.body().getData().getName());
                        nav_student_email.setText(response.body().getData().getEmail());
                        lovelyProgressDialogs.DismissDialog();
                    }else {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(StudentDashboardActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<_Student_Profile_Model> call, Throwable t) {

                    Log.d("local cause" , ""+t.getLocalizedMessage());
                    Log.d("local cause" , ""+t.getMessage());
                    Toast.makeText(StudentDashboardActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                    lovelyProgressDialogs.DismissDialog();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.studet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Announcement"))
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Announcement_Fragment()).commit();
            fragmentTransaction.addToBackStack( _Student_Announcement_Fragment.class.getName());
//                .add("Announcements", _Student_Announcement_Fragment.class)
        }
        return super.onOptionsItemSelected(item);
    }
}
