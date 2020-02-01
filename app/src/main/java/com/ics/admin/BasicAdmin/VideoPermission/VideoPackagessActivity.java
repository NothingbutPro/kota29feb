package com.ics.admin.BasicAdmin.VideoPermission;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.Adapter.AdminAdapters.VideoPackagesAdapter;
import com.ics.admin.Interfaces.GetDates;
import com.ics.admin.Model.ABBCoursemodel;
import com.ics.admin.Model.VideoPackages;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class VideoPackagessActivity extends AppCompatActivity {
    EditText vidpackagetit,viddescrip,mrpprice,discountpri,vidtime,credate,expiredate;
    private RecyclerView grivideorec;
    ArrayList<VideoPackages> videoPackagesArrayList = new ArrayList<>();
    ArrayList<VideoPackages> videoDemoPackagesArrayList = new ArrayList<>();
    VideoPackagesAdapter videoPackagesAdapter;
    VideoPackagesAdapter videoDemoPackagesAdapter;
    Button seleallpacks,makepackbutton;
    int demo_checked;
    public TextView allselecctedvid ,seldemopack,sele_vidtxt;
    public String Selected_packages,Selected_Demo_video;

//    public static List<String> Selected_packagesList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_packages);
        super.onCreate(savedInstanceState);
//        grivideorec= findViewById(R.id.grivideorec);
        vidpackagetit= findViewById(R.id.vidpackagetit);
        viddescrip= findViewById(R.id.viddescrip);
        mrpprice= findViewById(R.id.mrpprice);
        discountpri= findViewById(R.id.discountpri);
        vidtime= findViewById(R.id.vidtime);
        credate= findViewById(R.id.credate);
        expiredate= findViewById(R.id.expiredate);
        seleallpacks= findViewById(R.id.seleallpacks);
        seldemopack= findViewById(R.id.seldemopack);
        allselecctedvid= findViewById(R.id.allselecctedvid);
        makepackbutton= findViewById(R.id.makepackbutton);
        credate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDates getDates = new GetDates();
                getDates.PlaceDate(v.getContext() , credate);
            }
        });
        expiredate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDates getDates = new GetDates();
                getDates.PlaceDate(v.getContext() , expiredate);
            }
        });
        makepackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vidpackagetits = vidpackagetit.getText().toString();
                String viddescrips = viddescrip.getText().toString();
                String mrpprices = mrpprice.getText().toString();
                String discountpris = discountpri.getText().toString();
                String vidtimes = vidtime.getText().toString();
                String credates = vidtime.getText().toString();
                String expiredates = vidtime.getText().toString();
                new MAkePAckFrmVIdeo(vidpackagetits,viddescrips,Selected_packages,credates,expiredates,mrpprices,discountpris,vidtimes).execute();
            }
        });
        seldemopack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeADemoVideo(v);
            }
        });
        seleallpacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPackagesArrayList.clear();
                Selected_packages = null;
                allselecctedvid.setText(null);
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.packagesdialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(lp);
                Button doneselect = dialog.findViewById(R.id.doneselect);
                grivideorec = dialog.findViewById(R.id.grivideorec);
                try {
                    sele_vidtxt.setText("Please Select Packages Video");
                }catch (Exception e)
                {
                    Toast.makeText(VideoPackagessActivity.this, "Please select demo video first", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                new GETALLVIDEOSPackages(new Shared_Preference().getId(v.getContext())).execute();
                doneselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        for(int i =0;i<videoPackagesArrayList.size();i++)
                        {
                            if(i==0)
                            {
                                if(videoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
                                    Log.e("allselecctedvid "+i,""+videoPackagesArrayList.get(i).getPackage_select());
                                    allselecctedvid.setText(videoPackagesArrayList.get(i).getTitle());
                                    Selected_packages = videoPackagesArrayList.get(i).getId();
                                    vidtime.setText(String.valueOf(Integer.valueOf(videoPackagesArrayList.get(i).getVideo_time())));
                                }
                            }else {
                                if(videoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
                                    Log.e("allselecctedvid "+i,""+videoPackagesArrayList.get(i).getPackage_select());
                                    allselecctedvid.setText(allselecctedvid.getText().toString()+","+videoPackagesArrayList.get(i).getTitle());
                                    Selected_packages = Selected_packages +","+videoPackagesArrayList.get(i).getId();
                                    vidtime.setText(String.valueOf(Integer.valueOf(vidtime.getText().toString())+Integer.valueOf(videoPackagesArrayList.get(i).getVideo_time())));
                                }
                            }
                        }
                        vidtime.setText(Integer.valueOf(vidtime.getText().toString())+" min");
                    }
                });
                dialog.show();

            }
        });


    }

    private void MakeADemoVideo(View v) {
        videoDemoPackagesArrayList.clear();
        Selected_Demo_video = null;
//        allselecctedvid.setText(null);
        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.packagesdialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        Button doneselect = dialog.findViewById(R.id.doneselect);
        grivideorec = dialog.findViewById(R.id.grivideorec);
        sele_vidtxt = dialog.findViewById(R.id.sele_vidtxt);
        sele_vidtxt.setText("Please Select Demo Video(only 1)");
        new GETALLVIDEOSPackagesForDemo(new Shared_Preference().getId(v.getContext())).execute();
        doneselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                for(int i =0;i<videoDemoPackagesArrayList.size();i++)
                {
                    if(i==0)
                    {
                        if(videoDemoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
                            demo_checked =demo_checked+1;
                            Toast.makeText(VideoPackagessActivity.this, "Demo Video Selected", Toast.LENGTH_SHORT).show();
                            Selected_Demo_video = videoDemoPackagesArrayList.get(i).getId();
                        }
                    }else {
                        if(videoDemoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
                            demo_checked =demo_checked+1;
                            if(demo_checked >1) {
                                Toast.makeText(VideoPackagessActivity.this, "More than 1 video has been checked", Toast.LENGTH_LONG).show();
                                Log.e("More than " + i, "" + videoDemoPackagesArrayList.get(i).getPackage_select());
                                Selected_Demo_video = "";
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                                builder1.setMessage("You can only choose 1 video for demo ");
                                builder1.setCancelable(false);
                                builder1.setPositiveButton(
                                        "Select again",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogs, int id) {
                                                dialog.show();
                                                dialogs.cancel();
                                                demo_checked =0;
                                            }
                                        });
                                builder1.setNegativeButton("Dismiss all", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogs, int which) {
                                        dialogs.cancel();
                                        demo_checked =0;
                                    }
                                });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                                i= videoDemoPackagesArrayList.size();
                            }else {
                                demo_checked =demo_checked+1;
                                Selected_Demo_video = videoDemoPackagesArrayList.get(i).getId();
                            }
                        }
                    }
                }
            }
        });
        dialog.show();

    }

    private class GETALLVIDEOSPackages extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETALLVIDEOSPackages(String i) {
            this.userid = i;

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_video");

                JSONObject postDataParams = new JSONObject();

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

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")) {
//                        Toast.makeText(VideoPackagessActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String school_id = jsonObject.getString("school_id");
                            String addedby = jsonObject.getString("addedby");
                            String class_id = jsonObject.getString("class_id");
                            String course_id = jsonObject.getString("course_id");
                            String date = jsonObject.getString("date");
                            String video_image = jsonObject.getString("video_image");
                            String video = jsonObject.getString("video");
                            String video_url = jsonObject.getString("video_url");
                            String title = jsonObject.getString("title");
                            String description = jsonObject.getString("description");
                            String status = jsonObject.getString("status");
                            String Class = jsonObject.getString("Class");
                            String Course = jsonObject.getString("Course");
                            String video_time = jsonObject.getString("video_time");
                            String package_select = "Checked";
                            videoPackagesArrayList.add(new VideoPackages(id,school_id,addedby,class_id,course_id,date,video_image,video,video_url,title,description,status,Class,Course,package_select,video_time));
                        }
                        videoPackagesAdapter = new VideoPackagesAdapter(VideoPackagessActivity.this,videoPackagesArrayList);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(VideoPackagessActivity.this);
