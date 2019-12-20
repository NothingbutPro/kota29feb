package com.ics.admin.BasicAdmin.FeesStructure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.EnquiryAdapter;
import com.ics.admin.Adapter.AdminAdapters.ViewFeesAdapter;
import com.ics.admin.BasicAdmin.Enquiry.ViewEnquiryActivity;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.Enqiries;
import com.ics.admin.Model.ViewFees;
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

public class ViewAllFeesActivity extends AppCompatActivity {
    RecyclerView viewfeesrecy;
    Context context;
    ViewFeesAdapter viewFeesAdapter;
    ArrayList<ViewFees> viewFeesArrayList = new ArrayList<>();
    ProgressDialogs progressDialogs = new ProgressDialogs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_fees);
        context = this;
        viewfeesrecy = findViewById(R.id.viewfeesrecy);
        new GETAllFees(new Shared_Preference().getId(this)).execute();
    }

    private class GETAllFees extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;

        @Override
        protected void onPreExecute() {
            progressDialogs.ProgressMe(context,(Activity)context);

            super.onPreExecute();
        }

        public GETAllFees(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_fee");

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
                progressDialogs.EndMe();
                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(!jsonObject1.getBoolean("responce")){
                        Toast.makeText(context, "Nothing found", Toast.LENGTH_SHORT).show();
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
                            String fee_amount = jsonObject.getString("fee_amount");
                            String create_date = jsonObject.getString("create_date");
                            String Course = jsonObject.getString("Course");
                            String _Class = jsonObject.getString("Class");
                            String status = jsonObject.getString("status");

                            viewFeesArrayList.add(new ViewFees(id,school_id,addedby,class_id,course_id,fee_amount,status,create_date,_Class,Course));
                        }
                        viewFeesAdapter = new ViewFeesAdapter((Activity) context,viewFeesArrayList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        viewfeesrecy.setLayoutManager(layoutManager);
                        viewfeesrecy.setAdapter(viewFeesAdapter);
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
