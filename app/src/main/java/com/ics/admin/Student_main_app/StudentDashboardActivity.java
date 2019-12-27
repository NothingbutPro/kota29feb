package com.ics.admin.Student_main_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.Student_UI._My_Student_Attendence_Activity;
import com.ics.admin.Student_main_app.Student_UI._Student_Announcement_Fragment;
import com.ics.admin.Student_main_app.Student_UI._Student_Community;
import com.ics.admin.Student_main_app.Student_UI._Student_Pay_Fee;
import com.ics.admin.Student_main_app.Student_UI._Student_Profile_Activity;
import com.ics.admin.Student_main_app.Student_UI._Student_Study_material;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class StudentDashboardActivity extends AppCompatActivity {
    MorphBottomNavigationView morphBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        morphBottomNavigationView = findViewById(R.id.student_bottomnavview);
        NavigationView navigationView = findViewById(R.id.student_nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.studenttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SR");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.student_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getTitle().equals("My Attendance"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _My_Student_Attendence_Activity()).commit();
                    fragmentTransaction.addToBackStack(_My_Student_Attendence_Activity.class.getName());
                    drawer.closeDrawer(GravityCompat.START);
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
                }else if(menuItem.getTitle().equals("Attendance"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _My_Student_Attendence_Activity()).commit();
                    fragmentTransaction.addToBackStack(_My_Student_Attendence_Activity.class.getName());
//                    fragmentTransaction.addToBackStack(null);
//                    drawer.closeDrawer(GravityCompat.START);
                }

                return false;
            }
        });

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
