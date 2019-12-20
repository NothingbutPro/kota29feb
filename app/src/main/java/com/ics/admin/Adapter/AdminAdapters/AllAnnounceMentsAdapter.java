package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.HomeWork.ViewAllAnnouncements;
import com.ics.admin.DeleteDialog;
import com.ics.admin.Model.AllAnnouncemet;
import com.ics.admin.Model.AttendenceList;
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

public class AllAnnounceMentsAdapter extends RecyclerView.Adapter<AllAnnounceMentsAdapter.MyViewHolder>{
    ArrayList<AllAnnouncemet> classArrayList = new ArrayList<>();
    Activity activity;

    public AllAnnounceMentsAdapter(Activity activity, ArrayList<AllAnnouncemet> classArrayList) {
        this.activity=activity;
        this.classArrayList = classArrayList;

    }

    @NonNull
    @Override
    public AllAnnounceMentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.announcemetnttxt, viewGroup, false);

        return new AllAnnounceMentsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAnnounceMentsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.announcetxt.setText(""+classArrayList.get(i).getNotification());
        myViewHolder.attendclass.setText("Class :"+classArrayList.get(i).getClass_());
        myViewHolder.attendencetxt.setText(""+classArrayList.get(i).getBatch());
        myViewHolder.attenddate.setText(""+classArrayList.get(i).getDate());
        myViewHolder.crdate.setText(""+classArrayList.get(i).getCr_date());
//        myViewHolder.announcetxt.setText("");
        myViewHolder.delannounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAnnouncements(classArrayList.get(i).getId()).execute();
            }
        });


    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView attendstudent,attendclass,attendencetxt,attenddate,announcetxt,crdate;

        Button delannounce;
        LinearLayout hideli;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            attendstudent = (TextView) itemView.findViewById(R.id.attendstudent);
            attendclass = (TextView) itemView.findViewById(R.id.attendclass);
            attendencetxt = (TextView) itemView.findViewById(R.id.attendencetxt);
            crdate = (TextView) itemView.findViewById(R.id.cr_date);
            attenddate = (TextView) itemView.findViewById(R.id.attenddate);
            announcetxt = (TextView) itemView.findViewById(R.id.announcetxt);
            delannounce = (Button) itemView.findViewById(R.id.delannounce);

        }

    }

    private class DeleteAnnouncements extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;

        public DeleteAnnouncements(String id) {
            this.id = id;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/delete_announcement");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("id", id);

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

                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")) {
                        Intent intent = new Intent(activity , activity.getClass());
                        activity.startActivity(intent);
                        activity.finish();
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
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