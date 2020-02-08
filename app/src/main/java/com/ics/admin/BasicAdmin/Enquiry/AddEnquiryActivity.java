package com.ics.admin.BasicAdmin.Enquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.CoursesAdapter;
import com.ics.admin.BasicAdmin.FeesStructure.PayFeesActivity;
import com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity;
import com.ics.admin.CommonJavaClass.AdminProgressdialog;
import com.ics.admin.Interfaces.GetDates;
import com.ics.admin.Model.ABBCoursemodel;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Enqiries;
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

public class AddEnquiryActivity extends AppCompatActivity {
    Spinner class_enq_spin;
     ArrayList<String> list_batch  = new ArrayList<>();;
    String selected_class, sel_id;
    EditText enquiname, mob_enq, folltype, followdate, remark, enquiryby;
    Spinner course ,enqtype;
    Button submitenque;
    String selected_batch, sel_batch;
    ArrayList<String> selectcouser = new ArrayList<>();
    ArrayList<ABBCoursemodel> materialsArrayList = new ArrayList<>();
    private ArrayList<ClassNAmes> class_names;
    private CoursesAdapter addCourseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enquiry);
        class_enq_spin = findViewById(R.id.class_enq_spin);
        enquiname = findViewById(R.id.enquiname);
        course = findViewById(R.id.course);
        mob_enq = findViewById(R.id.mob_enq);
        enqtype = findViewById(R.id.enqtype);
        enquiryby = findViewById(R.id.enquiryby);
        folltype = findViewById(R.id.folltype);
        followdate = findViewById(R.id.followdate);
        remark = findViewById(R.id.remark);
        submitenque = findViewById(R.id.submitenque);

        new GETAllClasses(new Shared_Preference().getId(this)).execute();
        enquiryby.setText(new Shared_Preference().getFactName(this));
        followdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDates getDates = new GetDates();
                getDates.PlaceDate(v.getContext() , followdate);
            }
        });
        submitenque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enquinames = enquiname.getText().toString();
                String mob_enqs = mob_enq.getText().toString();
                String enqtypes = enqtype.getSelectedItem().toString();
//                String folltypes = folltype.getText().toString();
                String folltypes = "None";
                String followdates = followdate.getText().toString();
                String remarks = remark.getText().toString();
                String enquirybys = enquiryby.getText().toString();
                if(enqtype.getSelectedItemPosition() !=0) {
                    new SUBMITQUERY(enquinames, sel_batch, mob_enqs, enqtypes, folltypes, followdates, remarks, enquirybys).execute();
                }else {
                    Toast.makeText(AddEnquiryActivity.this, "Please select right source", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //++++++++++++++++++++++++++++++++++++GEt Class++++++++++++++++++++++++++++++
    private class GETAllClasses extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETAllClasses(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id = class_id;
        }

        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AddEnquiryActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/classlist");

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
                Log.e("PostRegistration ", "for get class  " + result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        class_names = new ArrayList<>();
                        for (int i = 0; i < jarr.length(); i++) {

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id, cl_name));
                        }
                        final ArrayList<String> list_class = new ArrayList<>();
                        list_class.add("-----Select Class----");
                        for (int k = 0; k < class_names.size(); k++) {
                            list_class.add(k+1,class_names.get(k).getClass_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEnquiryActivity.this, android.R.layout.simple_spinner_item
                                , list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_enq_spin.setAdapter(adapter);
                        class_enq_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position ==0) {
                                selected_class = list_class.get(position);
                                new GETAllBathcess(new Shared_Preference().getId(AddEnquiryActivity.this), "-1").execute();
//                                sel_id = "" + class_names.get(position).getUserId();
//                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                            }else {
                                selected_class = list_class.get(position);
                                sel_id =class_names.get(position-1).getUserId();
                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                new GETAllBathcess(new Shared_Preference().getId(AddEnquiryActivity.this), sel_id).execute();
                            }
                        }
                              @Override
                                  public void onNothingSelected(AdapterView<?> parent) {

                                  }
                            }
                        );
                        Log.e("GET CLASS ", ">>>>>>>>>>>>>>>>_____________________" + result.toString());
                        Log.e("GET CLASS ", "ARRAY LIST SPINNER MAP ____________________" + class_names + "\n" + list_class);
                        adminProgressdialog.EndProgress();
                    } else {
                        final ArrayList<String> list_class = new ArrayList<>();
                        list_class.add("-----Select Class----");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEnquiryActivity.this, android.R.layout.simple_spinner_item
                                , list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_enq_spin.setAdapter(adapter);
                        class_enq_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                     @Override
                                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                         if(position ==0) {
                                                                             selected_class = list_class.get(position);
                                                                             new GETAllBathcess(new Shared_Preference().getId(AddEnquiryActivity.this), "-1").execute();
//                                sel_id = "" + class_names.get(position).getUserId();
//                                Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                                                         }else {
                                                                             selected_class = list_class.get(position);
                                                                             sel_id =class_names.get(position-1).getUserId();
                                                                             Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                                                             new GETAllBathcess(new Shared_Preference().getId(AddEnquiryActivity.this), sel_id).execute();
                                                                         }
                                                                     }
                                                                     @Override
                                                                     public void onNothingSelected(AdapterView<?> parent) {

                                                                     }
                                                                 }
                        );
                        Log.e("GET CLASS ", ">>>>>>>>>>>>>>>>_____________________" + result.toString());
                        Log.e("GET CLASS ", "ARRAY LIST SPINNER MAP ____________________" + class_names + "\n" + list_class);
                        adminProgressdialog.EndProgress();
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
    //+++++++++++++++++++++++++++++++++++FOR Batches++++++++++++++++++++++++++++++++++++++++++

    private class GETAllBathcess extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETAllBathcess(String i, String sel_id) {
            this.userid = i;
            this.calls_id = sel_id;
        }
        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AddEnquiryActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getcourse");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", userid);
                postDataParams.put("class_id", calls_id);


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
                    Log.e("Add Course", ">>>>>>>>>>>>>>>>_____________________" + result);
                    jsonObject = new JSONObject(result);
                    materialsArrayList.clear();
                    course.setAdapter(null);
                    list_batch.clear();
                    list_batch.add("--Select Course---");
