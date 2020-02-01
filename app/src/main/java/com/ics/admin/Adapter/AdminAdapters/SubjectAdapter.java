package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.Masters.Subjects.SubjectViewActivity;
import com.ics.admin.CommonJavaClass.DeleteDialog;
import com.ics.admin.Model.ABBSubject;
import com.ics.admin.R;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder>{

    //ArrayList<SubjectViewActivity.ABBSubject> subjectArrayList = new ArrayList<>();
    ArrayList<ABBSubject>subjectArrayList = new ArrayList<>();
    Activity activity;

    public SubjectAdapter(SubjectViewActivity activity, ArrayList<ABBSubject> subjectsArrayList) {

        this.activity = activity;
        this.subjectArrayList = subjectsArrayList;
    }

    @NonNull
    @Override
    public SubjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_adapter, viewGroup, false);

        return new SubjectAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.add_Subject.setText(""+subjectArrayList.get(i).getSubject());
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myedit = myViewHolder.editall.getText().toString();
                String url = "http://ihisaab.in/school_lms/Adminapi/update_subject";
                new EditStuffs("update_subject", activity, "",subjectArrayList.get(i).getUserId(),myedit, url).execute();
            }
        });
        myViewHolder.editsubsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myViewHolder.hide_li.getVisibility() == View.GONE)
                {
                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
                }else {
                myViewHolder.hide_li .setVisibility(View.GONE);
                }
            }
        });
        myViewHolder.delesubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ihisaab.in/school_lms/Adminapi/deletesubjectlist";
                new DeleteDialog().DeleteDialog("subject_id",activity , subjectArrayList.get(i).getUserId(), url);
//                new DELETStuffSubject("subject_id",activity , subjectArrayList.get(i).getUserId(), url).execute();
            }
        });
        Log.e("Text > ","   "+subjectArrayList.get(i).getSubject());
    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView add_Subject;
        Button delesubs;
        EditText editall;
        LinearLayout hide_li;
        Button edit_btn;
        Button editsubsbtn;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            add_Subject = (TextView) itemView.findViewById(R.id.add_Subject);
            delesubs =  itemView.findViewById(R.id.delesubs);
            editall =  itemView.findViewById(R.id.editall);
            hide_li =  itemView.findViewById(R.id.hide_li);
            edit_btn =  itemView.findViewById(R.id.edit_btn);
            editsubsbtn =  itemView.findViewById(R.id.editsubsbtn);
        }
    }

    private class DELETStuffSubject extends AsyncTask<String, Void, String> {
        String user_id;
        String newclassname;
        private Dialog dialog;


        Activity activity;
        String urls;


        public DELETStuffSubject(String Del_ids ,Activity activity, String user_id, String url)
        {
            this.user_id = user_id;
            this.activity = activity;
            this.urls = url;
            this.newclassname = Del_ids;

        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(urls);

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put(newclassname, user_id);
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
                    if(jsonObject1.getBoolean("responce")){
                        Intent intent = new Intent(activity , activity.getClass());
                        activity.startActivity(intent);
                        activity.finish();
//                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {


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


