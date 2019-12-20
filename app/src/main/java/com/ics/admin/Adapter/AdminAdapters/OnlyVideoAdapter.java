package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.VideoPermission.AddVideoActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Model.ABBCoursemodel;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.VideoAdminPackages;
import com.ics.admin.Model.VideoPackages;
import com.ics.admin.Model.VideosOnly;
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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class OnlyVideoAdapter extends RecyclerView.Adapter<OnlyVideoAdapter.MyViewHolder> {
    private ArrayList<ClassNAmes> class_names = new ArrayList<>();
    ArrayList<VideosOnly> batchArrayList = new ArrayList<>();
    private ArrayList<ABBCoursemodel> batchArrayListAbbCoursemodelArrayList = new ArrayList<ABBCoursemodel>();
    ArrayList<VideoPackages> videoPackagesArrayList = new ArrayList<>();
    VideoPackagesAdapter videoPackagesAdapter;
    String selected_batch , sel_batch;
    String selected_class , sel_id;
    private ArrayList<String> batch_names = new ArrayList<>();

    Activity activity;
    private RecyclerView grivideorec;

    public OnlyVideoAdapter(Activity activity, ArrayList<VideosOnly> batchArrayList) {
        this.activity = activity;
        this.batchArrayList = batchArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public OnlyVideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onlyvideoadapter, viewGroup, false);

        return new OnlyVideoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlyVideoAdapter.MyViewHolder myViewHolder, int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);
        VideosOnly videoPackages = batchArrayList.get(i);
        Toast.makeText(activity, "video title is batchArrayList.get(i).getTitle()", Toast.LENGTH_SHORT).show();
        myViewHolder.videotits.setText(""+batchArrayList.get(i).getDescription());
        myViewHolder.batchvid.setText(""+batchArrayList.get(i).getCourse());

        myViewHolder.checvidpackag.setChecked(true);
//        myViewHolder.hide_li.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(myViewHolder.hide_li.getVisibility() == View.VISIBLE)
//                {
//                    myViewHolder.hide_li.setVisibility(View.GONE);
//                }else {
//                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

        myViewHolder.editvideonly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myViewHolder.hide_li.getVisibility() ==View.VISIBLE)
                {
                    myViewHolder.hide_li.setVisibility(View.GONE);
                }else {
                    class_names.clear();
                    batch_names.clear();
                    batchArrayListAbbCoursemodelArrayList.clear();
                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Executing", Toast.LENGTH_SHORT).show();
                    new GETAllClassesforedit(new Shared_Preference().getId((Context) activity) , myViewHolder).execute();
                }
            }
        });

        myViewHolder.delvidonly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteDialog().DeleteDialog("id",activity,batchArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_videoPackage");
//                new CommunityFragment.DELETStuff("id",activity,batchArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_video").execute();
            }
        });
        myViewHolder.edit_vid_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "usfhdksjdfhjksdf", Toast.LENGTH_SHORT).show();
                String url = "http://ihisaab.in/school_lms/api/update_video";
                new EditStuffs("update_video",activity ,sel_id,sel_batch,batchArrayListAbbCoursemodelArrayList.get(i).getUserId(),url,myViewHolder.videotits.getText().toString()).execute();
            }
        });
    }

    @Override
    public int getItemCount() {

        return batchArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView videotits,batchvid;
        Button edit_vid_btn_save;
        CheckBox checvidpackag;

        Button delvidonly,editvideonly;
        LinearLayout hide_li;
        Spinner new_class_spin,new_course_spin;
        TextView new_tits;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videotits = (TextView) itemView.findViewById(R.id.videotits);
            batchvid = (TextView) itemView.findViewById(R.id.batchvid);
            checvidpackag = itemView.findViewById(R.id.checvidpackag);
            delvidonly = itemView.findViewById(R.id.delvidonly);
            new_class_spin = itemView.findViewById(R.id.new_class);
            new_course_spin = itemView.findViewById(R.id.new_course);
            edit_vid_btn_save = itemView.findViewById(R.id.edit_vid_btn_save);
            new_tits = itemView.findViewById(R.id.new_tits);

//            editvidepack = itemView.findViewById(R.id.editvidepckas);
            //+++++++++++For Videos++++++++++++++++++
            hide_li =  itemView.findViewById(R.id.hide_li);
            //For Videopackage upload
            editvideonly = itemView.findViewById(R.id.editvideonly);
            //+++++++++++++++++++++++For
        }
    }

    private class GETALLVIDEOSPackagesx extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETALLVIDEOSPackagesx(String i) {
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
                            String package_select = "Checked";
                            videoPackagesArrayList.add(new VideoPackages(id,school_id,addedby,class_id,course_id,date,video_image,video,video_url,title,description,status,Class,Course,package_select));
                        }
                        videoPackagesAdapter = new VideoPackagesAdapter(activity,videoPackagesArrayList);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(VideoPackagessActivity.this);
