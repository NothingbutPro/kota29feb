package com.ics.admin.Fragment;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.OnlyVideoAdapter;
import com.ics.admin.Adapter.AdminAdapters.VideosAdminPackageAdapter;
import com.ics.admin.BasicAdmin.VideoPermission.VideosViewActivity;
import com.ics.admin.Model.VideoAdminPackages;
import com.ics.admin.Model.VideosOnly;
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

import javax.net.ssl.HttpsURLConnection;


public class VideoLibraryFragment extends Fragment {
    RecyclerView allvidpackagesrec,allvideosaddedrec;
    ArrayList<VideoAdminPackages> videoAdminPackagesList = new ArrayList<>();
    ArrayList<VideosOnly> videosOnlyArrayList = new ArrayList<>();
    Button videopackgesbtn,videoonlybtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_library , container ,false);
        allvidpackagesrec =view.findViewById(R.id.allvidpackagesrec);
        allvideosaddedrec =view.findViewById(R.id.allvideosaddedrec);
        videopackgesbtn =view.findViewById(R.id.videopackgesbtn);
        videoonlybtn =view.findViewById(R.id.videoonlybtn);
        new GETALLVIDEOSFRAG(new Shared_Preference().getId(getActivity())).execute();
        new GETONLYALLVIDEOSFAG(new Shared_Preference().getId(getActivity())).execute();
        videopackgesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allvideosaddedrec.setVisibility(View.GONE);
                allvidpackagesrec.setVisibility(View.VISIBLE);

            }
        });
        videoonlybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allvideosaddedrec.setVisibility(View.VISIBLE);
                allvidpackagesrec.setVisibility(View.GONE);
                allvideosaddedrec.setVisibility(View.VISIBLE);
            }
        });
        return view;
//        return inflater.inflate(R.layout.fragment_video_library, container, false);
    }


    private class GETALLVIDEOSFRAG extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;


        public GETALLVIDEOSFRAG(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_videoPackage");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", Faculty_id);


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
                Log.e("PostReg Videos are ", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(!jsonObject1.getBoolean("responce")){
                        try {
                            Toast.makeText(getActivity(), "Nothing found", Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {

                        }

//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("id");
                            String school_id = jsonObject.getString("school_id");
                            String video_id = jsonObject.getString("video_id");
                            String title = jsonObject.getString("title");
                            String description = jsonObject.getString("description");
                            String create_date = jsonObject.getString("create_date");
                            String expire_date = jsonObject.getString("expire_date");
                            String amount = jsonObject.getString("amount");
                            String addedby = jsonObject.getString("addedby");
                            String discount_amount = jsonObject.getString("discount_amount");
                            String video_time = jsonObject.getString("video_time");
                            String status = jsonObject.getString("status");
                            videoAdminPackagesList.add(new VideoAdminPackages(userid,school_id,addedby,video_id,title,description,create_date,expire_date,amount,discount_amount,video_time,status));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        allvidpackagesrec.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                        VideosAdminPackageAdapter videoPackagesAdapter = new VideosAdminPackageAdapter(getActivity(),videoAdminPackagesList);
                        allvidpackagesrec.setAdapter(videoPackagesAdapter); // set the Adapter to RecyclerView
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

    private class GETONLYALLVIDEOSFAG extends AsyncTask<String, Void, String>  {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;


        public GETONLYALLVIDEOSFAG(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_video");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", Faculty_id);


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
                    if(!jsonObject1.getBoolean("responce")){
                        try {
                            Toast.makeText(getContext(), "Nothing found", Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
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
                            String Class_ = jsonObject.getString("Class");
                            String Course = jsonObject.getString("Course");

                            videosOnlyArrayList.add(new VideosOnly(id,school_id,addedby,class_id,course_id,date,video_image,video,video_url,title,description,status,Class_,Course));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        OnlyVideoAdapter videoPackagesAdapter = new OnlyVideoAdapter(getActivity(),videosOnlyArrayList);
                        allvideosaddedrec.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
                        allvideosaddedrec.setAdapter(videoPackagesAdapter); // set the Adapter to RecyclerView
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
