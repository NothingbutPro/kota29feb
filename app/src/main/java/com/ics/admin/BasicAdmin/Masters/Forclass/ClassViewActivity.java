package com.ics.admin.BasicAdmin.Masters.Forclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.Adapter.AdminAdapters.ClassAdapter;
import com.ics.admin.Model.ClassNAmes;
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

public class ClassViewActivity extends AppCompatActivity {
    RecyclerView class_recyclerView;
    com.github.clans.fab.FloatingActionButton class_fab;
    Shared_Preference shared_preference;
    ArrayList<ClassNAmes>classArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        shared_preference=new Shared_Preference();

        TextView class_fab=findViewById(R.id.class_fab);
       class_recyclerView=(RecyclerView)findViewById(R.id.class_recyclerView);

        new Addclass(Shared_Preference.getId(ClassViewActivity.this)).execute();
       class_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in=new Intent(ClassViewActivity.this, AddClassActivity.class);
               startActivity(in);
               finish();
           }
       });
    }
  public class Addclass extends AsyncTask<String, Void, String> {
        String user_id;
        // String Faculty_id;
        private Dialog dialog;


        public Addclass(String user_id)
        {
            this.user_id = user_id;
            // this.subject=subject;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(" http://ihisaab.in/school_lms/Adminapi/classlist");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", user_id);
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
                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userId = jsonObject.getString("id");
                            String Class = jsonObject.getString("Class");
 //                           String Subject = jsonObject.getString("subject");
//                            String password = jsonObject.getString("password");
//                            String mobile = jsonObject.getString("mobile");
//                            String address = jsonObject.getString("address");
//                            String status = jsonObject.getString("status");
//                            String type = jsonObject.getString("type");
//                            String addedby = jsonObject.getString("addedby");


                            classArrayList.add(new ClassNAmes(userId,Class));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
                        class_recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                        ClassAdapter classAdapter = new ClassAdapter(ClassViewActivity.this,classArrayList);
                        class_recyclerView.setAdapter(classAdapter); // set the Adapter to RecyclerView

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