//                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,1);
                        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        grivideorec.setLayoutManager(gridLayoutManager);
                        grivideorec.setAdapter(videoPackagesAdapter);
                    }else {
                        Toast.makeText(activity, "Nothing found", Toast.LENGTH_SHORT).show();
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

    private class GETAllClassesforedit extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;
        MyViewHolder myViewHolder;
        public GETAllClassesforedit(String id, MyViewHolder myViewHolder) {
            this.id = id;
            //  this.course=course;
            this.myViewHolder=myViewHolder;
        }



        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/classlist");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", id);

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
                Log.e("PostRegistration ", "for get class  "+result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        Toast.makeText(activity, "class found", Toast.LENGTH_SHORT).show();
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        class_names = new ArrayList<>();
                        for (int i=0 ; i<jarr.length() ; i++){

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id,cl_name));
                        }

                        final ArrayList <String> list_class = new ArrayList<>();
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item
                                ,list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        myViewHolder.new_class_spin.setAdapter(adapter);
                        myViewHolder.new_class_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_class = list_class.get(position);
                                sel_id =""+class_names.get(position).getUserId();
                                Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                                batch_names.clear();
                                batchArrayListAbbCoursemodelArrayList.clear();

                                new GETAllBathcesedit(new Shared_Preference().getId((Context) activity),sel_id).execute();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);

                    }
                    else
                    {
                        Toast.makeText(activity, "No class found", Toast.LENGTH_SHORT).show();
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

        private class GETAllBathcesedit extends AsyncTask<String, Void, String>
        {

            String userid;
            String calls_id;
            // String Faculty_id;
            private Dialog dialog;


            public GETAllBathcesedit(String i, String sel_id) {
                this.userid = i;
                this.calls_id = sel_id;
            }

            @Override
            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getcourse");

                    JSONObject postDataParams = new JSONObject();

                    postDataParams.put("user_id", userid);
                    postDataParams.put("class_id", calls_id);


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
                            Toast.makeText(activity, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.clear();
                            batchArrayListAbbCoursemodelArrayList.clear();
//                        batch_names.clear();
                            myViewHolder.new_course_spin.setAdapter(null);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(activity, "batch found", Toast.LENGTH_SHORT).show();
                            for(int i=0;i<jsonObject1.getJSONArray("data").length();i++)
                            {
                                JSONObject jsonObject2 = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String id = jsonObject2.getString("id");
                                String Class = jsonObject2.getString("Course");
                                String title = jsonObject2.getString("addedby");
                                String class_id = jsonObject2.getString("class_id");
//                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");
                                batch_names.add(Class);
                                batchArrayListAbbCoursemodelArrayList.add(new ABBCoursemodel(id,Class,title,"Course :","Class:",class_id));

                            }

//                            batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>((Context)activity, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            myViewHolder.new_course_spin.setAdapter(adapter);
                            myViewHolder.new_course_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selected_batch = batch_names.get(position);
                                    sel_batch =""+batchArrayListAbbCoursemodelArrayList.get(position).getUserId();
                                    Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

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
    }
}
