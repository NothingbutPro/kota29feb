package com.ics.admin.BasicAdmin.Attendence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.FacultyAdapter;
import com.ics.admin.Adapter.AdminAdapters.StudentAdapter;
import com.ics.admin.Adapter.AdminAdapters.StudentAttendenceAdapter;
import com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity;
import com.ics.admin.BasicAdmin.SelectFacultyActivity;
import com.ics.admin.BasicAdmin.StudentDetails.Studentadnviewactivty;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.Model.Faculties;
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
import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.selectteacher;
import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.selectteacherStrings;
import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.teacher_id;
public class Addattendactivity extends AppCompatActivity {
    RecyclerView attendancegrid;
//    public static TextView selectteacher;
//    public static String selectteacherStrings ,teacher_id;
    LinearLayout attenrec;
    Button attenbtn;
    private Spinner attenclass_home_spinner,attenclass_batch_spinner;
    private ArrayList<ClassNAmes> class_names;
    TextView attenselectteacher;
    String selected_class , sel_id;
    String selected_batch , sel_batch;
    private ArrayList<String> batch_names = new ArrayList<>();
    private ArrayList<ABBBatch> batchArrayList = new ArrayList<>();
    private ArrayList<Faculties> facultiesArrayList = new ArrayList<>();
    ArrayList<Students> studentsList = new ArrayList<>();
   public static ArrayList<Students> attendedstudentsList = new ArrayList<>();
    private StudentAttendenceAdapter studentAdapter;
    Button studentattendbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addattendactivity);
        attenclass_home_spinner = findViewById(R.id.attenclass_home_spinner);
        attendancegrid = findViewById(R.id.attendancegrid);
        attenbtn = findViewById(R.id.attenbtn);
        attenclass_batch_spinner = findViewById(R.id.attenclass_batch_spinner);
        attenselectteacher = findViewById(R.id.attenselectteacher);
        selectteacher = findViewById(R.id.attenselectteacher);
        studentattendbtn = findViewById(R.id.studentattendbtn);
        attenrec = findViewById(R.id.attenrec);
//        attenrec.setVisibility(View.GONE);
//        new GETSTUDENTFORTTENDENCE().execute();

        new GETCLASSFORHOMe(new Shared_Preference().getId(this)).execute();

        selectteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , SelectFacultyActivity.class);
                startActivity(intent);
