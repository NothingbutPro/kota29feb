package com.ics.admin.BasicAdmin.Attendence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.AttendenceListAdapter;
import com.ics.admin.Adapter.AdminAdapters.ViewTeachersonAdapter;
import com.ics.admin.BasicAdmin.TeachersDetails.AnnouncementActivity;
import com.ics.admin.BasicAdmin.TeachersDetails.ViewTeachersActivity;
import com.ics.admin.Interfaces.GetDates;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.AttendenceList;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Faculties;
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

public class ViewAttendenceActivity extends AppCompatActivity {
    RecyclerView attenlist;
    EditText attenddate;
    Spinner classattend ,batchattend;
    String selected_batch, sel_batch;
    ArrayList<AttendenceList> attendenceListArrayList = new ArrayList<>();
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    private ArrayList<ClassNAmes> class_names;
    private ArrayList<String> batch_names = new ArrayList<>();
    String selected_class, sel_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);
        attenlist = findViewById(R.id.attenlist);
        classattend = findViewById(R.id.classattend);
        attenddate = findViewById(R.id.attenddateview);
        batchattend = findViewById(R.id.batchattend);
        attenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDates getDates  =new GetDates();
                getDates.PlaceDate(v.getContext() , attenddate);

            }
        });
        attenddate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new GETALLMYATTENDENCES(new Shared_Preference().getId(ViewAttendenceActivity.this) ,sel_id,sel_batch,s.toString()).execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        new GetAllClassesForAttendence(new Shared_Preference().getId(this)).execute();
    }

    private class GETALLMYATTENDENCES extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;
        String sel_id;
        String sel_batch;
        String dates;

        public GETALLMYATTENDENCES(String Faculty_id, String sel_id, String sel_batch, String date) {
            this.Faculty_id =Faculty_id;
            this.sel_id =sel_id;
            this.sel_batch =sel_batch;
            this.dates =date;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/view_attendance");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("school_id", Faculty_id);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("batch_id", sel_batch);
                postDataParams.put("att_date", dates);


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
                        Toast.makeText(ViewAttendenceActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String school_id = jsonObject.getString("school_id");
                            String teacher_id = jsonObject.getString("teacher_id");
                            String class_id = jsonObject.getString("class_id");
                            String batch_id = jsonObject.getString("batch_id");
                            String student_id = jsonObject.getString("student_id");
                            String name = jsonObject.getString("name");
                            String date = jsonObject.getString("date");
                            String attendance = jsonObject.getString("attendance");
                            String status = jsonObject.getString("status");
                            String Class_ = jsonObject.getString("Class");
                            String Batch = jsonObject.getString("Batch");
                            String fromwhere = "forfaculty";

                            attendenceListArrayList.add(new AttendenceList(id,school_id,teacher_id,class_id,batch_id,student_id,date,attendance,school_id,status,Class_,Batch,name));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAttendenceActivity.this);
                        attenlist.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
                        AttendenceListAdapter facultyAdapter = new AttendenceListAdapter(ViewAttendenceActivity.this,attendenceListArrayList);
                        attenlist.setAdapter(facultyAdapter); // set the Adapter to RecyclerView
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

    private class GetAllClassesForAttendence extends AsyncTask<String, Void, String> {
        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GetAllClassesForAttendence(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id = class_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getclass");

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
                        list_class.add("--Class--");
                        for (int k = 0; k < class_names.size(); k++) {

                            list_class.add(k + 1, class_names.get(k).getClass_name());

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewAttendenceActivity.this, android.R.layout.simple_spinner_item
                                , list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        classattend.setAdapter(adapter);
                        classattend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    selected_class = list_class.get(position);

                                } else {
                                    try {
                                        selected_class = list_class.get(position);
                                        sel_id = "" + class_names.get(position - 1).getUserId();
                                        new GETAllBathcesForAttendence(new Shared_Preference().getId(ViewAttendenceActivity.this), sel_id).execute();
                                    } catch (Exception e) {

//                                        selected_class = list_class.get(position);
//                                        sel_id = "" + class_names.get(position-1).getUserId();
//                                        new GETAllBathces(new Shared_Preference().getId(HomeWorkActivity.this),sel_id).execute();
                                        e.printStackTrace();
                                    }
                                    Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                }
                                batch_names.clear();
                                batchArrayList.clear();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ", ">>>>>>>>>>>>>>>>_____________________" + result.toString());
                        Log.e("GET CLASS ", "ARRAY LIST SPINNER MAP ____________________" + class_names + "\n" + list_class);

                    } else {

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

        private class GETAllBathcesForAttendence extends AsyncTask<String, Void, String> {

            String userid;
            String calls_id;
            // String Faculty_id;
            private Dialog dialog;


            public GETAllBathcesForAttendence(String i, String sel_id) {
                this.userid = i;
                this.calls_id = sel_id;
            }

            @Override
            protected String doInBackground(String... arg0) {

                try {

                    URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getbatch");

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

                    JSONObject jsonObject1 = null;
                    Log.e("PostRegistration", result.toString());
                    try {

                        jsonObject1 = new JSONObject(result);

                        if (!jsonObject1.getBoolean("responce")) {
                            Toast.makeText(ViewAttendenceActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.clear();
                            batchArrayList.clear();
                            batchattend.setAdapter(null);
                            batch_names.add("None");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewAttendenceActivity.this, android.R.layout.simple_spinner_item
                                    , batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            batchattend.setAdapter(adapter);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        } else {
                            batch_names.add("--Select--");
                            for (int i = 0; i < jsonObject1.getJSONArray("data").length(); i++) {
                                JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String userid = jsonObject.getString("id");
                                String Class = jsonObject.getString("class_id");
                                String Batch = jsonObject.getString("Batch");


                                batch_names.add(Batch);
                                batchArrayList.add(new ABBBatch(userid, Class, Batch, "Class", "Batch"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewAttendenceActivity.this, android.R.layout.simple_spinner_item
                                    , batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            batchattend.setAdapter(adapter);
                            batchattend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) {
                                        selected_batch = batch_names.get(position);

                                    } else {
                                        try {

                                            selected_batch = batch_names.get(position);
                                            sel_batch = "" + batchArrayList.get(position - 1).getUserId();
                                            Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                            new GETALLMYATTENDENCES(new Shared_Preference().getId(ViewAttendenceActivity.this) ,sel_id,sel_batch,attenddate.getText().toString()).execute();
                                        } catch (Exception e) {
//                                        selected_batch = batch_names.get(position);
//                                        sel_batch = "" + batchArrayList.get(position-1).getUserId();
                                            Toast.makeText(ViewAttendenceActivity.this, "No batch found", Toast.LENGTH_SHORT).show();
                                            Log.e("Spinner batch Selected ", " Items >>> _______" + selected_batch + selected_class + " --- " + sel_id);
                                            e.printStackTrace();
                                        }
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

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
}
