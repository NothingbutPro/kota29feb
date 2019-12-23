package com.ics.admin.Student_main_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ics.admin.R;
import com.ics.admin.Student_main_app.Student_UI._Student_Pay_Fee;
import com.ics.admin.Student_main_app.Student_UI._Student_Study_material;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class StudentDashboardActivity extends AppCompatActivity {
    MorphBottomNavigationView morphBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        morphBottomNavigationView = findViewById(R.id.student_bottomnavview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.studenttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SR");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Study_material()).commit();
        fragmentTransaction.addToBackStack(null);
        morphBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(StudentDashboardActivity.this, "You have selecetasdf "+menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if(menuItem.getTitle().equals("Study Material"))
                {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Study_material()).commit();
                    fragmentTransaction.addToBackStack(null);
                }else if((menuItem.getTitle().toString().contains("Fee") )) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.student_frame_container ,new _Student_Pay_Fee()).commit();
                    fragmentTransaction.addToBackStack(null);

                    Toast.makeText(StudentDashboardActivity.this, "wait working", Toast.LENGTH_SHORT).show();
                }
//                if(menuItem.getTitle().equals("navi_faculty"))
//                {
//                    Toast.makeText(StudentDashboardActivity.this, "You have selecetasdf", Toast.LENGTH_SHORT).show();
//                }
                return false;
            }
        });

    }
}
