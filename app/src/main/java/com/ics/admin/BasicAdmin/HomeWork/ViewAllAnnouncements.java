package com.ics.admin.BasicAdmin.HomeWork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.AllAnnounceMentsAdapter;
import com.ics.admin.Adapter.AdminAdapters.HomeWorkAdapter;
import com.ics.admin.Model.AllAnnouncemet;
import com.ics.admin.Model.HomeWorks;
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

public class ViewAllAnnouncements extends AppCompatActivity {
    RecyclerView _announceidrec;
    ArrayList<AllAnnouncemet> allAnnouncemetArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_announcements);
        _announceidrec=findViewById(R.id.announceidrec);
        new GETALLANNPOUNCEMTNS(new Shared_Preference().getId(this)).execute();
    }

    private class GETALLANNPOUNCEMTNS extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String batch;

        public GETALLANNPOUNCEMTNS(String id) {
            this.id = id;
        }


        @Override
        protected String doInBackground(String... arg0) {


            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_announcement");

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
                Log.e("PostRegistration", result.toString());
                try {

                    Log.e("AddBatch ",">>>>>>>>>>>>>>>>_____________________"+result);
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        Toast.makeText(ViewAllAnnouncements.this, "Geting Data", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String user_id = jsonObject1.getString("user_id");
                            String class_id = jsonObject1.getString("class_id");
                            String batch_id = jsonObject1.getString("batch_id");
                            String notification = jsonObject1.getString("notification");
                            String date = jsonObject1.getString("date");
                            String status = jsonObject1.getString("status");
                            String Batch = jsonObject1.getString("Batch");
                            String Class_ = jsonObject1.getString("Class");
                            String cr_date = jsonObject1.getString("cr_date");
                            Toast.makeText(ViewAllAnnouncements.this, "date is "+date, Toast.LENGTH_SHORT).show();

                            allAnnouncemetArrayList.add(new AllAnnouncemet(id,user_id,class_id,batch_id,notification,date,status,Batch,Class_,cr_date));

                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewAllAnnouncements.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        AllAnnounceMentsAdapter allAnnounceMentsAdapter = new AllAnnounceMentsAdapter(ViewAllAnnouncements.this , allAnnouncemetArrayList);
                        _announceidrec.setLayoutManager(layoutManager);
                        _announceidrec.setAdapter(allAnnounceMentsAdapter);


                    }
                    else
                    {
                        Toast.makeText(ViewAllAnnouncements.this, "Failed", Toast.LENGTH_SHORT).show();
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
