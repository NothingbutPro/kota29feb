package com.ics.admin.BasicAdmin.StudentDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ics.admin.Adapter.AdminAdapters.StudentAdapter;
import com.ics.admin.Model.Students;
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

public class StudentAssinviewActivity extends AppCompatActivity {
    ArrayList<Students> studentsList = new ArrayList<>();
    StudentAdapter studentAdapter;
    RecyclerView stuview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assinview);
        //Recycler++++++++++++
        stuview = findViewById(R.id.stuview);
        //++++++++++++
        new GellAllStudebnts(new Shared_Preference().getId(this)).execute();
    }


    private class GellAllStudebnts extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        private Dialog dialog;
        String name; String email; String password; String mobile; String address;

        public GellAllStudebnts(String id) {
            this.userid = id;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/get_assignstudents");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
//                postDataParams.put("user_id", userid);



                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("GET");
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
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            String id = jsonObject11.getString("id");
                            String name = jsonObject11.getString("name");
                            String mobile = jsonObject11.getString("mobile");
                            String email = jsonObject11.getString("email");
                            String address = jsonObject11.getString("address");
//                            String Batch = jsonObject11.getString("Batch");
                            studentsList.add(new Students(id, name, mobile, email, password,address,"","Not Assigned", "feedone"));
                        }
                    }else {

                    }
                    studentAdapter = new StudentAdapter(StudentAssinviewActivity.this,studentsList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(StudentAssinviewActivity.this );
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    stuview.setLayoutManager(layoutManager);
                    stuview.setAdapter(studentAdapter);


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