//                    list_batch.clear();
                    if (jsonObject.getBoolean("responce")) {
                        Toast.makeText(AddEnquiryActivity.this, "Geting Data", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String Classs = jsonObject1.getString("class_id");
                            String title = jsonObject1.getString("Course");
                            String class_id = jsonObject1.getString("class_id");
//                            String pdf_file = jsonObject1.getString("pdf_file");
//                            String id = jsonObject1.getString("id");
//                            String id = jsonObject1.getString("id");
//                            selectcouser.add(title);
                            materialsArrayList.add(new ABBCoursemodel(id, Classs, title, "Course :", "Class:",class_id));
                        }
                        list_batch.clear();
                        list_batch.add("--Select Course---");
                        for (int k = 0; k < materialsArrayList.size(); k++) {

                            list_batch.add(k+1 ,materialsArrayList.get(k).getCourse());
                        }


                        Log.e("GET CLASS ", ">>>>>>>>>>>>>>>>_____________________" + result.toString());
                        Log.e("GET CLASS ", "ARRAY LIST SPINNER MAP ____________________" + class_names + "\n" + selectcouser);
                        adminProgressdialog.EndProgress();

                    } else {
                        Toast.makeText(AddEnquiryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        adminProgressdialog.EndProgress();

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEnquiryActivity.this, android.R.layout.simple_spinner_item
                            , list_batch);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    course.setAdapter(adapter);
                    course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                         @Override
                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                             if(position ==0) {
                                                                 selected_batch = list_batch.get(position);
//                                 sel_batch = "" + materialsArrayList.get(position).getUserId();
                                                                 Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                                             }else {
                                                                 selected_batch = list_batch.get(position);
                                                                 sel_batch = "" + materialsArrayList.get(position-1).getUserId();
                                                                 Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                                             }

//
                                                         }
                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                         }
                                                     }
                    );

                    cancel(true);
                    adminProgressdialog.EndProgress();
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
            cancel(true);
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
    //+++++++++++++++++++++++++++++++++++++++


    private class SUBMITQUERY extends AsyncTask<String, Void, String> {

        String id;
        String enquinames;
        String courses;
        String mob_enqs;
        String enqtypes;
        String folltypes;
        String followdates;
        String remarks;
        String enquirybys;

        public SUBMITQUERY(String enquinames, String courses, String mob_enqs, String enqtypes, String folltypes, String followdates, String remarks, String enquirybys) {
            this.enquinames = enquinames;
            this.courses = courses;
            this.mob_enqs = mob_enqs;
            this.enqtypes = enqtypes;
            this.folltypes = folltypes;
            this.followdates = followdates;
            this.remarks = remarks;
            this.enquirybys = enquirybys;

        }

        AdminProgressdialog adminProgressdialog;
        @Override
        protected void onPreExecute() {
            adminProgressdialog= new AdminProgressdialog(AddEnquiryActivity.this);
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/add_enquiry");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("name", enquinames);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("school_id", new Shared_Preference().getId(AddEnquiryActivity.this));
                postDataParams.put("enquiry_by", enquirybys);
                postDataParams.put("course", courses);
                postDataParams.put("enquiry_type", enqtypes);
                postDataParams.put("followup_type", folltypes);
                postDataParams.put("followup_date", followdates);
                postDataParams.put("remark", remarks);
                postDataParams.put("mobile", mob_enqs);

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
                Log.e("PostRegistration ", "for get class  " + result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("responce")) {
                        adminProgressdialog.EndProgress();
                        Toast.makeText(AddEnquiryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddEnquiryActivity.this , ViewEnquiryActivity.class);
                        startActivity(intent);

                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
//                    JSONArray jarr = jsonObject.getJSONArray("data");
//                    class_names = new ArrayList<>();

                    } else {
                        adminProgressdialog.EndProgress();
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

    //+++++++++++++++++++++++++++++++++++
}