//                final Dialog dialog = new Dialog(v.getContext() );
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(true);
////                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
////                        WindowManager.LayoutParams.MATCH_PARENT);
////                dialog.getWindow().setLayout(getWindow().);
//                dialog.setContentView(R.layout.facultydialog);
//                facultyrec = dialog.findViewById(R.id.facultyrecx);
//                facultiesArrayList.clear();
//                new GETSALLFACULTIESs(new Shared_Preference().getId(v.getContext())).execute();
//                selectteacher.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
//
//                dialog.show();
            }
        });
        studentattendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(attenrec.getVisibility() == View.GONE)
            {
                attenrec.setVisibility(View.VISIBLE);
            }else {
                attenrec.setVisibility(View.GONE);
            }
            }
        });
        attenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attendedstudentsList.size() !=0)
                {
                    if(selectteacher !=null)
                    {
                        if(sel_id ==null)
                        {
                            Toast.makeText(Addattendactivity.this, "Please select class first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(sel_batch == null)
                        {
                            Toast.makeText(Addattendactivity.this, "Please select batch first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONArray attenJsonArray = new JSONArray();
                        for(int i=0;i<attendedstudentsList.size();i++) {
                            JSONObject jObjP = new JSONObject();
                            try {
                                jObjP.put("student_id",attendedstudentsList.get(i).getId());
                                jObjP.put("attendance",attendedstudentsList.get(i).getPresent_or_not());
                                attenJsonArray.put(jObjP);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        new SUBMITFINALATTNDENCE(new Shared_Preference().getId(v.getContext()),attenJsonArray ,sel_batch,sel_id).execute();
                    }else {
                        Toast.makeText(Addattendactivity.this, "Please select a teacher", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(Addattendactivity.this, "No Students For attendance", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class GETCLASSFORHOMe extends AsyncTask<String, Void, String> {

        String id;
        String class_id;
        String course;
        private Dialog dialog;

        public GETCLASSFORHOMe(String id) {
            this.id = id;
            //  this.course=course;
            this.class_id=class_id;
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
                Log.e("PostRegistration ", "for get class  "+result.toString());
                try {
                    class_names = new ArrayList<>();
                    class_names.clear();
//                    list_class.clear();
                    jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("responce")){
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                        JSONArray jarr = jsonObject.getJSONArray("data");
                        for (int i=0 ; i<jarr.length() ; i++){

                            String cl_id = jarr.getJSONObject(i).getString("id");
                            String cl_name = jarr.getJSONObject(i).getString("Class");
                            class_names.add(new ClassNAmes(cl_id,cl_name));
                        }
                        final ArrayList <String> list_class = new ArrayList<>();
                        list_class.add("--Select Class--");
                        for (int k=0 ; k<class_names.size() ; k++){
                            list_class.add(class_names.get(k).getClass_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Addattendactivity.this, android.R.layout.simple_spinner_item
                                ,list_class);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        attenclass_home_spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        attenclass_home_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position ==0) {
                                    selected_class = list_class.get(position);
                                }else {
                                    selected_class = list_class.get(position);
                                    sel_id =""+class_names.get(position-1).getUserId();
                                    Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);
                                    new GETAllBathcess(new Shared_Preference().getId(Addattendactivity.this),sel_id).execute();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("GET CLASS ",">>>>>>>>>>>>>>>>_____________________"+result.toString());
                        Log.e("GET CLASS ","ARRAY LIST SPINNER MAP ____________________"+class_names+"\n"+list_class);

                    }
                    else
                    {

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

        private class GETAllBathcess extends AsyncTask<String, Void, String> {

            String userid;
            String calls_id;
            // String Faculty_id;
            private Dialog dialog;


            public GETAllBathcess(String i, String sel_id) {
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
                        batch_names.clear();
                        batchArrayList.clear();
                        jsonObject1 = new JSONObject(result);
                        if(!jsonObject1.getBoolean("responce"))
                        {
                            attenclass_batch_spinner.setAdapter(null);
                            Toast.makeText(Addattendactivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            batch_names.add("--Select Batch--");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Addattendactivity.this, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            attenclass_batch_spinner.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                        }else {
                            batch_names.add("--Select--");
                            for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                                JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                                String userid = jsonObject.getString("id");
                                String Class = jsonObject.getString("class_id");
                                String Batch = jsonObject.getString("Batch");
//                            String password = jsonObject.getString("password");
//                            String mobile = jsonObject.getString("mobile");
//                            String address = jsonObject.getString("address");
//                            String status = jsonObject.getString("status");
//                            String type = jsonObject.getString("type");
//                            String addedby = jsonObject.getString("addedby");

                                batch_names.add(Batch);
                                batchArrayList.add(new ABBBatch(userid,Class,Batch,"Class","Batch"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Addattendactivity.this, android.R.layout.simple_spinner_item
                                    ,batch_names);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            // Apply the adapter to the spinner
                            attenclass_batch_spinner.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            attenclass_batch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position ==0) {
                                        selected_batch = batch_names.get(position);
                                    }else {
                                        selected_batch = batch_names.get(position);
                                        sel_batch =""+batchArrayList.get(position-1).getUserId();
                                        Log.e("Spinner Selected "," Items >>> _______"+selected_class+" --- "+sel_id);

                                        attenrec.setVisibility(View.VISIBLE);
                                    }

                                    studentsList.clear();
                                    new GETALLSTUDENTS(new Shared_Preference().getId(Addattendactivity.this),sel_id,sel_batch ).execute();
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

    private class GETALLSTUDENTS extends AsyncTask<String, Void, String> {
        String userid,class_id,bath_id;
        // String Faculty_id;
        private Dialog dialog;
        String name; String email; String password; String mobile; String address;

        public GETALLSTUDENTS(String id, String sel_id, String sel_batch) {
            this.userid = id;
            this.class_id = sel_id;
            this.bath_id = sel_batch;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/get_students");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("school_id", userid);
                postDataParams.put("class_id", class_id);
                postDataParams.put("batch_id", bath_id);



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
                    if(jsonObject1.getBoolean("responce")){
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            String id = jsonObject11.getString("id");
                            String name = jsonObject11.getString("name");
                            String mobile = jsonObject11.getString("mobile");
                            String email = jsonObject11.getString("email");
                            studentsList.add(new Students(id, name, mobile, email, password,"Present"));
                            attendedstudentsList.add(new Students(id, name, mobile, email, password,"Present"));
                        }
                    }else {

                    }
                    studentAdapter = new StudentAttendenceAdapter(Addattendactivity.this,studentsList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Addattendactivity.this );
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    attendancegrid.setLayoutManager(layoutManager);
                    attendancegrid.setAdapter(studentAdapter);


                } catch (JSONException e) {
                    Toast.makeText(Addattendactivity.this, "Nothing Found", Toast.LENGTH_SHORT).show();
                    attendedstudentsList.clear();
                    studentsList.clear();
                    studentAdapter.notifyDataSetChanged();
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

    private class SUBMITFINALATTNDENCE extends AsyncTask<String, Void, String> {
        String userid,class_id,bath_id;
        // String Faculty_id;
        private Dialog dialog;
        JSONArray attenJsonArray;
        String name; String email; String password; String mobile; String address;

        public SUBMITFINALATTNDENCE(String id, JSONArray attenJsonArray, String sel_batch, String sel_id) {
            this.userid =id;
            this.attenJsonArray =attenJsonArray;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/add_students_attendance");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("school_id", userid);
                postDataParams.put("class_id", sel_id);
                postDataParams.put("batch_id", sel_batch);
                postDataParams.put("teacher_id", teacher_id);
                postDataParams.put("data", attenJsonArray);



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
                    if(jsonObject1.getBoolean("responce")){
                        finish();
                    }else {
                        Toast.makeText(Addattendactivity.this, "Please select all fields", Toast.LENGTH_SHORT).show();
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
