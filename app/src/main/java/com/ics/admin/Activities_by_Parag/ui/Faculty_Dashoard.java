package com.ics.admin.Activities_by_Parag.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.MenuExpandableAdapter;
import com.ics.admin.BasicAdmin.AdminActivity;
import com.ics.admin.Fragment.AdminAFragment;
import com.ics.admin.Fragment.AdminBFragment;
import com.ics.admin.Fragment.AdminCFragment;
import com.ics.admin.Fragment.BatchFragment;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.Fragment.FacultyFragment;
import com.ics.admin.Fragment.VideoFragment;
import com.ics.admin.Fragment.VideoLibraryFragment;
import com.ics.admin.Student_main_app.Student_UI._Student_HomeWork_Fragment;
import com.ics.admin.Fragment.AdminEFragment;
import com.ics.admin.Model.MenuPermisssion;
import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class Faculty_Dashoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottom_nav_faculty;
DrawerLayout drawer;
TextView faculty_logout;
    public List<String> _MenuPermisssionslistDataHeader = new ArrayList<>();
    ArrayList<SubMenuPermissions> menuPermissionsSubList = new ArrayList<>();
    ArrayList<String> menuPermissionsSubListString = new ArrayList<>();
    public ArrayList<MenuPermisssion> menuPermisssionheaderListStrings;
    ArrayList<String> menuPermisssionheaderList = new ArrayList<>();
    public HashMap<SubMenuPermissions, List<SubMenuPermissions>> _ListHashMaplistDataChild = new HashMap<>();
    RecyclerView navexpandable;
    private MenuExpandableAdapter menuExpandableAdapter;
   Shared_Preference shared_preference;
    // private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty__dashoard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        faculty_logout=(TextView)findViewById(R.id.faculty_logout);
        bottom_nav_faculty = (BottomNavigationView) findViewById(R.id.bottom_nav_view_fact);
        NavigationView navigationView = findViewById(R.id.fact_nav_view);
        navexpandable = findViewById(R.id.navexpandable);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Faculty");

        shared_preference = new Shared_Preference();
        new GETALLMYPERMISSIONS(Shared_Preference.getFacultyId(Faculty_Dashoard.this)).execute();
        faculty_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_preference.setId(Faculty_Dashoard.this, "", "", false);
                Intent in = new Intent(Faculty_Dashoard.this, OTPActivity.class);
                startActivity(in);
                finish();
            }
        });

        bottom_nav_faculty.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.navi_fact_home:
//                        Toast.makeText(Faculty_Dashoard.this, "navi_fact_home selected", Toast.LENGTH_SHORT).show();
//                        fragment = new CommunityFragment();
//                        loadFragment(fragment);
//                        Toast.makeText(Faculty_Dashoard.this, "Not allowed", Toast.LENGTH_SHORT).show();
                        fragment = new VideoLibraryFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.navi_fact_faculty:
//                        Toast.makeText(Faculty_Dashoard.this, "navi_vid_lib selected", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Faculty_Dashoard.this, "Not allowed", Toast.LENGTH_SHORT).show();
                        fragment = new VideoLibraryFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.navi__fact_Batch:
                        Toast.makeText(Faculty_Dashoard.this, "navi__fact_Batch selected", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getAc)
//                        fragment = new BatchFragment();
//                        loadFragment(fragment);
                        break;
                    case R.id.nav_fact_commun:
//                        Toast.makeText(Faculty_Dashoard.this, "nav_fact_commun selected", Toast.LENGTH_SHORT).show();
                        fragment = new AdminEFragment();
                        loadFragment(fragment);
                        break;
                        case R.id.navi_vid_lib:
//                        Toast.makeText(Faculty_Dashoard.this, "nav_fact_commun selected", Toast.LENGTH_SHORT).show();
                        fragment = new VideoFragment();
                        loadFragment(fragment);
                        break;
                }
                return loadFragment(fragment);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.faulty_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
         toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Faculty");
        loadFragment(new VideoLibraryFragment());
        bottom_nav_faculty.setSelectedItemId( R.id.navi_fact_home);

    }


    private boolean loadFragment(Fragment fragmentAdmin) {

        if (fragmentAdmin != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                      .replace(R.id.frame_fact_container,fragmentAdmin)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.navigation_Home) {
            Intent intent = new Intent(Faculty_Dashoard.this, MYAdminPermissions.class);
            intent.putExtra("teacher_id", "");
            startActivity(intent);


        } else if (id == R.id.navination_profile) {

        } else if (id == R.id.navination_about) {

        } else if (id == R.id.navigation_contact) {

        } else if (id == R.id.navigation_feedback) {


        } else if (id == R.id.navigation_logout) {


        }
        return true;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.faculty__dashoard, menu);
        return true;
    }




    private class GETALLMYPERMISSIONS extends AsyncTask<String, Void, String> {
        String Faculty_id;
        private Dialog dialog;

        public GETALLMYPERMISSIONS(String Faculty_id) {
            this.Faculty_id = Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsidemenu");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("user_id", OTPActivity.Faculty_id);
                postDataParams.put("user_id",Faculty_id );
//                postDataParams.put("teacher_id", "4");


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

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
                        Toast.makeText(getApplication(),"You are not registerd"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String menu_name =null;
                        SubMenuPermissions sUbMenuModel = null;
                        for(int i=0;i<jsonObject.getJSONObject("data").length();i++)
                        {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("a"+i);

                            for (int px=0;px<jsonArray.length();px++)
                            {
                                String menu_id = jsonArray.getJSONObject(px).getString("menu_id");
                                menu_name = jsonArray.getJSONObject(px).getString("menu_name");
                                String submenu = jsonArray.getJSONObject(px).getString("submenu");
                                sUbMenuModel = new SubMenuPermissions(menu_id,menu_name,submenu);

//                                menuPermissionsSubListString.
                                if(px==0) {
                                    menuPermissionsSubList.add(sUbMenuModel);
//                                    _MenuPermisssionslistDataHeader.add(menu_name);

                                }

                            }
//                            _ListHashMaplistDataChild.put(sUbMenuModel,menuPermissionsSubList);

                        }
//                        Log.e("full hash map",""+_ListHashMaplistDataChild.entrySet());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Faculty_Dashoard.this);
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        navexpandable.setLayoutManager(linearLayoutManager);
                        menuExpandableAdapter = new MenuExpandableAdapter(Faculty_Dashoard.this, menuPermissionsSubList);
                        navexpandable.setAdapter(menuExpandableAdapter);
                        menuExpandableAdapter.notifyDataSetChanged();
                        navexpandable.requestLayout();
//                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
//                        {
//                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
//                            String  permission_id =jsonObject1.getString("permission_id");
//                            String  menu_id =jsonObject1.getString("menu_id");
//                            String  menu_name =jsonObject1.getString("menu_name");
//                            String  status =jsonObject1.getString("status");
//                            if(i<5)
//                            {
//                                MenuItem fact1 = navigationView.findViewById(R.id.nav_home);
//                                fact1.setTitle(""+menu_name);
//
//                            }
////                            menuPermisssionList.add(new MenuPermisssion(permission_id,menu_id,menu_name,status));
////sdf
//                        }
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
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

    private class Verifyotp extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        private Dialog dialog;


        public Verifyotp(String toString, String s) {

            this.Mobile_Number = toString;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getmenulist");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id", Mobile_Number);
//                postDataParams.put("mobile", mobile.getText().toString());


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

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
//                        getotp.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        String type = jsonObject.getJSONObject("data").getString("type");
                        String Faculty_id = jsonObject.getJSONObject("data").getString("type");
                        if(type.equals("1")) {
                            Intent intent1 = new Intent(Faculty_Dashoard.this, AdminActivity.class);
                            startActivity(intent1);
//                            finish();
                        }else {
                            Intent intent1 = new Intent(Faculty_Dashoard.this, Faculty_Dashoard.class);
                            startActivity(intent1);
//                            finish();
                        }
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
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