package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.VideoPermission.VideoPackagessActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.Model.VideoAdminPackages;
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

public class VideosAdminPackageAdapter extends RecyclerView.Adapter<VideosAdminPackageAdapter.MyViewHolder> {

    ArrayList<VideoAdminPackages> batchArrayList = new ArrayList<>();
    ArrayList<VideoPackages> videoPackagesArrayList = new ArrayList<>();
    VideoPackagesAdapter videoPackagesAdapter;
    public String allselecctedvid;
    public String Selected_packages;
    Activity activity;
    private RecyclerView grivideorec;

    public VideosAdminPackageAdapter(Activity activity, ArrayList<VideoAdminPackages> batchArrayList) {
        this.activity = activity;
        this.batchArrayList = batchArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public VideosAdminPackageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videopackges, viewGroup, false);

        return new VideosAdminPackageAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdminPackageAdapter.MyViewHolder myViewHolder, int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);
        VideoAdminPackages videoPackages = batchArrayList.get(i);
        myViewHolder.videotits.setText(""+batchArrayList.get(i).getTitle());
        myViewHolder.batchvid.setText("Description : "+batchArrayList.get(i).getDescription());

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

        myViewHolder.editvidepckas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myViewHolder.hide_li.getVisibility() ==View.VISIBLE)
                {
                    myViewHolder.hide_li.setVisibility(View.GONE);
                }else {
                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
                }
            }
        });
    myViewHolder.selvids.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            videoPackagesArrayList.clear();
            Selected_packages = null;
//            allselecctedvid.setText(null);
            final Dialog dialog = new Dialog(view.getContext());
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
            new GETALLVIDEOSPackagesx(new Shared_Preference().getId(view.getContext())).execute();
            doneselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    for(int i =0;i<videoPackagesArrayList.size();i++)
                    {
                        if(i==0)
                        {
                            if(videoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
//                                Log.e("allselecctedvid "+i,""+videoPackagesArrayList.get(i).getPackage_select());
//                                allselecctedvid.setText(videoPackagesArrayList.get(i).getTitle());
                                allselecctedvid = videoPackagesArrayList.get(i).getTitle();
                                Selected_packages = videoPackagesArrayList.get(i).getId();
                            }
                        }else {
                            if(videoPackagesArrayList.get(i).getPackage_select().equals("Checked")) {
//                                Log.e("allselecctedvid "+i,""+videoPackagesArrayList.get(i).getPackage_select());
//                                allselecctedvid.setText(allselecctedvid.getText().toString()+","+videoPackagesArrayList.get(i).getTitle());
                                allselecctedvid = allselecctedvid.toString()+","+videoPackagesArrayList.get(i).getTitle();
                                Selected_packages = Selected_packages +","+videoPackagesArrayList.get(i).getId();
                            }
                        }
                    }
                    myViewHolder.selvids.setText(""+allselecctedvid);
                }
            });
            dialog.show();

        }
    });
        myViewHolder.delbyvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteDialog().DeleteDialog("id",activity,batchArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_videoPackage");
//                new CommunityFragment.DELETStuff("id",activity,batchArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_video").execute();
            }
        });
        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = myViewHolder.newtitle.getText().toString();
                String description = myViewHolder.new_description.getText().toString();
                String create_date = myViewHolder.new_create_date.getText().toString();
                String expire_date = myViewHolder.new_expire_date.getText().toString();
                String amount = myViewHolder.new_amount.getText().toString();
                String discount_amount = myViewHolder.new_discount_amount.getText().toString();
                String video_time = myViewHolder.new_video_time.getText().toString();
                String url = "http://ihisaab.in/school_lms/api/updatevideoPackage";
                new EditStuffs("updatevideoPackage", activity,Selected_packages,batchArrayList.get(i).getId(),title,description,create_date, expire_date,amount,discount_amount,video_time,url,"").execute();

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
        CheckBox checvidpackag;
        ImageView add_student;
        Button delbyvideo,editvidepckas ,edit_btn_save,selvids;
        LinearLayout hide_li;
        EditText newtitle , new_description, new_create_date,new_expire_date,new_amount,new_discount_amount,new_video_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videotits = (TextView) itemView.findViewById(R.id.videotits);
            batchvid = (TextView) itemView.findViewById(R.id.batchvid);
            checvidpackag = itemView.findViewById(R.id.checvidpackag);
            delbyvideo = itemView.findViewById(R.id.delbyvideo);
//            editvidepack = itemView.findViewById(R.id.editvidepckas);
        //+++++++++++For Videos++++++++++++++++++
            hide_li =  itemView.findViewById(R.id.hide_li);
            //For Videopackage upload
            newtitle = itemView.findViewById(R.id.new_title);
            editvidepckas = itemView.findViewById(R.id.editvidepckas);
            new_description = itemView.findViewById(R.id.new_description);
            new_create_date = itemView.findViewById(R.id.new_create_date);
            new_expire_date = itemView.findViewById(R.id.new_expire_date);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            new_amount = itemView.findViewById(R.id.new_amount);
            new_discount_amount = itemView.findViewById(R.id.newnydiscount_amount);
            new_video_time = itemView.findViewById(R.id.newnynewvideo_time);
            selvids = itemView.findViewById(R.id.selvids);
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
}