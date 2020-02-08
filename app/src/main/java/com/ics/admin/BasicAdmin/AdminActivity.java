package com.ics.admin.BasicAdmin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Fragment.AdminEFragment;
import com.ics.admin.Fragment.BatchFragment;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.Fragment.EnquiryFragment;
import com.ics.admin.Fragment.FacultyFragment;

import com.ics.admin.Fragment.Faculty_Fragments.HomeWorkByFaculty;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottom_nav_view;
    Shared_Preference shared_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottom_nav_view = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin");


        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.navi_home:
                        fragment = new CommunityFragment();
                        break;
                    case R.id.navi_faculty:
                        fragment = new FacultyFragment();
                        break;

                    case R.id.navi_video_libary:
                        fragment = new HomeWorkByFaculty();
//                        loadFragment(fragment);
                        break;
                    case R.id.navi_Batch:
                        fragment = new BatchFragment();
                        break;
                    case R.id.navi_student_material:
                         fragment = new AdminEFragment();

                        break;
                }
                return loadFragment(fragment);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        toolbar.setTitle("Admin");
        loadFragment(new CommunityFragment());
        new GETMYBASICINFO(headerLayout,new Shared_Preference().getId(this)).execute();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment!= null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container,fragment)
                    .commit();
       return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.admin, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        }
        else if (id == R.id.nav_profile) {
            Intent intent = new Intent(AdminActivity.this ,AdminProfileActivity.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_about) {
        }
        else if (id == R.id.nav_contact) {
        }
        else if (id == R.id.nav_feedback) {
        }
        else if (id == R.id.nav_logout) {
           shared_preference = new Shared_Preference();
           shared_preference.setId(AdminActivity.this,"","",false);
            Intent in =new Intent(AdminActivity.this, OTPActivity.class);
            startActivity(in);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
   }

    private class GETMYBASICINFO extends AsyncTask<String, Void, String> {

        String userid;
        // String Faculty_id;
        private Dialog dialog;
        View headerLayout;

        public GETMYBASICINFO(View headerLayout, String i) {
            this.userid = i;
            this.headerLayout = headerLayout;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AdminActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getprofile");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", userid);


                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result).getJSONObject("data");
                    if(new JSONObject(result).getBoolean("responce"))
                    {
//                        Toast.makeText(AdminProfileActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        TextView user_name = headerLayout.findViewById(R.id.admin_name_hd);
                        TextView admin_emails = headerLayout.findViewById(R.id.admin_emails);
                        user_name.setText(jsonObject1.getString("name"));
//                        if(jsonObject1.getString("type").equals("1")) {
//                            types.setText("Admin");
//                        }else if( jsonObject1.getString("type").equals("2")){
//                            types.setText("Faculty");
//                        }else {
//                            types.setText("Staff");
//                        }
//                        if(jsonObject1.getString("status").equals("1"))
//                        {
//                            status_of.setText("Active");
//                            status_of.setTextColor(Color.GREEN);
//                        }else {
//                            status_of.setText("Not Active");
//                            status_of.setTextColor(Color.RED);
//                        }
//                        profile_mob.setText(jsonObject1.getString("mobile"));
                        admin_emails.setText(jsonObject1.getString("email"));
//                        pro_address.setText(jsonObject1.getString("address"));
//                        Toast.makeText(AdminProfileActivity.this, "password is"+jsonObject1.getString("address"), Toast.LENGTH_SHORT).show();
//                        pro_password.setText(jsonObject1.getString("name"));
//                        Toast.makeText(AdminProfileActivity.this, "password is"+jsonObject1.getString("password"), Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {



                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
            adminProgressdialog.EndProgress();
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}

