package com.ics.admin.BasicAdmin.Enquiry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.EnquiryAdapter;
import com.ics.admin.Adapter.AdminAdapters.VideosAdminPackageAdapter;
import com.ics.admin.BasicAdmin.Attendence.ViewAttendenceActivity;
import com.ics.admin.BasicAdmin.VideoPermission.VideosViewActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Model.Enqiries;
import com.ics.admin.Model.VideoAdminPackages;
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

public class ViewEnquiryActivity extends AppCompatActivity {
RecyclerView viewenquiry;
    ArrayList<Enqiries> enqiriesArrayList = new ArrayList<>();
    EnquiryAdapter enquiryAdapter;
//    RecyclerView viewenquiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enquiry);
        viewenquiry = findViewById(R.id.viewenquiry);
        new GETALLMYENQUIRIES(new Shared_Preference().getId(this)).execute();
    }

    private class GETALLMYENQUIRIES extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;


        public GETALLMYENQUIRIES(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(ViewEnquiryActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_enquiry");

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

                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(!jsonObject1.getBoolean("responce")){
                        adminProgressdialog.EndProgress();
                        Toast.makeText(ViewEnquiryActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();

                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String enquiry_id = jsonObject.getString("enquiry_id");
                            String school_id = jsonObject.getString("school_id");
                            String enquiry_by = jsonObject.getString("enquiry_by");
                            String enquiry_date = jsonObject.getString("enquiry_date");
                            String name = jsonObject.getString("name");
                            String mobile = jsonObject.getString("mobile");
                            String enquiry_type = jsonObject.getString("enquiry_type");
                            String followup_type = jsonObject.getString("followup_type");
                            String followup_date = jsonObject.getString("followup_date");
                            String remark = jsonObject.getString("remark");
                            String Course = jsonObject.getString("Course");
                            String _Class = jsonObject.getString("Class");
                            String status = jsonObject.getString("status");
                            String classx = jsonObject.getString("class");
                            enqiriesArrayList.add(new Enqiries(enquiry_id,school_id,enquiry_by,enquiry_date,name,mobile,enquiry_type
                            ,followup_type,followup_date,remark,status,_Class,Course,classx));

                            String fromwhere = "forfaculty";

//                            videoAdminPackagesList.add(new VideoAdminPackages(userid,school_id,addedby,video_id,title,description,create_date,expire_date,amount,discount_amount,video_time,status));
                        }

// set LayoutManager to RecyclerView
//
                       enquiryAdapter = new EnquiryAdapter(ViewEnquiryActivity.this,enqiriesArrayList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewEnquiryActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        viewenquiry.setLayoutManager(layoutManager);
                        viewenquiry.setAdapter(enquiryAdapter);
                        adminProgressdialog.EndProgress();
// set the Adapter to RecyclerView
                    }


                } catch (JSONException e) {
                    adminProgressdialog.EndProgress();
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
