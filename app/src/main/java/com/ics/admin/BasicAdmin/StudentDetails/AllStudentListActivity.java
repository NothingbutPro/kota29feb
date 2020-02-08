package com.ics.admin.BasicAdmin.StudentDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.StudentAdapter;
import com.ics.admin.BasicAdmin.AdminProfileActivity;
import com.ics.admin.BasicAdmin.FeesStructure.AllStudentFeeActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
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

public class AllStudentListActivity extends AppCompatActivity {
    RecyclerView allrecyle;
    ArrayList<Students> studentsList = new ArrayList<>();
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_list);
        allrecyle = findViewById(R.id.allrecyle);
        new GETALLSTUDENTSFOrAll(new Shared_Preference().getId(this)).execute();
    }

    private class GETALLSTUDENTSFOrAll extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        private Dialog dialog;
        String name; String email; String password; String mobile; String address;

        public GETALLSTUDENTSFOrAll(String id) {
            this.userid = id;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AllStudentListActivity.this);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_student");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", userid);



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
                            String Class = jsonObject11.getString("Class");
                            String Batch = jsonObject11.getString("Batch");
                            studentsList.add(new Students(id, name, mobile, email, password,Class,Batch,"assigned", "feedone"));
                        }
                    }else {
                        Toast.makeText(AllStudentListActivity.this, "No students found ,assign them first", Toast.LENGTH_SHORT).show();
                    }
                    studentAdapter = new StudentAdapter(AllStudentListActivity.this,studentsList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(AllStudentListActivity.this );
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    allrecyle.setLayoutManager(layoutManager);
                    allrecyle.setAdapter(studentAdapter);


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
            adminProgressdialog.EndProgress();
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
