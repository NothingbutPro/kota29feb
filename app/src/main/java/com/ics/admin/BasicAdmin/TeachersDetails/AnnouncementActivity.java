package com.ics.admin.BasicAdmin.TeachersDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity;
import com.ics.admin.BasicAdmin.HomeWork.ViewAllAnnouncements;
import com.ics.admin.Interfaces.GetDates;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.StudentsFeesEmi;
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
import java.util.Calendar;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AnnouncementActivity extends AppCompatActivity {
//    RecyclerView announcerecy;
    Button announcementbtn,viewannounce_btn;
    EditText notificationedt, seldates;
//    com.github.clans.fab.FloatingActionButton announcefabid;
    String selected_batch, sel_batch;
    Spinner class_announce_spin, batch_id_announce_spin;
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    private ArrayList<ClassNAmes> class_names;
    private ArrayList<String> batch_names = new ArrayList<>();
    String selected_class, sel_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
//        announcerecy = findViewById(R.id.announcerecy);
        seldates = findViewById(R.id.seldates);
        announcementbtn = findViewById(R.id.announcementbtn);
        notificationedt = findViewById(R.id.notificationedt);
//        announcefabid = findViewById(R.id.announcefabid);
        class_announce_spin = findViewById(R.id.class_announce_spin);
        viewannounce_btn = findViewById(R.id.viewannounce_btn);
        batch_id_announce_spin = findViewById(R.id.batch_id_announce_spin);
        viewannounce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0= new Intent(v.getContext(), ViewAllAnnouncements.class);
                v.getContext().startActivity(intent0);
            }
        });
        new GetAllClassesForannounce(new Shared_Preference().getId(this)).execute();
        seldates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDates getDates = new GetDates();
                getDates.PlaceDate(v.getContext(), seldates);
            }
        });
        announcementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostAllAnnouncements(new Shared_Preference().getId(v.getContext()), notificationedt.getText().toString()).execute();
            }
        });
    }

    private class GetAllClassesForannounce extends AsyncTask<String, Void, String> {
        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GetAllClassesForannounce(String id) {
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
                        list_class.add("--Select Class--");
                        for (int k = 0; k < class_names.size(); k++) {

                            list_class.add(k + 1, class_names.get(k).getClass_name());

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnnouncementActivity.this, android.R.layout.simple_spinner_item
                                , list_class);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        class_announce_spin.setAdapter(adapter);
                        class_announce_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    selected_class = list_class.get(position);

                                } else {
                                    try {
                                        selected_class = list_class.get(position);
                                        sel_id = "" + class_names.get(position - 1).getUserId();
                                        new GETAllBathcesForAnnounce(new Shared_Preference().getId(AnnouncementActivity.this), sel_id).execute();
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
    }

    private class GETAllBathcesForAnnounce extends AsyncTask<String, Void, String> {

        String userid;
        String calls_id;
        // String Faculty_id;
        private Dialog dialog;


        public GETAllBathcesForAnnounce(String i, String sel_id) {
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
                        Toast.makeText(AnnouncementActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        batch_names.clear();
                        batchArrayList.clear();
                        batch_id_announce_spin.setAdapter(null);
                        batch_names.add("No Batch Found");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnnouncementActivity.this, android.R.layout.simple_spinner_item
                                , batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_id_announce_spin.setAdapter(adapter);
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    } else {
                        batch_names.add("--Select Batch--");
                        for (int i = 0; i < jsonObject1.getJSONArray("data").length(); i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("id");
                            String Class = jsonObject.getString("class_id");
                            String Batch = jsonObject.getString("Batch");


                            batch_names.add(Batch);
                            batchArrayList.add(new ABBBatch(userid, Class, Batch, "Class", "Batch"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AnnouncementActivity.this, android.R.layout.simple_spinner_item
                                , batch_names);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        batch_id_announce_spin.setAdapter(adapter);
                        batch_id_announce_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    selected_batch = batch_names.get(position);
                                } else {
                                    try {

                                        selected_batch = batch_names.get(position);
                                        sel_batch = "" + batchArrayList.get(position - 1).getUserId();
                                        Log.e("Spinner Selected ", " Items >>> _______" + selected_class + " --- " + sel_id);
                                    } catch (Exception e) {
//                                        selected_batch = batch_names.get(position);
//                                        sel_batch = "" + batchArrayList.get(position-1).getUserId();
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

    private class PostAllAnnouncements extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        public ProgressDialogs progressDialogss;
        Dialog dialog;
        String name;
        String email;
        String password;
        String mobile;
        String address;

        public PostAllAnnouncements(String id, String dialog) {
            this.userid = id;
            this.name = dialog;
        }

        @Override
        protected void onPreExecute() {
//            progressDialogss=  new ProgressDialogs();
//            progressDialogss.ProgressMe((Context) Ann ,activity );
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/add_announcement");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("batch_id", sel_batch);
                postDataParams.put("notification", name);
                postDataParams.put("user_id", userid);
                postDataParams.put("date", seldates.getText().toString());
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
                    if (jsonObject1.getBoolean("responce")) {
                        Toast.makeText(AnnouncementActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AnnouncementActivity.this , ViewAllAnnouncements.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        Toast.makeText(activity, "No students found ,assign them first", Toast.LENGTH_SHORT).show();
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