//                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(VideoPackagessActivity.this,1);
                        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        grivideorec.setLayoutManager(gridLayoutManager);
                        grivideorec.setAdapter(videoPackagesAdapter);
                    }else {
                        Toast.makeText(VideoPackagessActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

//                    batch_spin_assign.remo
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

    private class MAkePAckFrmVIdeo extends AsyncTask<String, Void, String> {

        String userid;
        String vidpackagetits;
        String viddescrips;
        String selected_packages;
        String credates;
        String expiredates;
        String mrpprice;
        String discountpris;
        String vidtimes;
        private Dialog dialog;

        public MAkePAckFrmVIdeo(String vidpackagetits, String viddescrips, String selected_packages, String credates, String expiredates, String mrpprice, String discountpris, String vidtimes) {
            this.vidpackagetits =vidpackagetits;
            this.viddescrips =viddescrips;
            this.selected_packages =selected_packages;
            this.credates =credates;
            this.expiredates =expiredates;
            this.mrpprice =mrpprice;
            this.discountpris =discountpris;
            this.vidtimes =vidtimes;

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/addvideoPackage");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("school_id", new Shared_Preference().getId(VideoPackagessActivity.this));
                postDataParams.put("title", vidpackagetits);
                postDataParams.put("description", viddescrips);
                postDataParams.put("video_id", selected_packages);
                postDataParams.put("create_date", credates);
                postDataParams.put("expire_date", expiredates);
                postDataParams.put("amount", mrpprice);
                postDataParams.put("discount_amount", discountpris);
                postDataParams.put("video_time", vidtimes);
                postDataParams.put("demo_video_id", Selected_Demo_video);

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

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")) {
                        Toast.makeText(VideoPackagessActivity.this, " Success", Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        Toast.makeText(VideoPackagessActivity.this, "Error found Please check the fields", Toast.LENGTH_SHORT).show();
                    }
                    cancel(true);
                } catch (JSONException e) {
                    cancel(true);
//                    batch_spin_assign.remo
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

    private class GETALLVIDEOSPackagesForDemo extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETALLVIDEOSPackagesForDemo(String i) {
            this.userid = i;

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_video");

                JSONObject postDataParams = new JSONObject();

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

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")) {
//                        Toast.makeText(VideoPackagessActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String school_id = jsonObject.getString("school_id");
                            String addedby = jsonObject.getString("addedby");
                            String class_id = jsonObject.getString("class_id");
                            String course_id = jsonObject.getString("course_id");
                            String date = jsonObject.getString("date");
                            String video_image = jsonObject.getString("video_image");
                            String video = jsonObject.getString("video");
                            String video_url = jsonObject.getString("video_url");
                            String title = jsonObject.getString("title");
                            String description = jsonObject.getString("description");
                            String status = jsonObject.getString("status");
                            String Class = jsonObject.getString("Class");
                            String Course = jsonObject.getString("Course");
                            String video_time = jsonObject.getString("video_time");
                            String package_select = "Checked";
                            videoDemoPackagesArrayList.add(new VideoPackages(id,school_id,addedby,class_id,course_id,date,video_image,video,video_url,title,description,status,Class,Course,package_select,video_time));
                        }
                        videoDemoPackagesAdapter = new VideoPackagesAdapter(VideoPackagessActivity.this,videoDemoPackagesArrayList);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(VideoPackagessActivity.this);
//                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(VideoPackagessActivity.this,1);
                        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        grivideorec.setLayoutManager(gridLayoutManager);
                        grivideorec.setAdapter(videoDemoPackagesAdapter);
                    }else {
                        Toast.makeText(VideoPackagessActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                    }
                    cancel(true);
                } catch (JSONException e) {
                    cancel(true);
//                    batch_spin_assign.remo
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
