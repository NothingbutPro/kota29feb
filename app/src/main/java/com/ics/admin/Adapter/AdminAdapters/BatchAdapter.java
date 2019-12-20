package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.Masters.Forclass.ClassViewActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Fragment.CommunityFragment;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.R;
import com.ics.admin.BasicAdmin.Masters.Batch.AddStudentActivity;

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

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.MyViewHolder> {

    ArrayList<ABBBatch>batchArrayList = new ArrayList<>();
    Activity activity;

    public BatchAdapter(Activity activity, ArrayList<ABBBatch> batchArrayList) {
        this.activity = activity;
        this.batchArrayList = batchArrayList;
      //  this.Images = Images;
    }

    @NonNull
    @Override
    public BatchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.batch_adapter, viewGroup, false);

        return new BatchAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchAdapter.MyViewHolder myViewHolder, int i) {
//        ABBBatch ob = Names.get(i);
//        ABBBatch ob1=
//        myViewHolder.name.setText("" + ob.getBatch()+"");
//        myViewHolder.add_class.setText(""+batchArrayList.get(i).get);

        myViewHolder.name.setText(""+batchArrayList.get(i).getBatch());
        myViewHolder.add_class.setText(""+batchArrayList.get(i).getClass_id());
        myViewHolder.mainname.setText(""+batchArrayList.get(i).getMainName() +": ");
        myViewHolder.mainsubname.setText(""+batchArrayList.get(i).getMainSubName());
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myedit = myViewHolder.batchedit.getText().toString();
                String url = "http://ihisaab.in/school_lms/Adminapi/update_Batch";
                new EditStuffs("update_Batch", activity, batchArrayList.get(i).getActual_class_id(),batchArrayList.get(i).getUserId(),myedit, url).execute();
            }
        });
        myViewHolder.editbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(myViewHolder.hide_li.getVisibility() == View.GONE) {
                    myViewHolder.hide_li.setVisibility(View.VISIBLE);

                }else {
                    myViewHolder.hide_li.setVisibility(View.GONE);
                }

            }
        });

        myViewHolder.delbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ihisaab.in/school_lms/Adminapi/deleteBatchlist";
              new  DeleteDialog().DeleteDialog("batch_id", activity, batchArrayList.get(i).getUserId(), url);
//                if(new DeleteDialog().DeleteDialog(activity) ==1) {

//                    new CommunityFragment.DELETStuff("batch_id", activity, batchArrayList.get(i).getUserId(), url).execute();
//                }else {
//                    Toast.makeText(activity, "ok won't delete", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return batchArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView name,add_class,mainname,mainsubname;
        Button delbatch ,editbatch,edit_btn;
        LinearLayout hide_li;
        ImageView add_student;
        EditText batchedit;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            hide_li =  itemView.findViewById(R.id.hide_li);
            edit_btn =  itemView.findViewById(R.id.edit_btn);
            editbatch = itemView.findViewById(R.id.editbatch);
            batchedit = itemView.findViewById(R.id.batchedit);
            add_class=(TextView)itemView.findViewById(R.id.add_class);
//            add_student = (ImageView) itemView.findViewById(R.id.add_student);
            mainname =  itemView.findViewById(R.id.mainname);
            delbatch =  itemView.findViewById(R.id.delbatch);
            mainsubname = itemView.findViewById(R.id.mainsubname);

//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(activity, StudentActivity.class);
//                    activity.startActivity(intent1);
//                }
//            });
//       add_student.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(activity,AddStudentActivity.class);
//        activity.startActivity(intent);
//        activity.finish();
//    }
//});


        }
    }

//    public static class DELETStuff extends AsyncTask<String, Void, String> {
//        String user_id;
//        String newclassname;
//        private Dialog dialog;
//        Activity activity;
//        String urls;
//
//
//        public DELETStuff(Activity activity, String user_id, String url)
//        {
//            this.user_id = user_id;
//            this.activity = activity;
//            this.urls = url;
//
//        }
//
//
//        @Override
//        protected String doInBackground(String... arg0) {
//
//            try {
//
//                URL url = new URL(urls);
//
//                JSONObject postDataParams = new JSONObject();
////                postDataParams.put("otp", Mobile_Number);
//                postDataParams.put("class_id", user_id);
//                Log.e("postDataParams", postDataParams.toString());
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(15000 /*milliseconds*/);
//                conn.setConnectTimeout(15000 /*milliseconds*/);
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getPostDataString(postDataParams));
//
//                writer.flush();
//                writer.close();
//                os.close();
//
//                int responseCode = conn.getResponseCode();
//
//                if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                    BufferedReader in = new BufferedReader(new
//                            InputStreamReader(
//                            conn.getInputStream()));
//
//                    StringBuffer sb = new StringBuffer("");
//                    String line = "";
//
//                    while ((line = in.readLine()) != null) {
//
//                        StringBuffer Ss = sb.append(line);
//                        Log.e("Ss", Ss.toString());
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//                    return sb.toString();
//
//                } else {
//                    return new String("false : " + responseCode);
//                }
//            } catch (Exception e) {
//                return new String("Exception: " + e.getMessage());
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
////                dialog.dismiss();
//
//                JSONObject jsonObject1 = null;
//                Log.e("PostRegistration", result.toString());
//                try {
//
//                    jsonObject1 = new JSONObject(result);
//                    if(jsonObject1.getBoolean("responce")){
//                        Intent intent = new Intent(activity , ClassViewActivity.class);
//                        activity.startActivity(intent);
//                        activity.finish();
////                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
////                        getotp.setVisibility(View.VISIBLE);
////                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
//                    }else {
//
//
//                    }
//
//
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//        public String getPostDataString(JSONObject params) throws Exception {
//
//            StringBuilder result = new StringBuilder();
//            boolean first = true;
//
//            Iterator<String> itr = params.keys();
//
//            while (itr.hasNext()) {
//
//                String key = itr.next();
//                Object value = params.get(key);
//
//                if (first)
//                    first = false;
//                else
//                    result.append("&");
//
//                result.append(URLEncoder.encode(key, "UTF-8"));
//                result.append("=");
//                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//            }
//            return result.toString();
//        }
//    }
}